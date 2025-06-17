package com.cashcontrol.cashcontrol_api.controller;

import com.cashcontrol.cashcontrol_api.dto.TransacaoCreateDto;
import com.cashcontrol.cashcontrol_api.dto.TransacaoDto;
import com.cashcontrol.cashcontrol_api.model.Transacao;
import com.cashcontrol.cashcontrol_api.model.TipoTransacao;
import com.cashcontrol.cashcontrol_api.model.User;
import com.cashcontrol.cashcontrol_api.service.TransacaoService;
import com.cashcontrol.cashcontrol_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * Controller responsável pelo gerenciamento de transações do usuário.
 * <p>
 * Possibilita criação, listagem e busca de transações, além de proteger endpoints críticos por autenticação via token.
 * </p>
 */
@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private UserService userService;

    /**
     * Cria uma nova transação para o usuário autenticado.
     *
     * @param dto   DTO de criação de transação (valor, tipo, userId).
     * @param token Token de autenticação enviado no header Authorization.
     * @return TransacaoDto salva ou mensagem de erro/intervenção.
     *
     * <p>Exemplo de requisição:
     * <pre>
     * POST /api/transacoes
     * Header: Authorization: seu-token-aqui
     * {
     *   "valor": 100,
     *   "tipo": "APOSTA",
     *   "userId": 1
     * }
     * </pre>
     * </p>
     */
    // @Operation(summary = "Criar transação", description = "Cria uma nova transação (aposta ou investimento) para o usuário autenticado.")
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody TransacaoCreateDto dto,
                                   @RequestHeader("Authorization") String token) {
        Optional<User> userOpt = userService.buscarPorId(dto.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }
        User user = userOpt.get();

        // Verifica token
        if (user.getToken() == null || !user.getToken().equals(token)) {
            return ResponseEntity.status(401).body("Token inválido ou não autorizado");
        }

        // Verifica cooldown (impedido de apostar)
        if (dto.getTipo().equalsIgnoreCase("APOSTA") && user.getCooldownAposta() != null) {
            if (user.getCooldownAposta().isAfter(LocalDateTime.now())) {
                long minutosRestantes = java.time.Duration.between(LocalDateTime.now(), user.getCooldownAposta()).toMinutes() + 1;
                return ResponseEntity.badRequest().body(
                        "Você precisa esperar " + minutosRestantes + " minuto(s) antes de apostar novamente devido a tentativa anterior acima do limite. Procure investir esse valor!"
                );
            } else {
                // Cooldown expirou, libera aposta e limpa cooldown
                user.setCooldownAposta(null);
                userService.salvar(user);
            }
        }

        // Intervenção: bloquear aposta maior que 20% do saldo
        if (dto.getTipo().equalsIgnoreCase("APOSTA")) {
            double percentual = dto.getValor() / user.getSaldo();
            if (percentual > 0.2) {
                // Adiciona cooldown de 5 minutos
                user.setCooldownAposta(LocalDateTime.now().plusMinutes(5));
                userService.salvar(user);

                // Monta sugestão de investimento detalhada
                String sugestaoInvestimento =
                        "\nSugestão de Investimento:\n" +
                                "- Tesouro Selic: Liquidez diária, risco baixíssimo, rendimento próximo de 100% do CDI\n" +
                                "- Poupança: Rendimento baixo (~0,5% ao mês), mas resgate fácil\n" +
                                "- CDB de grande banco: Rende mais que a poupança e tem proteção do FGC\n\n" +
                                "Que tal simular quanto você teria daqui alguns meses investindo esse valor? Use a função Simulador no app!";

                return ResponseEntity.badRequest().body(
                        "Atenção: O valor desta aposta (" + dto.getValor() +
                                ") representa " + String.format("%.1f", percentual * 100) +
                                "% do seu saldo atual. Por sua segurança, você ficará impedido de apostar por 5 minutos.\n" +
                                sugestaoInvestimento
                );
            }
        }

        Transacao transacao = new Transacao();
        transacao.setUser(user);
        transacao.setValor(dto.getValor());
        transacao.setTipo(TipoTransacao.valueOf(dto.getTipo().toUpperCase()));
        transacao.setData(LocalDateTime.now());
        transacao.setStatus("REALIZADA");

        Transacao salva = transacaoService.salvar(transacao);
        TransacaoDto response = toDto(salva);
        return ResponseEntity.ok(response);
    }

    /**
     * Lista todas as transações existentes no sistema (endpoint público).
     *
     * @return Lista de TransacaoDto.
     */
    // @Operation(summary = "Listar todas as transações", description = "Retorna todas as transações do sistema.")
    @GetMapping
    public List<TransacaoDto> listarTodas() {
        return transacaoService.listarTodas()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Lista todas as transações de um usuário autenticado.
     *
     * @param userId ID do usuário.
     * @param token  Token enviado no header Authorization.
     * @return Lista de TransacaoDto.
     */
    // @Operation(summary = "Listar transações do usuário", description = "Retorna todas as transações do usuário autenticado (proteção por token).")
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<TransacaoDto>> listarPorUsuario(@PathVariable Long userId,
                                                               @RequestHeader("Authorization") String token) {
        Optional<User> userOpt = userService.buscarPorId(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).build();
        }
        User user = userOpt.get();

        if (user.getToken() == null || !user.getToken().equals(token)) {
            return ResponseEntity.status(401).build();
        }

        List<TransacaoDto> lista = transacaoService.listarPorUsuario(user)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    /**
     * Busca uma transação específica pelo seu ID, protegida por token.
     *
     * @param id    ID da transação.
     * @param token Token enviado no header Authorization.
     * @return TransacaoDto se encontrado e autorizado, ou 401/404.
     */
    // @Operation(summary = "Buscar transação por ID", description = "Retorna detalhes da transação caso pertença ao usuário autenticado.")
    @GetMapping("/{id}")
    public ResponseEntity<TransacaoDto> buscarPorId(@PathVariable Long id,
                                                    @RequestHeader("Authorization") String token) {
        Optional<Transacao> transacaoOpt = transacaoService.buscarPorId(id);
        if (transacaoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Transacao transacao = transacaoOpt.get();

        // Confirma que o usuário autenticado é o dono da transação
        User user = transacao.getUser();
        if (user.getToken() == null || !user.getToken().equals(token)) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(toDto(transacao));
    }

    /**
     * Converte a entidade Transacao para DTO.
     *
     * @param t Transacao da base.
     * @return TransacaoDto preenchido.
     */
    private TransacaoDto toDto(Transacao t) {
        TransacaoDto dto = new TransacaoDto();
        dto.setId(t.getId());
        dto.setValor(t.getValor());
        dto.setTipo(t.getTipo().name());
        dto.setData(t.getData());
        dto.setStatus(t.getStatus());
        dto.setUserId(t.getUser().getId());
        return dto;
    }
}

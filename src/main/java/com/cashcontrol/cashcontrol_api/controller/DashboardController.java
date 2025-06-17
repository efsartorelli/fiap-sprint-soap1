package com.cashcontrol.cashcontrol_api.controller;

import com.cashcontrol.cashcontrol_api.dto.BadgeDto;
import com.cashcontrol.cashcontrol_api.dto.DashboardDto;
import com.cashcontrol.cashcontrol_api.model.Badge;
import com.cashcontrol.cashcontrol_api.model.Transacao;
import com.cashcontrol.cashcontrol_api.model.TipoTransacao;
import com.cashcontrol.cashcontrol_api.model.User;
import com.cashcontrol.cashcontrol_api.service.BadgeService;
import com.cashcontrol.cashcontrol_api.service.TransacaoService;
import com.cashcontrol.cashcontrol_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * Controller responsável pelo dashboard do usuário.
 * <p>
 * Este endpoint retorna informações consolidadas do progresso do usuário,
 * como saldo, gastos, XP acumulado, dias sem apostar e badges conquistadas.
 * Requer autenticação por token.
 * </p>
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private BadgeService badgeService;

    /**
     * Retorna o dashboard consolidado do usuário autenticado.
     *
     * @param userId ID do usuário.
     * @param token  Token de autenticação (enviado no header "Authorization").
     * @return Informações do dashboard: saldo, gastos, dias sem apostar, XP, badges.
     *
     * <p>Exemplo de requisição:
     * <pre>
     * GET /api/dashboard/1
     * Header: Authorization: seu-token-aqui
     * </pre>
     * </p>
     *
     * <p>Exemplo de resposta:
     * <pre>
     * {
     *   "saldo": 2700.00,
     *   "gastosTotais": 300.00,
     *   "diasSemApostar": 7,
     *   "xp": 3,
     *   "badges": [
     *     {
     *       "id": 1,
     *       "nome": "1B XP",
     *       "descricao": "Primeiro dia sem apostas",
     *       "dataConquista": "2025-06-10",
     *       "userId": 1
     *     }
     *   ]
     * }
     * </pre>
     * </p>
     */
    // @Operation(summary = "Dashboard do usuário", description = "Retorna informações de saldo, gastos, XP, dias sem apostar e badges do usuário. Protegido por token.")
    @GetMapping("/{userId}")
    public ResponseEntity<DashboardDto> getDashboard(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String token
    ) {
        Optional<User> userOpt = userService.buscarPorId(userId);
        if (userOpt.isEmpty()) return ResponseEntity.status(401).build(); // Não encontrado = 401 para evitar leak de existência

        User user = userOpt.get();

        // Checagem de token
        if (user.getToken() == null || !user.getToken().equals(token)) {
            return ResponseEntity.status(401).build(); // Não autorizado
        }

        // Buscar transações do usuário
        List<Transacao> transacoes = transacaoService.listarPorUsuario(user);
        double gastosTotais = transacoes.stream()
                .filter(t -> t.getTipo() == TipoTransacao.APOSTA)
                .mapToDouble(Transacao::getValor)
                .sum();

        // Buscar badges do usuário e mapear para DTO
        List<BadgeDto> badgeDtos = badgeService.listarPorUsuario(user)
                .stream()
                .map(b -> {
                    BadgeDto dto = new BadgeDto();
                    dto.setId(b.getId());
                    dto.setNome(b.getNome());
                    dto.setDescricao(b.getDescricao());
                    dto.setDataConquista(b.getDataConquista());
                    dto.setUserId(user.getId());
                    return dto;
                })
                .collect(Collectors.toList());

        DashboardDto dto = new DashboardDto();
        dto.setSaldo(user.getSaldo());
        dto.setGastosTotais(gastosTotais);
        dto.setDiasSemApostar(user.getDiasSemApostar());
        dto.setXp(user.getXp());
        dto.setBadges(badgeDtos);

        return ResponseEntity.ok(dto);
    }
}

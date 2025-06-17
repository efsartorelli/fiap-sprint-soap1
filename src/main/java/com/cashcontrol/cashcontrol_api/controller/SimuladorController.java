package com.cashcontrol.cashcontrol_api.controller;

import com.cashcontrol.cashcontrol_api.dto.SimuladorRequestDto;
import com.cashcontrol.cashcontrol_api.dto.SimuladorResponseDto;
import com.cashcontrol.cashcontrol_api.model.User;
import com.cashcontrol.cashcontrol_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * Controller responsável pela simulação de cenários "apostei vs investi".
 * <p>
 * Este endpoint compara o resultado financeiro de apostar ou investir um valor em determinado período.
 * É protegido por autenticação via token.
 * </p>
 */
@RestController
@RequestMapping("/api/simulador")
public class SimuladorController {

    @Autowired
    private UserService userService;

    /**
     * Realiza a simulação "apostei vs investi" para o usuário autenticado.
     *
     * @param userId  ID do usuário autenticado.
     * @param request Objeto com valor inicial, meses e rendimento.
     * @param token   Token do usuário (enviado no header Authorization).
     * @return Resultado da simulação: valor final apostando vs investindo.
     *
     * <p>Exemplo de requisição:
     * <pre>
     * POST /api/simulador/usuario/1
     * Header: Authorization: seu-token-aqui
     * Body:
     * {
     *   "valorInicial": 500,
     *   "meses": 12,
     *   "rendimentoMensal": 0.008
     * }
     * </pre>
     * </p>
     *
     * <p>Exemplo de resposta:
     * <pre>
     * {
     *   "resultadoApostando": 50.0,
     *   "resultadoInvestindo": 548.5
     * }
     * </pre>
     * </p>
     */
    // @Operation(summary = "Simulador Apostei vs Investi", description = "Compara o resultado de apostar ou investir determinado valor durante um período. Protegido por token.")
    @PostMapping("/usuario/{userId}")
    public ResponseEntity<SimuladorResponseDto> simular(
            @PathVariable Long userId,
            @RequestBody SimuladorRequestDto request,
            @RequestHeader("Authorization") String token
    ) {
        // Valida usuário e token
        Optional<User> userOpt = userService.buscarPorId(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).build();
        }
        User user = userOpt.get();
        if (user.getToken() == null || !user.getToken().equals(token)) {
            return ResponseEntity.status(401).build();
        }

        double valor = request.getValorInicial();
        int meses = request.getMeses();
        double rendimento = request.getRendimentoMensal() != null ? request.getRendimentoMensal() : 0.008; // 0,8% padrão

        // Cenário Apostando: supõe perda de 90%
        double resultadoApostando = valor * 0.10;

        // Cenário Investindo: juros compostos
        double resultadoInvestindo = valor * Math.pow(1 + rendimento, meses);

        SimuladorResponseDto resposta = new SimuladorResponseDto();
        resposta.setResultadoApostando(resultadoApostando);
        resposta.setResultadoInvestindo(resultadoInvestindo);
        return ResponseEntity.ok(resposta);
    }
}

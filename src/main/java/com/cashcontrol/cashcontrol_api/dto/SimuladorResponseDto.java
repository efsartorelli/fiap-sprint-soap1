package com.cashcontrol.cashcontrol_api.dto;

import lombok.Data;

/**
 * Data Transfer Object para resposta da simulação de aposta vs investimento.
 *
 * Usado como retorno no endpoint de simulação (/api/simulador).
 *
 * <p>
 * Exemplo de resposta:
 * <pre>
 * {
 *   "resultadoApostando": 50.0,
 *   "resultadoInvestindo": 510.0
 * }
 * </pre>
 * </p>
 */
@Data
public class SimuladorResponseDto {
    /**
     * Valor final ao apostar (exemplo: geralmente supõe-se 90% de perda).
     */
    private Double resultadoApostando;

    /**
     * Valor final ao investir (com juros compostos pelo rendimento mensal informado).
     */
    private Double resultadoInvestindo;
}

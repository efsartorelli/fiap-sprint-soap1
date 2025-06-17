package com.cashcontrol.cashcontrol_api.dto;

import lombok.Data;

/**
 * Data Transfer Object para requisição de simulação de investimento ou aposta.
 *
 * Usado como payload no endpoint de simulação (/api/simulador).
 *
 * <p>
 * Exemplo de requisição:
 * <pre>
 * {
 *   "valorInicial": 500.0,
 *   "meses": 12,
 *   "rendimentoMensal": 0.008
 * }
 * </pre>
 * </p>
 */
@Data
public class SimuladorRequestDto {
    /**
     * Valor inicial a ser investido ou apostado.
     */
    private Double valorInicial;

    /**
     * Quantidade de meses a serem simulados.
     */
    private Integer meses;

    /**
     * Rendimento mensal (exemplo: 0.008 equivale a 0,8% ao mês, próximo ao CDI).
     * Pode ser null, caso em que o sistema usará um valor padrão.
     */
    private Double rendimentoMensal; // ex: 0.008 para 0,8% ao mês (CDI)
}

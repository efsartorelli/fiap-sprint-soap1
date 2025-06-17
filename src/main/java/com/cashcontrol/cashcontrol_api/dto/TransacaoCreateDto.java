package com.cashcontrol.cashcontrol_api.dto;

import lombok.Data;

/**
 * Data Transfer Object para requisição de criação de transação.
 *
 * Usado como payload para criar novas apostas, investimentos ou retornos.
 *
 * <p>
 * Exemplo de requisição:
 * <pre>
 * {
 *   "valor": 100.0,
 *   "tipo": "APOSTA",
 *   "userId": 3
 * }
 * </pre>
 * </p>
 */
@Data
public class TransacaoCreateDto {
    /**
     * Valor da transação (aposta, investimento ou retorno).
     */
    private Double valor;

    /**
     * Tipo da transação.
     * Valores aceitos: "APOSTA", "INVESTIMENTO", "RETORNO".
     */
    private String tipo;

    /**
     * Identificador do usuário que está realizando a transação.
     */
    private Long userId;
}

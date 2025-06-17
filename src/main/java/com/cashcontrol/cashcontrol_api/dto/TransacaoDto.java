package com.cashcontrol.cashcontrol_api.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Data Transfer Object para representação de uma transação na resposta da API.
 *
 * Utilizado para exibir detalhes de apostas, investimentos e retornos.
 *
 * <p>
 * Exemplo de resposta:
 * <pre>
 * {
 *   "id": 42,
 *   "valor": 150.0,
 *   "tipo": "APOSTA",
 *   "data": "2025-06-16T20:55:00",
 *   "status": "REALIZADA",
 *   "userId": 3
 * }
 * </pre>
 * </p>
 */
@Data
public class TransacaoDto {
    /**
     * Identificador único da transação.
     */
    private Long id;

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
     * Data e hora em que a transação foi realizada.
     */
    private LocalDateTime data;

    /**
     * Status da transação (exemplo: "REALIZADA", "CANCELADA", etc).
     */
    private String status;

    /**
     * Identificador do usuário responsável pela transação.
     */
    private Long userId;
}

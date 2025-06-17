package com.cashcontrol.cashcontrol_api.dto;

import lombok.Data;
import java.time.LocalDate;

/**
 * Data Transfer Object para representar conquistas (badges) do usuário.
 *
 * Utilizado para transferir dados entre a API e o frontend/app,
 * sem expor entidades do banco diretamente.
 *
 * <p>
 * Exemplo de resposta:
 * <pre>
 * {
 *   "id": 1,
 *   "nome": "1B XP",
 *   "descricao": "Primeiro dia sem apostar",
 *   "dataConquista": "2025-06-10",
 *   "userId": 3
 * }
 * </pre>
 * </p>
 */
@Data
public class BadgeDto {
    /**
     * Identificador único da badge.
     */
    private Long id;

    /**
     * Nome da badge.
     */
    private String nome;

    /**
     * Descrição detalhada da conquista.
     */
    private String descricao;

    /**
     * Data em que a badge foi conquistada pelo usuário.
     */
    private LocalDate dataConquista;

    /**
     * ID do usuário dono da badge.
     */
    private Long userId;
}

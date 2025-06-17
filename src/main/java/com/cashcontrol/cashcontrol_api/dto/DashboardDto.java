package com.cashcontrol.cashcontrol_api.dto;

import lombok.Data;
import java.util.List;

/**
 * Data Transfer Object para dados do dashboard do usuário.
 *
 * <p>
 * Usado para exibir informações resumidas e indicadores no painel principal do app,
 * como saldo, XP, badges, dias sem apostar, etc.
 * </p>
 *
 * <p>
 * Exemplo de resposta:
 * <pre>
 * {
 *   "saldo": 1200.0,
 *   "gastosTotais": 350.0,
 *   "diasSemApostar": 5,
 *   "xp": 250,
 *   "badges": [
 *     {
 *       "id": 1,
 *       "nome": "1B XP",
 *       "descricao": "Primeiro dia sem apostar",
 *       "dataConquista": "2025-06-10",
 *       "userId": 3
 *     }
 *   ]
 * }
 * </pre>
 * </p>
 */
@Data
public class DashboardDto {
    /**
     * Saldo atual do usuário.
     */
    private Double saldo;

    /**
     * Soma total dos valores apostados pelo usuário.
     */
    private Double gastosTotais;

    /**
     * Quantidade de dias consecutivos sem apostar.
     */
    private Integer diasSemApostar;

    /**
     * Pontuação de experiência (XP) acumulada pelo usuário.
     */
    private Integer xp;

    /**
     * Lista de conquistas (badges) do usuário.
     */
    private List<BadgeDto> badges;

    // Acrescente outros campos conforme desejar (exemplo: metas, gráficos, etc.)
}

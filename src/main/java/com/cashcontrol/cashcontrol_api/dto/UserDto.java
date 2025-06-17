package com.cashcontrol.cashcontrol_api.dto;

import lombok.Data;

/**
 * Data Transfer Object para retorno de informações de usuário.
 *
 * Usado para transferir dados do usuário em respostas da API,
 * como no dashboard, detalhes do perfil e login.
 *
 * <p>
 * Exemplo de resposta:
 * <pre>
 * {
 *   "id": 3,
 *   "nome": "Maria Silva",
 *   "email": "maria@email.com",
 *   "rendaMensal": 2500.0,
 *   "perfilRisco": "moderado",
 *   "limiteMensalAposta": 250.0,
 *   "saldo": 1200.0,
 *   "xp": 300,
 *   "diasSemApostar": 5
 * }
 * </pre>
 * </p>
 */
@Data
public class UserDto {
    /**
     * Identificador único do usuário.
     */
    private Long id;

    /**
     * Nome completo do usuário.
     */
    private String nome;

    /**
     * E-mail do usuário.
     */
    private String email;

    /**
     * Renda mensal informada pelo usuário.
     */
    private Double rendaMensal;

    /**
     * Perfil de risco do usuário (baixo, moderado, alto).
     */
    private String perfilRisco;

    /**
     * Limite mensal para apostas, definido com base na renda.
     */
    private Double limiteMensalAposta;

    /**
     * Saldo atual do usuário na plataforma.
     */
    private Double saldo;

    /**
     * Pontuação de experiência (XP) acumulada.
     */
    private Integer xp;

    /**
     * Número de dias consecutivos sem apostar.
     */
    private Integer diasSemApostar;
}

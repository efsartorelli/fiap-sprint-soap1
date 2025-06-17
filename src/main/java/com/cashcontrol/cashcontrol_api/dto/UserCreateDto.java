package com.cashcontrol.cashcontrol_api.dto;

import lombok.Data;

/**
 * Data Transfer Object para requisição de criação (registro) de usuário.
 *
 * Usado como payload nos endpoints de cadastro e login.
 *
 * <p>
 * Exemplo de requisição:
 * <pre>
 * {
 *   "nome": "Maria Silva",
 *   "email": "maria@email.com",
 *   "senha": "minhasenha123",
 *   "rendaMensal": 2500.0
 * }
 * </pre>
 * </p>
 */
@Data
public class UserCreateDto {
    /**
     * Nome completo do usuário.
     */
    private String nome;

    /**
     * E-mail do usuário (deve ser único).
     */
    private String email;

    /**
     * Senha do usuário.
     */
    private String senha;

    /**
     * Renda mensal declarada pelo usuário (utilizada para cálculos de limites).
     */
    private Double rendaMensal;
}

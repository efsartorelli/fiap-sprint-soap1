package com.cashcontrol.cashcontrol_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Classe de configuração de segurança da aplicação.
 *
 * <p>
 * Esta configuração define as regras de autenticação e autorização das rotas da API,
 * permitindo acesso público apenas para cadastro e login, e exigindo autenticação para os demais endpoints.
 * </p>
 *
 * <ul>
 *     <li>Desabilita CSRF para facilitar testes com Postman/Insomnia (não recomendado para produção).</li>
 *     <li>Permite acesso a <b>/api/users/register</b> e <b>/api/users/login</b> sem autenticação.</li>
 *     <li>Exige autenticação HTTP Basic para todos os outros endpoints.</li>
 * </ul>
 */
@Configuration
public class SecurityConfig {

    /**
     * Define o filtro de segurança para requisições HTTP.
     *
     * @param http objeto de configuração do Spring Security
     * @return SecurityFilterChain configurada
     * @throws Exception caso haja erro de configuração
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(); // Permite testar com Basic Auth no Postman

        return http.build();
    }
}

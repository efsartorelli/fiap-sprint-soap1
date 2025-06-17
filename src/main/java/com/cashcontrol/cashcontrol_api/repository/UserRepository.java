package com.cashcontrol.cashcontrol_api.repository;

import com.cashcontrol.cashcontrol_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório JPA para entidade User.
 *
 * Fornece operações CRUD e métodos customizados para buscas por email e token.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca um usuário pelo email.
     *
     * @param email email do usuário
     * @return Optional contendo o usuário, se encontrado
     */
    Optional<User> findByEmail(String email);

    /**
     * Busca um usuário pelo token de autenticação.
     *
     * @param token token do usuário
     * @return Optional contendo o usuário, se encontrado
     */
    Optional<User> findByToken(String token);

    /**
     * Verifica se um usuário existe com o email informado.
     *
     * @param email email a ser verificado
     * @return true se existir usuário com o email, false caso contrário
     */
    boolean existsByEmail(String email);
}

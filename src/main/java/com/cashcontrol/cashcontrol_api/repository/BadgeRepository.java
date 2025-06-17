package com.cashcontrol.cashcontrol_api.repository;

import com.cashcontrol.cashcontrol_api.model.Badge;
import com.cashcontrol.cashcontrol_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório JPA para entidade Badge.
 *
 * Fornece operações CRUD básicas e consultas customizadas para badges.
 */
public interface BadgeRepository extends JpaRepository<Badge, Long> {

    /**
     * Busca todas as badges associadas a um usuário específico.
     *
     * @param user usuário dono das badges
     * @return lista de badges do usuário
     */
    List<Badge> findByUser(User user);
}

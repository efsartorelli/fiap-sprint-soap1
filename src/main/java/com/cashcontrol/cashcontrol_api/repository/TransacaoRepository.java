package com.cashcontrol.cashcontrol_api.repository;

import com.cashcontrol.cashcontrol_api.model.Transacao;
import com.cashcontrol.cashcontrol_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório JPA para a entidade Transacao.
 *
 * Fornece operações CRUD básicas e consultas customizadas relacionadas às transações.
 */
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    /**
     * Busca todas as transações associadas a um usuário específico.
     *
     * @param user o usuário dono das transações
     * @return lista de transações do usuário
     */
    List<Transacao> findByUser(User user);
}

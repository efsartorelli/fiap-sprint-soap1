package com.cashcontrol.cashcontrol_api.service;

import com.cashcontrol.cashcontrol_api.model.Badge;
import com.cashcontrol.cashcontrol_api.model.User;
import com.cashcontrol.cashcontrol_api.repository.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciar operações relacionadas às badges (conquistas).
 *
 * Responsável pela lógica de negócio e interação com o repositório de badges.
 */
@Service
public class BadgeService {

    @Autowired
    private BadgeRepository badgeRepository;

    /**
     * Lista todas as badges cadastradas.
     *
     * @return lista de todas as badges
     */
    public List<Badge> listarTodos() {
        return badgeRepository.findAll();
    }

    /**
     * Lista todas as badges de um usuário específico.
     *
     * @param user usuário dono das badges
     * @return lista de badges do usuário
     */
    public List<Badge> listarPorUsuario(User user) {
        return badgeRepository.findByUser(user);
    }

    /**
     * Busca uma badge pelo seu ID.
     *
     * @param id identificador da badge
     * @return Optional com a badge, se encontrada
     */
    public Optional<Badge> buscarPorId(Long id) {
        return badgeRepository.findById(id);
    }

    /**
     * Salva uma nova badge ou atualiza uma existente.
     *
     * @param badge objeto badge a ser salvo
     * @return badge salva no banco
     */
    public Badge salvar(Badge badge) {
        return badgeRepository.save(badge);
    }

    /**
     * Deleta uma badge pelo seu ID.
     *
     * @param id identificador da badge a ser deletada
     */
    public void deletar(Long id) {
        badgeRepository.deleteById(id);
    }
}

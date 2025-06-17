package com.cashcontrol.cashcontrol_api.service;

import com.cashcontrol.cashcontrol_api.model.Badge;
import com.cashcontrol.cashcontrol_api.model.Transacao;
import com.cashcontrol.cashcontrol_api.model.TipoTransacao;
import com.cashcontrol.cashcontrol_api.model.User;
import com.cashcontrol.cashcontrol_api.repository.BadgeRepository;
import com.cashcontrol.cashcontrol_api.repository.TransacaoRepository;
import com.cashcontrol.cashcontrol_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelas operações relacionadas às transações financeiras.
 * Controla atualizações de saldo, XP e badges dos usuários.
 */
@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BadgeRepository badgeRepository;

    /**
     * Lista todas as transações registradas.
     *
     * @return lista com todas as transações
     */
    public List<Transacao> listarTodas() {
        return transacaoRepository.findAll();
    }

    /**
     * Lista todas as transações feitas por um usuário específico.
     *
     * @param user usuário dono das transações
     * @return lista de transações do usuário
     */
    public List<Transacao> listarPorUsuario(User user) {
        return transacaoRepository.findByUser(user);
    }

    /**
     * Busca uma transação pelo seu ID.
     *
     * @param id identificador da transação
     * @return Optional contendo a transação, se encontrada
     */
    public Optional<Transacao> buscarPorId(Long id) {
        return transacaoRepository.findById(id);
    }

    /**
     * Salva uma transação e atualiza o estado do usuário conforme o tipo da transação.
     * - Para apostas, deduz o valor do saldo e reseta dias sem apostar.
     * - Para investimentos, incrementa XP.
     * - Para retorno, acrescenta saldo.
     * Também atribui badges de acordo com dias sem apostar.
     *
     * @param transacao transação a ser salva
     * @return transação salva no banco
     */
    public Transacao salvar(Transacao transacao) {
        User user = transacao.getUser();

        // Atualiza saldo e XP baseado no tipo da transação
        if (transacao.getTipo() == TipoTransacao.APOSTA) {
            user.setSaldo(user.getSaldo() - transacao.getValor());
            user.setDiasSemApostar(0);
            // Opcional: perder XP ao apostar
            // user.setXp(Math.max(0, user.getXp() - 5));
        } else if (transacao.getTipo() == TipoTransacao.INVESTIMENTO) {
            user.setXp(user.getXp() + 5);
        } else if (transacao.getTipo() == TipoTransacao.RETORNO) {
            user.setSaldo(user.getSaldo() + transacao.getValor());
        }

        // Incrementa dias sem apostar e XP se não for aposta
        if (transacao.getTipo() != TipoTransacao.APOSTA) {
            user.setDiasSemApostar(user.getDiasSemApostar() + 1);
            user.setXp(user.getXp() + 10);
        }

        // Adiciona badges ao atingir metas de dias sem apostar
        if (user.getDiasSemApostar() == 7 && !badgeJaConquistado(user, "7 dias sem apostar")) {
            Badge badge = new Badge();
            badge.setNome("7 dias sem apostar");
            badge.setDescricao("Parabéns! Você ficou uma semana sem apostas.");
            badge.setDataConquista(LocalDate.now());
            badge.setUser(user);
            badgeRepository.save(badge);
        }
        if (user.getDiasSemApostar() == 30 && !badgeJaConquistado(user, "30 dias sem apostar")) {
            Badge badge = new Badge();
            badge.setNome("30 dias sem apostar");
            badge.setDescricao("Incrível! Um mês sem apostas!");
            badge.setDataConquista(LocalDate.now());
            badge.setUser(user);
            badgeRepository.save(badge);
        }

        userRepository.save(user);
        return transacaoRepository.save(transacao);
    }

    /**
     * Deleta uma transação pelo seu ID.
     *
     * @param id identificador da transação a ser deletada
     */
    public void deletar(Long id) {
        transacaoRepository.deleteById(id);
    }

    /**
     * Verifica se o usuário já conquistou uma badge específica para evitar duplicação.
     *
     * @param user usuário em questão
     * @param nomeBadge nome da badge a verificar
     * @return true se a badge já foi conquistada, false caso contrário
     */
    private boolean badgeJaConquistado(User user, String nomeBadge) {
        List<Badge> badges = badgeRepository.findByUser(user);
        return badges.stream().anyMatch(b -> b.getNome().equalsIgnoreCase(nomeBadge));
    }
}

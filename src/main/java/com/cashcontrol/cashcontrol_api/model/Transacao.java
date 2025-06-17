package com.cashcontrol.cashcontrol_api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Entidade que representa uma transação financeira do usuário.
 *
 * <p>
 * Pode ser uma aposta, um investimento ou um retorno (prêmio/resgate).
 * Relacionada a um usuário.
 * </p>
 *
 * Exemplos de uso: registro do histórico financeiro, controle de limites, estatísticas de apostas/investimentos.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transacao {

    /**
     * Identificador único da transação.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Usuário ao qual a transação pertence.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * Valor financeiro da transação.
     */
    private Double valor;

    /**
     * Tipo da transação (aposta, investimento, retorno).
     */
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    /**
     * Data e hora em que a transação foi realizada.
     */
    private LocalDateTime data;

    /**
     * Status atual da transação (exemplo: "PENDENTE", "REALIZADA", "CANCELADA").
     */
    private String status;
}

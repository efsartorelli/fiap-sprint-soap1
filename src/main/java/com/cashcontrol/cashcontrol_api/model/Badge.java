package com.cashcontrol.cashcontrol_api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Entidade que representa uma badge/conquista do usuário na plataforma.
 *
 * <p>
 * Cada badge simboliza um marco, feito ou recompensa relacionada ao progresso do usuário,
 * como dias sem apostar, atingir XP, entre outros.
 * </p>
 *
 * Exemplos de uso: exibição de conquistas no dashboard, sistema de gamificação, etc.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Badge {

    /**
     * Identificador único da badge.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome da badge/conquista (exemplo: "1B XP", "10 dias sem apostar").
     */
    private String nome;

    /**
     * Descrição detalhada da conquista.
     */
    private String descricao;

    /**
     * Data em que a badge foi conquistada pelo usuário.
     */
    private LocalDate dataConquista;

    /**
     * Usuário ao qual a badge pertence.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}

package com.cashcontrol.cashcontrol_api.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "usuario")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nome;

    @Column
    private LocalDateTime cooldownAposta;  // Campo para cooldown de apostas

    @Column(nullable = false)
    private String senha;

    @Column
    private Double rendaMensal;

    @Column
    private String perfilRisco; // Ex: "baixo", "moderado", "alto"

    @Column
    private Double limiteMensalAposta;

    @Column
    private Double saldo;

    @Column
    private Integer xp;

    @Column
    private Integer diasSemApostar;

    @Column
    private String token;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transacao> transacoes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Badge> badges;
}

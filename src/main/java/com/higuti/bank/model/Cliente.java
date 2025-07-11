package com.higuti.bank.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="clients")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String documento;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClienteCategoria categoria;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<Conta> contas = new HashSet<>();

    public void addConta(Conta conta) {
        this.contas.add(conta);
        conta.setCliente(this);
    }

    public void removeConta(Conta conta) {
        this.contas.remove(conta);
        conta.setCliente(null);
    }
}

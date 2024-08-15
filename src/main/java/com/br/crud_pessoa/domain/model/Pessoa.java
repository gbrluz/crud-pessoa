package com.br.crud_pessoa.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity (name = "pessoa")
@Table (name = "pessoa")
@Getter
@Setter
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column
    private LocalDate dataNascimento;

    @Column(nullable = false, unique = true)
    private String cpf;

    @OneToMany(mappedBy = "pessoa")
    private List<Endereco> enderecos;


}

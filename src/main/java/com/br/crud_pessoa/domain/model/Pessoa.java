package com.br.crud_pessoa.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity (name = "pessoa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Past
    private LocalDate dataNascimento;

    @CPF
    @NotBlank
    private String cpf;

    @ElementCollection
    private List<Long> enderecoIds = new ArrayList<>();

    public Pessoa(String nome, LocalDate dataNascimento, String cpf, List<Long> enderecoIds) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.enderecoIds = enderecoIds;
    }
}

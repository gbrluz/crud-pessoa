package com.br.crud_pessoa.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity (name = "pessoa")
@Getter
@Setter
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    private LocalDate dataNascimento;

    @NotBlank
    private String cpf;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private List<Endereco> enderecos;

    public int getIdade() {
        return Period.between(this.dataNascimento, LocalDate.now()).getYears();
    }


}

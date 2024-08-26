package com.br.crud_pessoa.domain.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class PessoaResponseDTO {
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private int idade;
    private List<EnderecoDTO> enderecos;
}

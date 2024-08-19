package com.br.crud_pessoa.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class PessoaDTO {
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private List<EnderecoDTO> enderecos;
}

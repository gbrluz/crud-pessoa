package com.br.crud_pessoa.domain.model.dto;

import java.time.LocalDate;
import java.util.List;

public record PessoaDTO(String nome, String cpf, LocalDate dataNascimento, List<Long> enderecoIds) {}


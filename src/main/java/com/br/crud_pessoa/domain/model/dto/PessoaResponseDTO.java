package com.br.crud_pessoa.domain.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

public record PessoaResponseDTO(Long id, String nome, String cpf, LocalDate dataNascimento, int idade, List<EnderecoResponseDTO> enderecos) {}


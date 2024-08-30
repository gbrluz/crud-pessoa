package com.br.crud_pessoa.domain.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public record EnderecoDTO(@NotBlank(message = "Rua não pode estar em branco") String rua,
                          String numero,
                          String bairro,
                          String cidade,
                          String estado,
                          String cep) {}


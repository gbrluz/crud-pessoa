package com.br.crud_pessoa.domain.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoDTO {
    @NotBlank
    private String rua;

    @NotBlank
    private String numero;

    @NotBlank
    private String bairro;

    @NotBlank
    private String cidade;

    @NotBlank
    private String estado;

    @NotBlank
    private String cep;

    @NotBlank
    private boolean principal;
}

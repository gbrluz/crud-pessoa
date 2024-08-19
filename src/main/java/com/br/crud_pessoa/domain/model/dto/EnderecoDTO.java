package com.br.crud_pessoa.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoDTO {
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private boolean principal;
}

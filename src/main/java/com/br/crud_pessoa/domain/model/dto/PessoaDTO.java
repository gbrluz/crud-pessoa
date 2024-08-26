package com.br.crud_pessoa.domain.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Builder
public class PessoaDTO {

    @NotBlank
    private String nome;

    @CPF
    @NotBlank
    private String cpf;

    @Past
    @NotBlank
    private LocalDate dataNascimento;

    private List<EnderecoDTO> enderecos;
}

package com.br.crud_pessoa.stub;

import com.br.crud_pessoa.domain.model.Pessoa;
import com.br.crud_pessoa.domain.model.dto.PessoaDTO;

import java.time.LocalDate;
import java.util.List;

public class PessoaStub {
    public Pessoa criarPessoaStub() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Gabriel Luz");
        pessoa.setCpf("637.513.148-06");
        pessoa.setDataNascimento(LocalDate.of(1999, 1, 1));
        pessoa.setEnderecoIds(List.of(1L));
        return pessoa;
    }

    public PessoaDTO criarPessoaDTOStub() {
        return new PessoaDTO(
                "Gabriel Luz",
                "637.513.148-06",
                LocalDate.of(1999, 1, 1),
                List.of(1L)
        );
    }
}

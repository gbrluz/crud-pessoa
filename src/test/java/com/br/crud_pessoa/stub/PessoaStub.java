package com.br.crud_pessoa.stub;

import com.br.crud_pessoa.domain.model.Pessoa;
import com.br.crud_pessoa.domain.model.dto.PessoaDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PessoaStub {
    public static Pessoa criarPessoaStub() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Gabriel");
        pessoa.setCpf("12345678900");
        pessoa.setDataNascimento(LocalDate.of(1999, 2, 13));
        pessoa.setEnderecoIds(new ArrayList<>());
        return pessoa;
    }

    public static PessoaDTO criarPessoaDTOStub() {
        return new PessoaDTO("Gabriel",
                "12345678900",
                LocalDate.of(1999, 2, 13),
                Collections.emptyList());
    }
}

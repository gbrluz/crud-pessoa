package com.br.crud_pessoa.stub;

import com.br.crud_pessoa.domain.model.Endereco;
import com.br.crud_pessoa.domain.model.dto.EnderecoDTO;

public class EnderecoStub {
    public static Endereco criarEnderecoStub() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setRua("Rua X");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("Porto Alegre");
        endereco.setEstado("RS");
        endereco.setCep("01000-000");
        return endereco;
    }

    public static EnderecoDTO criarEnderecoDTOStub() {
        return new EnderecoDTO("Rua 1", "100", "Bairro 1", "Cidade 1", "Estado 1", "12345-678");
    }
}

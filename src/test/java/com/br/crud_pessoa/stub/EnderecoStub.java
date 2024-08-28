package com.br.crud_pessoa.stub;

import com.br.crud_pessoa.domain.model.Endereco;

public class EnderecoStub {
    public Endereco criarEnderecoStub() {
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
}

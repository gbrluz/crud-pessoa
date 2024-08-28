package com.br.crud_pessoa.v1.service;

import com.br.crud_pessoa.domain.model.Endereco;
import com.br.crud_pessoa.domain.model.dto.EnderecoDTO;
import com.br.crud_pessoa.domain.model.dto.EnderecoResponseDTO;
import com.br.crud_pessoa.domain.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public EnderecoResponseDTO criarEndereco(EnderecoDTO enderecoDTO) {
        Endereco endereco = new Endereco();
        endereco.setRua(enderecoDTO.rua());
        endereco.setNumero(enderecoDTO.numero());
        endereco.setBairro(enderecoDTO.bairro());
        endereco.setCidade(enderecoDTO.cidade());
        endereco.setEstado(enderecoDTO.estado());
        endereco.setCep(enderecoDTO.cep());

        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        return convertToResponseDTO(enderecoSalvo);
    }

    private EnderecoResponseDTO convertToResponseDTO(Endereco endereco) {
        return new EnderecoResponseDTO(
                endereco.getId(),
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep()
        );
    }
}

package com.br.crud_pessoa.v1.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.br.crud_pessoa.domain.model.Endereco;
import com.br.crud_pessoa.domain.model.dto.EnderecoDTO;
import com.br.crud_pessoa.domain.model.dto.EnderecoResponseDTO;
import com.br.crud_pessoa.domain.repository.EnderecoRepository;
import com.br.crud_pessoa.stub.EnderecoStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    private EnderecoDTO enderecoDTO;
    private Endereco endereco;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        enderecoDTO = EnderecoStub.criarEnderecoDTOStub();

        endereco = new Endereco();
        endereco.setId(1L);
        endereco.setRua(enderecoDTO.rua());
        endereco.setNumero(enderecoDTO.numero());
        endereco.setBairro(enderecoDTO.bairro());
        endereco.setCidade(enderecoDTO.cidade());
        endereco.setEstado(enderecoDTO.estado());
        endereco.setCep(enderecoDTO.cep());
    }

    @Test
    void criarEnderecoSuccess() {
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        EnderecoResponseDTO responseDTO = enderecoService.criarEndereco(enderecoDTO);

        assertNotNull(responseDTO);
        assertEquals(endereco.getId(), responseDTO.id());
        assertEquals(enderecoDTO.rua(), responseDTO.rua());
        assertEquals(enderecoDTO.numero(), responseDTO.numero());
        assertEquals(enderecoDTO.bairro(), responseDTO.bairro());
        assertEquals(enderecoDTO.cidade(), responseDTO.cidade());
        assertEquals(enderecoDTO.estado(), responseDTO.estado());
        assertEquals(enderecoDTO.cep(), responseDTO.cep());

        verify(enderecoRepository).save(any(Endereco.class));
    }
}


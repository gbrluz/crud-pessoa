package com.br.crud_pessoa.v1.service;

import com.br.crud_pessoa.domain.model.Endereco;
import com.br.crud_pessoa.domain.model.Pessoa;
import com.br.crud_pessoa.domain.model.dto.PessoaDTO;
import com.br.crud_pessoa.domain.model.dto.PessoaResponseDTO;
import com.br.crud_pessoa.domain.repository.PessoaRepository;
import com.br.crud_pessoa.exception.ResourceNotFoundException;
import com.br.crud_pessoa.stub.EnderecoStub;
import com.br.crud_pessoa.stub.PessoaStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    private Pessoa pessoa;
    private PessoaDTO pessoaDTO;

    private Endereco endereco;

    @BeforeEach
    void setUp() {
        pessoa = PessoaStub.criarPessoaStub();
        pessoaDTO = PessoaStub.criarPessoaDTOStub();
        endereco = EnderecoStub.criarEnderecoStub();
    }

    @Test
    void listarTodosSuccess() {
        Pageable pageable = PageRequest.of(0, 10);
        when(pessoaRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(pessoa)));

        Page<PessoaResponseDTO> result = pessoaService.listarTodos(pageable);

        assertEquals(1, result.getTotalElements());
        verify(pessoaRepository, times(1)).findAll(pageable);
    }

    @Test
    void obterPorIdSuccess() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));

        PessoaResponseDTO result = pessoaService.obterPorId(1L);

        assertEquals("Gabriel", result.nome());
        verify(pessoaRepository, times(1)).findById(1L);
    }

    @Test
    void obterPorIdException() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pessoaService.obterPorId(1L));
    }

    @Test
    void criarPessoaSuccess() {
        when(pessoaRepository.existsByCpf("12345678900")).thenReturn(false);
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        PessoaResponseDTO responseDTO = pessoaService.criarPessoa(pessoaDTO);

        assertNotNull(responseDTO);
        assertEquals(pessoaDTO.nome(), responseDTO.nome());
        assertEquals(pessoaDTO.cpf(), responseDTO.cpf());
        assertEquals(pessoaDTO.dataNascimento(), responseDTO.dataNascimento());

        verify(pessoaRepository).existsByCpf("12345678900");
        verify(pessoaRepository).save(any(Pessoa.class));
    }

    @Test
    void criarPessoaException() {
        when(pessoaRepository.existsByCpf(anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> pessoaService.criarPessoa(pessoaDTO));
        verify(pessoaRepository, times(1)).existsByCpf(pessoaDTO.cpf());
    }

    @Test
    void atualizarPessoaSuccess() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        PessoaResponseDTO result = pessoaService.atualizarPessoa(1L, pessoaDTO);

        assertEquals("Gabriel", result.nome());
        verify(pessoaRepository, times(1)).findById(1L);
        verify(pessoaRepository, times(1)).save(any(Pessoa.class));
    }

    @Test
    void atualizarPessoaException() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pessoaService.atualizarPessoa(1L, pessoaDTO));
    }

    @Test
    void excluirPessoaSuccess() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));

        pessoaService.excluirPessoa(1L);

        verify(pessoaRepository, times(1)).delete(pessoa);
    }

    @Test
    void excluirPessoaException() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pessoaService.excluirPessoa(1L));
    }
}
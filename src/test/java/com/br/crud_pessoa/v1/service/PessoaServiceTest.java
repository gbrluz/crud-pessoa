package com.br.crud_pessoa.v1.service;

import com.br.crud_pessoa.domain.model.Pessoa;
import com.br.crud_pessoa.domain.model.dto.PessoaDTO;
import com.br.crud_pessoa.domain.model.dto.PessoaResponseDTO;
import com.br.crud_pessoa.domain.repository.PessoaRepository;
import com.br.crud_pessoa.exception.ResourceNotFoundException;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    private Pessoa pessoa;
    private PessoaDTO pessoaDTO;

    @BeforeEach
    void setUp() {
        pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Gabriel");
        pessoa.setCpf("12345678900");
        pessoa.setDataNascimento(LocalDate.of(1999, 2, 13));
        pessoa.setEnderecos(new ArrayList<>());

        pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("Gabriel");
        pessoaDTO.setCpf("12345678900");
        pessoaDTO.setDataNascimento(LocalDate.of(1990, 1, 1));
        pessoaDTO.setEnderecos(Collections.emptyList());
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

        assertEquals("Gabriel", result.getNome());
        verify(pessoaRepository, times(1)).findById(1L);
    }

    @Test
    void obterPorIdException() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pessoaService.obterPorId(1L));
    }

    @Test
    void criarPessoaSuccess() {
        when(pessoaRepository.existsByCpf(anyString())).thenReturn(false);
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        PessoaResponseDTO result = pessoaService.criarPessoa(pessoaDTO);

        assertEquals("Gabriel", result.getNome());
        verify(pessoaRepository, times(1)).existsByCpf(pessoaDTO.getCpf());
        verify(pessoaRepository, times(1)).save(any(Pessoa.class));
    }

    @Test
    void criarPessoaException() {
        when(pessoaRepository.existsByCpf(anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> pessoaService.criarPessoa(pessoaDTO));
        verify(pessoaRepository, times(1)).existsByCpf(pessoaDTO.getCpf());
    }

    @Test
    void atualizarPessoaSuccess() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        PessoaResponseDTO result = pessoaService.atualizarPessoa(1L, pessoaDTO);

        assertEquals("Gabriel", result.getNome());
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
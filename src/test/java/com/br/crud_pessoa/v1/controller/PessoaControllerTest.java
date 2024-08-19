package com.br.crud_pessoa.v1.controller;

import com.br.crud_pessoa.domain.model.dto.PessoaDTO;
import com.br.crud_pessoa.domain.model.dto.PessoaResponseDTO;
import com.br.crud_pessoa.v1.service.PessoaService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PessoaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private PessoaController pessoaController;

    private PessoaResponseDTO pessoaResponseDTO;
    private PessoaDTO pessoaDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pessoaController).build();

        pessoaResponseDTO = new PessoaResponseDTO();
        pessoaResponseDTO.setId(1L);
        pessoaResponseDTO.setNome("Gabriel");

        pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("Gabriel");
    }

    @Test
    void listarTodosSuccess() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PessoaResponseDTO> pessoaPage = new PageImpl<>(Collections.singletonList(pessoaResponseDTO));

        when(pessoaService.listarTodos(any(Pageable.class))).thenReturn(pessoaPage);

        mockMvc.perform(get("/api/pessoas")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "nome,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));

        verify(pessoaService, times(1)).listarTodos(any(Pageable.class));
    }

    @Test
    void obterPorIdSuccess() throws Exception {
        when(pessoaService.obterPorId(anyLong())).thenReturn(pessoaResponseDTO);

        mockMvc.perform(get("/api/pessoas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Gabriel"));

        verify(pessoaService, times(1)).obterPorId(1L);
    }

    @Test
    void criarPessoaSuccess() throws Exception {
        when(pessoaService.criarPessoa(any(PessoaDTO.class))).thenReturn(pessoaResponseDTO);

        mockMvc.perform(post("/api/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Gabriel\",\"cpf\":\"12345678900\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Gabriel"));

        verify(pessoaService, times(1)).criarPessoa(any(PessoaDTO.class));
    }

    @Test
    void atualizarPessoaSuccess() throws Exception {
        when(pessoaService.atualizarPessoa(anyLong(), any(PessoaDTO.class))).thenReturn(pessoaResponseDTO);

        mockMvc.perform(put("/api/pessoas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Gabriel\",\"cpf\":\"12345678900\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Gabriel"));

        verify(pessoaService, times(1)).atualizarPessoa(anyLong(), any(PessoaDTO.class));
    }

    @Test
    void excluirPessoaSuccess() throws Exception {
        doNothing().when(pessoaService).excluirPessoa(anyLong());

        mockMvc.perform(delete("/api/pessoas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(pessoaService, times(1)).excluirPessoa(1L);
    }
}

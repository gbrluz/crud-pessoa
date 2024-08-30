package com.br.crud_pessoa.v1.controller;

import com.br.crud_pessoa.domain.model.Endereco;
import com.br.crud_pessoa.domain.model.Pessoa;
import com.br.crud_pessoa.domain.model.dto.PessoaDTO;
import com.br.crud_pessoa.domain.repository.EnderecoRepository;
import com.br.crud_pessoa.domain.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @BeforeEach
    void setUp() {
        pessoaRepository.deleteAll();
        enderecoRepository.deleteAll();
    }

    @Test
    void givenPessoasExist_whenListarTodos_thenReturnPagedPessoas() throws Exception {
        Endereco endereco1 = enderecoRepository.save(new Endereco("Rua 1", "100", "Bairro 1", "Cidade 1", "Estado 1", "12345-678"));
        Endereco endereco2 = enderecoRepository.save(new Endereco("Rua 2", "200", "Bairro 2", "Cidade 2", "Estado 2", "23456-789"));

        Pessoa pessoa1 = new Pessoa("Gabriel",  LocalDate.of(1990, 1, 1), "405.360.232-71", List.of(endereco1.getId()));
        Pessoa pessoa2 = new Pessoa("Marcelo",  LocalDate.of(1990, 1, 1), "887.238.287-49", List.of(endereco2.getId()));
        pessoaRepository.saveAll(List.of(pessoa1, pessoa2));

        mockMvc.perform(get("/api/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].nome").value("Gabriel"))
                .andExpect(jsonPath("$.content[1].nome").value("Marcelo"))
                .andExpect(jsonPath("$.content[0].enderecos[0].rua").value("Rua 1"))
                .andExpect(jsonPath("$.content[1].enderecos[0].rua").value("Rua 2"));
    }

    @Test
    void givenValidPessoa_whenCreatePessoa_thenReturnCreatedPessoa() throws Exception {
        Endereco endereco = enderecoRepository.save(new Endereco("Rua 1", "100", "Bairro 1", "Cidade 1", "Estado 1", "12345-678"));
        PessoaDTO pessoaDTO = new PessoaDTO("Gabriel", "405.360.232-71", LocalDate.of(1990, 1, 1), List.of(endereco.getId()));

        mockMvc.perform(post("/api/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Gabriel"))
                .andExpect(jsonPath("$.enderecos[0].rua").value("Rua 1"));
    }

    @Test
    void givenExistingPessoa_whenGetPessoaById_thenReturnPessoa() throws Exception {
        Endereco endereco = enderecoRepository.save(new Endereco("Rua 1", "100", "Bairro 1", "Cidade 1", "Estado 1", "12345-678"));
        Pessoa pessoa = new Pessoa("Gabriel",  LocalDate.of(1990, 1, 1), "405.360.232-71", List.of(endereco.getId()));
        pessoa = pessoaRepository.save(pessoa);

        mockMvc.perform(get("/api/pessoas/{id}", pessoa.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Gabriel"))
                .andExpect(jsonPath("$.enderecos[0].rua").value("Rua 1"));
    }

    @Test
    void givenInvalidPessoaId_whenGetPessoaById_thenReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/pessoas/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenExistingPessoa_whenUpdatePessoa_thenReturnUpdatedPessoa() throws Exception {
        Endereco endereco = enderecoRepository.save(new Endereco("Rua 1", "100", "Bairro 1", "Cidade 1", "Estado 1", "12345-678"));
        Pessoa pessoa = new Pessoa("Gabriel",  LocalDate.of(1990, 1, 1), "405.360.232-71", List.of(endereco.getId()));
        pessoa = pessoaRepository.save(pessoa);

        PessoaDTO updatedPessoaDTO = new PessoaDTO("Marcelo", "405.360.232-71", LocalDate.of(1990, 1, 1), List.of(endereco.getId()));

        mockMvc.perform(put("/api/pessoas/{id}", pessoa.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPessoaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Marcelo"));
    }

    @Test
    void givenExistingPessoa_whenDeletePessoa_thenReturnNoContent() throws Exception {
        Endereco endereco = enderecoRepository.save(new Endereco("Rua 1", "100", "Bairro 1", "Cidade 1", "Estado 1", "12345-678"));
        Pessoa pessoa = new Pessoa("Gabriel",  LocalDate.of(1990, 1, 1), "405.360.232-71", List.of(endereco.getId()));
        pessoa = pessoaRepository.save(pessoa);

        mockMvc.perform(delete("/api/pessoas/{id}", pessoa.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

package com.br.crud_pessoa.v1.controller;

import com.br.crud_pessoa.domain.model.dto.EnderecoDTO;
import com.br.crud_pessoa.domain.repository.EnderecoRepository;
import com.br.crud_pessoa.stub.EnderecoStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EnderecoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @BeforeEach
    void setUp() {
        enderecoRepository.deleteAll();
    }

    @Test
    void givenEnderecoDTO_whenCriarEndereco_thenEnderecoIsCreated() throws Exception {

        EnderecoDTO enderecoDTO = EnderecoStub.criarEnderecoDTOStub();

        // When
        ResultActions resultActions = mockMvc.perform(post("/enderecos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enderecoDTO)));

        // Then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.rua").value(enderecoDTO.rua()))
                .andExpect(jsonPath("$.numero").value(enderecoDTO.numero()))
                .andExpect(jsonPath("$.bairro").value(enderecoDTO.bairro()))
                .andExpect(jsonPath("$.cidade").value(enderecoDTO.cidade()))
                .andExpect(jsonPath("$.estado").value(enderecoDTO.estado()))
                .andExpect(jsonPath("$.cep").value(enderecoDTO.cep()));
    }
}


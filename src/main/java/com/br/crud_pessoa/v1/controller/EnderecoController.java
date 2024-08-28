package com.br.crud_pessoa.v1.controller;

import com.br.crud_pessoa.domain.model.dto.EnderecoDTO;
import com.br.crud_pessoa.domain.model.dto.EnderecoResponseDTO;
import com.br.crud_pessoa.v1.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<EnderecoResponseDTO> criarEndereco(@RequestBody EnderecoDTO enderecoDTO) {
        EnderecoResponseDTO enderecoCriado = enderecoService.criarEndereco(enderecoDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(enderecoCriado.id())
                .toUri();
        return ResponseEntity.created(location).body(enderecoCriado);
    }
}

package com.br.crud_pessoa.v1.controller;


import com.br.crud_pessoa.domain.model.dto.PessoaDTO;
import com.br.crud_pessoa.domain.model.dto.PessoaResponseDTO;
import com.br.crud_pessoa.v1.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<Page<PessoaResponseDTO>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(pessoaService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> obterPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pessoaService.obterPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PessoaResponseDTO> criarPessoa(@RequestBody PessoaDTO pessoaDTO) {
        return ResponseEntity.ok(pessoaService.criarPessoa(pessoaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> atualizarPessoa(@PathVariable Long id, @RequestBody PessoaDTO pessoaDTO) {
        return ResponseEntity.ok(pessoaService.atualizarPessoa(id, pessoaDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> excluirPessoa(@PathVariable Long id) {
        pessoaService.excluirPessoa(id);
        return ResponseEntity.noContent().build();
    }
}

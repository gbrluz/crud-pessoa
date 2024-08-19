package com.br.crud_pessoa.v1.controller;


import com.br.crud_pessoa.domain.model.dto.PessoaDTO;
import com.br.crud_pessoa.domain.model.dto.PessoaResponseDTO;
import com.br.crud_pessoa.v1.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public Page<PessoaResponseDTO> listarTodos(Pageable pageable) {
        return pessoaService.listarTodos(pageable);
    }

    @GetMapping("/{id}")
    public PessoaResponseDTO obterPorId(@PathVariable Long id) {
        return pessoaService.obterPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaResponseDTO criarPessoa(@RequestBody PessoaDTO pessoaDTO) {
        return pessoaService.criarPessoa(pessoaDTO);
    }

    @PutMapping("/{id}")
    public PessoaResponseDTO atualizarPessoa(@PathVariable Long id, @RequestBody PessoaDTO pessoaDTO) {
        return pessoaService.atualizarPessoa(id, pessoaDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirPessoa(@PathVariable Long id) {
        pessoaService.excluirPessoa(id);
    }
}

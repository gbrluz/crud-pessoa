package com.br.crud_pessoa.v1.controller;


import com.br.crud_pessoa.domain.model.Pessoa;
import com.br.crud_pessoa.domain.model.dto.PessoaDTO;
import com.br.crud_pessoa.domain.model.dto.PessoaResponseDTO;
import com.br.crud_pessoa.v1.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @Operation(summary = "Lista todas as pessoas.",
            responses = {
                    @ApiResponse(description = "Pessoas",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Pessoa.class))),
                    @ApiResponse(responseCode = "400", description = "Pessoas não encontradas.")})
    @GetMapping
    public ResponseEntity<Page<PessoaResponseDTO>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(pessoaService.listarTodos(pageable));
    }

    @Operation(summary = "Busca pessoa pelo ID.",
            responses = {
                    @ApiResponse(description = "Pessoa",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Pessoa.class))),
                    @ApiResponse(responseCode = "400", description = "ID inválido"),
                    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")})
    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> obterPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pessoaService.obterPorId(id));
    }

    @Operation(summary = "Cria uma pessoa.",
            responses = {
                    @ApiResponse(description = "Pessoa",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Pessoa.class))),
                    @ApiResponse(responseCode = "400", description = "Não foi possível criar uma pessoa")})
    @PostMapping
    public ResponseEntity<PessoaResponseDTO> criarPessoa(@RequestBody PessoaDTO pessoaDTO) {
        return ResponseEntity.ok(pessoaService.criarPessoa(pessoaDTO));
    }

    @Operation(summary = "Atualiza uma pessoa.",
            responses = {
                    @ApiResponse(description = "Pessoa",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Pessoa.class))),
                    @ApiResponse(responseCode = "400", description = "Não foi possível atualizar a pessoa."),
                    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")})
    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> atualizarPessoa(@PathVariable Long id, @RequestBody PessoaDTO pessoaDTO) {
        return ResponseEntity.ok(pessoaService.atualizarPessoa(id, pessoaDTO));
    }

    @Operation(summary = "Deleta uma pessoa.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content"),
                    @ApiResponse(responseCode = "400", description = "Não foi possível deletar pessoa."),
                    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPessoa(@PathVariable Long id) {
        pessoaService.excluirPessoa(id);
        return ResponseEntity.noContent().build();
    }
}

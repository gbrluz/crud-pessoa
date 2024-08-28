package com.br.crud_pessoa.v1.service;

import com.br.crud_pessoa.domain.model.Endereco;
import com.br.crud_pessoa.domain.model.Pessoa;
import com.br.crud_pessoa.domain.model.dto.EnderecoResponseDTO;
import com.br.crud_pessoa.domain.model.dto.PessoaDTO;
import com.br.crud_pessoa.domain.model.dto.PessoaResponseDTO;
import com.br.crud_pessoa.domain.repository.EnderecoRepository;
import com.br.crud_pessoa.domain.repository.PessoaRepository;
import com.br.crud_pessoa.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Page<PessoaResponseDTO> listarTodos(Pageable pageable) {
        return pessoaRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public PessoaResponseDTO obterPorId(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com ID: " + id));
        return convertToDTO(pessoa);
    }

    @Transactional
    public PessoaResponseDTO criarPessoa(PessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDTO.nome());
        pessoa.setCpf(pessoaDTO.cpf());
        pessoa.setDataNascimento(pessoaDTO.dataNascimento());

        List<Long> enderecoIds = pessoaDTO.enderecoIds();
        validarEnderecosExistentes(enderecoIds);

        pessoa.setEnderecoIds(enderecoIds);
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        return convertToDTO(pessoaSalva);
    }

    @Transactional
    public PessoaResponseDTO atualizarPessoa(Long id, PessoaDTO pessoaDTO) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com ID: " + id));

        pessoa.setNome(pessoaDTO.nome());
        pessoa.setCpf(pessoaDTO.cpf());
        pessoa.setDataNascimento(pessoaDTO.dataNascimento());

        List<Long> enderecoIds = pessoaDTO.enderecoIds();
        validarEnderecosExistentes(enderecoIds);

        pessoa.setEnderecoIds(enderecoIds);
        Pessoa pessoaAtualizada = pessoaRepository.save(pessoa);

        return convertToDTO(pessoaAtualizada);
    }

    @Transactional
    public void excluirPessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));
        pessoaRepository.delete(pessoa);
    }

    private void validarEnderecosExistentes(List<Long> enderecoIds) {
        enderecoIds.forEach(enderecoId -> {
            if (!enderecoRepository.existsById(enderecoId)) {
                throw new ResourceNotFoundException("Endereço não encontrado com ID: " + enderecoId);
            }
        });
    }

    private PessoaResponseDTO convertToDTO(Pessoa pessoa) {
        List<EnderecoResponseDTO> enderecosDTO = pessoa.getEnderecoIds().stream()
                .map(id -> {
                    Endereco endereco = enderecoRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com ID: " + id));
                    return new EnderecoResponseDTO(
                            endereco.getId(),
                            endereco.getRua(),
                            endereco.getNumero(),
                            endereco.getBairro(),
                            endereco.getCidade(),
                            endereco.getEstado(),
                            endereco.getCep()
                    );
                })
                .collect(Collectors.toList());

        return new PessoaResponseDTO(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getCpf(),
                pessoa.getDataNascimento(),
                calcularIdade(pessoa.getDataNascimento()),
                enderecosDTO
        );
    }

    private int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}

package com.br.crud_pessoa.v1.service;

import com.br.crud_pessoa.domain.model.Endereco;
import com.br.crud_pessoa.domain.model.Pessoa;
import com.br.crud_pessoa.domain.model.dto.EnderecoDTO;
import com.br.crud_pessoa.domain.model.dto.PessoaDTO;
import com.br.crud_pessoa.domain.model.dto.PessoaResponseDTO;
import com.br.crud_pessoa.domain.repository.PessoaRepository;
import com.br.crud_pessoa.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Page<PessoaResponseDTO> listarTodos(Pageable pageable) {
        return pessoaRepository.findAll(pageable)
                .map(this::convertToResponseDTO);
    }

    public PessoaResponseDTO obterPorId(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));
        return convertToResponseDTO(pessoa);
    }

    @Transactional
    public PessoaResponseDTO criarPessoa(PessoaDTO pessoaDTO) {
        if (pessoaRepository.existsByCpf(pessoaDTO.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        Pessoa pessoa = convertToEntity(pessoaDTO);
        Pessoa savedPessoa = pessoaRepository.save(pessoa);
        return convertToResponseDTO(savedPessoa);
    }

    @Transactional
    public PessoaResponseDTO atualizarPessoa(Long id, PessoaDTO pessoaDTO) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));

        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setCpf(pessoaDTO.getCpf());
        pessoa.setDataNascimento(pessoaDTO.getDataNascimento());

        pessoa.getEnderecos().clear();
        List<Endereco> enderecos = pessoaDTO.getEnderecos().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        pessoa.getEnderecos().addAll(enderecos);
        enderecos.forEach(e -> e.setPessoa(pessoa));

        Pessoa updatedPessoa = pessoaRepository.save(pessoa);
        return convertToResponseDTO(updatedPessoa);
    }

    @Transactional
    public void excluirPessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));
        pessoaRepository.delete(pessoa);
    }

    private Pessoa convertToEntity(PessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setCpf(pessoaDTO.getCpf());
        pessoa.setDataNascimento(pessoaDTO.getDataNascimento());

        List<Endereco> enderecos = pessoaDTO.getEnderecos().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        pessoa.setEnderecos(enderecos);

        enderecos.forEach(endereco -> endereco.setPessoa(pessoa));
        return pessoa;
    }

    private Endereco convertToEntity(EnderecoDTO enderecoDTO) {
        Endereco endereco = new Endereco();
        endereco.setRua(enderecoDTO.getRua());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());
        endereco.setCep(enderecoDTO.getCep());
        endereco.setPrincipal(enderecoDTO.isPrincipal());
        return endereco;
    }

    private PessoaResponseDTO convertToResponseDTO(Pessoa pessoa) {
        PessoaResponseDTO responseDTO = new PessoaResponseDTO();
        responseDTO.setId(pessoa.getId());
        responseDTO.setNome(pessoa.getNome());
        responseDTO.setCpf(pessoa.getCpf());
        responseDTO.setDataNascimento(pessoa.getDataNascimento());
        responseDTO.setIdade(pessoa.getIdade());

        List<EnderecoDTO> enderecos = pessoa.getEnderecos().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        responseDTO.setEnderecos(enderecos);

        return responseDTO;
    }

    private EnderecoDTO convertToDTO(Endereco endereco) {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setRua(endereco.getRua());
        dto.setNumero(endereco.getNumero());
        dto.setBairro(endereco.getBairro());
        dto.setCidade(endereco.getCidade());
        dto.setEstado(endereco.getEstado());
        dto.setCep(endereco.getCep());
        dto.setPrincipal(endereco.isPrincipal());
        return dto;
    }
}

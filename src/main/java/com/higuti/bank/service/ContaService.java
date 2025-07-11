package com.higuti.bank.service;

import com.higuti.bank.dto.ContaCreateDto;
import com.higuti.bank.dto.ContaResponseDto;
import com.higuti.bank.dto.ContaUpdateDto;
import com.higuti.bank.mapper.ContaMapper;
import com.higuti.bank.model.Cliente;
import com.higuti.bank.model.Conta;
import com.higuti.bank.model.ContaStatus;
import com.higuti.bank.repository.ClienteRepository;
import com.higuti.bank.repository.ContaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;
    private final ContaMapper contaMapper;

    public ContaService(ContaRepository contaRepository, ClienteRepository clienteRepository, ContaMapper contaMapper) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
        this.contaMapper = contaMapper;
    }

    @Transactional
    public ContaResponseDto criarConta(ContaCreateDto contaCreateDto) {
        Cliente cliente = clienteRepository.findById(contaCreateDto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com Id: " + contaCreateDto.getClienteId()));

        Conta conta = contaMapper.toEntity(contaCreateDto);
        conta.setCliente(cliente);
        conta.setContaStatus(ContaStatus.ATIVA);

        Conta contaSalva = contaRepository.save(conta);

        return contaMapper.toResponseDto(contaSalva);
    }

    @Transactional
    public ContaResponseDto buscarContaPorId(Long id) {
        return contaRepository.findById(id)
                .map(contaMapper::toResponseDto)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada com Id: "+ id));
    }

    @Transactional(readOnly = true)
    public List<ContaResponseDto> listarTodasContas() {
        return contaRepository.findAll().stream()
                .map(contaMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ContaResponseDto> buscarContasPorCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com Id: " + clienteId));

        return contaRepository.findByCliente(cliente).stream()
                .map(contaMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ContaResponseDto atualizarConta(Long id, ContaUpdateDto contaUpdateDto) {
        Conta contaExistente = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada para atualização com Id: " + id));

        contaMapper.updateContaFromDto(contaUpdateDto, contaExistente);

        Conta contaAtualizada = contaRepository.save(contaExistente);

        return contaMapper.toResponseDto(contaAtualizada);
    }

    @Transactional
    public ContaResponseDto ativarConta(Long id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada com Id: " + id));

        if (conta.getContaStatus().equals(ContaStatus.ATIVA)) {
            throw new RuntimeException("A conta já está ativa");
        }

        conta.setContaStatus(ContaStatus.ATIVA);
        Conta contaAtivada = contaRepository.save(conta);

        return contaMapper.toResponseDto(contaAtivada);
    }

    @Transactional
    public ContaResponseDto inativarConta(Long id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada com Id: " + id));

        if (conta.getContaStatus().equals(ContaStatus.INATIVA)) {
            throw new RuntimeException("A conta já está inativa");
        }

        conta.setContaStatus(ContaStatus.INATIVA);
        Conta contaInativada = contaRepository.save(conta);

        return contaMapper.toResponseDto(contaInativada);
    }

    @Transactional
    public ContaResponseDto encerrarConta(Long id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada com Id: " + id));

        if (conta.getContaStatus().equals(ContaStatus.ENCERRADA)) {
            throw new RuntimeException("A conta já está encerrada");
        }

        conta.setContaStatus(ContaStatus.ENCERRADA);
        Conta contaEncerrada = contaRepository.save(conta);

        return contaMapper.toResponseDto(contaEncerrada);

    }
}

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaService {

    private static final Logger log = LoggerFactory.getLogger(ContaService.class);

    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;
    private final ContaMapper contaMapper;

    public ContaService(ContaRepository contaRepository, ClienteRepository clienteRepository, ContaMapper contaMapper) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
        this.contaMapper = contaMapper;
        log.info("ContaService inicializado");
    }

    @Transactional
    public ContaResponseDto criarConta(ContaCreateDto contaCreateDto) {
        log.debug("Tentando criar conta com Dto: {}", contaCreateDto);
        Cliente cliente = clienteRepository.findById(contaCreateDto.getClienteId())
                .orElseThrow(() -> {
                    log.warn("Cliente não encontrado com Id: {}", contaCreateDto.getClienteId());
                    return new RuntimeException("Cliente não encontrado com Id: " + contaCreateDto.getClienteId());
                });

        Conta conta = contaMapper.toEntity(contaCreateDto);
        conta.setCliente(cliente);
        conta.setContaStatus(ContaStatus.ATIVA);

        Conta contaSalva = contaRepository.save(conta);

        log.info("Conta criada com sucesso. Id: {}", contaSalva.getId());

        return contaMapper.toResponseDto(contaSalva);
    }

    @Transactional(readOnly = true)
    public ContaResponseDto buscarContaPorId(Long id) {
        log.debug("Tentando buscar conta pelo Id: {}", id);

        return contaRepository.findById(id)
                .map(conta -> {
                    log.info("Conta encontrada. Id: {}", conta.getId());
                    return contaMapper.toResponseDto(conta);
                })
                .orElseThrow(() -> {
                    log.warn("Conta não encontrada com Id: {}", id);
                    return new RuntimeException("Conta não encontrada com Id: " + id);
                });
    }

    @Transactional(readOnly = true)
    public List<ContaResponseDto> listarTodasContas() {
        log.debug("Tentando listar todas as contas");

        List<ContaResponseDto> contas = contaRepository.findAll().stream()
                .map(contaMapper::toResponseDto)
                .collect(Collectors.toList());

        log.info("Listagem de {} contas concluída com sucesso", contas.size());

        return contas;
    }

    @Transactional(readOnly = true)
    public List<ContaResponseDto> buscarContasPorCliente(Long clienteId) {
        log.debug("Tentando buscar contas para o cliente com Id: {}", clienteId);

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> {
                    log.warn("Cliente não encontrado com Id: {}", clienteId);
                    return new RuntimeException("Cliente não encontrado com Id: " + clienteId);
                });

        List<ContaResponseDto> contas = contaRepository.findByCliente(cliente).stream()
                .map(contaMapper::toResponseDto)
                .collect(Collectors.toList());

        log.info("Encontradas {} contas para o cliente com Id: {}", contas.size() ,clienteId);

        return contas;
    }

    @Transactional
    public ContaResponseDto atualizarConta(Long id, ContaUpdateDto contaUpdateDto) {
        log.debug("Tentando atualizar conta com Id: {}", id);

        Conta contaExistente = contaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Conta não encontrada com Id: {}", id);
                    return new RuntimeException("Conta não encontrada para atualização com Id: " + id);
                });

        contaMapper.updateContaFromDto(contaUpdateDto, contaExistente);

        Conta contaAtualizada = contaRepository.save(contaExistente);

        log.info("Conta com Id: {}, atualizada com sucesso", id);

        return contaMapper.toResponseDto(contaAtualizada);
    }

    @Transactional
    public ContaResponseDto ativarConta(Long id) {
        log.debug("Tentando ativar conta com Id: {}", id);

        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Conta não encontrada com Id: {}", id);
                    return new RuntimeException("Conta não encontrada com Id: " + id);
                });

        if (conta.getContaStatus().equals(ContaStatus.ATIVA)) {
            log.warn("Tentativa de ativar conta já ativa. Id: {}", id);
            throw new RuntimeException("A conta já está ativa");
        }

        conta.setContaStatus(ContaStatus.ATIVA);
        Conta contaAtivada = contaRepository.save(conta);

        log.info("Conta com Id: {}, ativada com sucesso", id);

        return contaMapper.toResponseDto(contaAtivada);
    }

    @Transactional
    public ContaResponseDto inativarConta(Long id) {
        log.debug("Tentando inativar conta com Id: {}", id);

        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Conta não encontrada com Id: {}", id);
                    return new RuntimeException("Conta não encontrada com Id: " + id);
                });

        if (conta.getContaStatus().equals(ContaStatus.INATIVA)) {
            log.warn("Tentativa de inativar conta já inativa. Id: {}", id);
            throw new RuntimeException("A conta já está inativa");
        }

        conta.setContaStatus(ContaStatus.INATIVA);
        Conta contaInativada = contaRepository.save(conta);

        log.info("Conta com Id: {}, inativada com sucesso", id);

        return contaMapper.toResponseDto(contaInativada);
    }

    @Transactional
    public ContaResponseDto encerrarConta(Long id) {
        log.debug("Tentando encerrar conta com Id: {}", id);

        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Conta não encontrada com Id: {}", id);
                    return new RuntimeException("Conta não encontrada com Id: " + id);
                });

        if (conta.getContaStatus().equals(ContaStatus.ENCERRADA)) {
            log.warn("Tentativa de encerrar conta já encerrada. Id: {}", id);
            throw new RuntimeException("A conta já está encerrada");
        }

        conta.setContaStatus(ContaStatus.ENCERRADA);
        Conta contaEncerrada = contaRepository.save(conta);

        log.info("Conta com Id: {}, encerrada com sucesso", id);

        return contaMapper.toResponseDto(contaEncerrada);
    }
}
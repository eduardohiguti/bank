package com.higuti.bank.service;

import com.higuti.bank.model.Cliente;
import com.higuti.bank.mapper.ClienteMapper;
import com.higuti.bank.repository.ClienteRepository;
import com.higuti.bank.dto.*;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        log.info("ClienteService inicializado");
    }

    @Transactional
    public ClienteResponseDto criarCliente(ClienteCreateDto clienteCreateDto) {
        log.debug("Tentando criar cliente com Dto: {}", clienteCreateDto);
        Cliente cliente = clienteMapper.toEntity(clienteCreateDto);

        Cliente clienteSalvo = clienteRepository.save(cliente);
        log.info("Cliente criado com sucesso. Id: {}", clienteSalvo.getId());

        return clienteMapper.toResponseDto(clienteSalvo);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDto buscarClientePorId(Long id) {
        log.debug("Buscando cliente pelo Id: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cliente não encontrado com Id: {}", id);
                    return new RuntimeException("Cliente não encontrado com Id: " + id);
                });

        Hibernate.initialize(cliente.getContas());

        log.info("Cliente encontrado e contas inicializadas. Id: {}", cliente.getId());
        return clienteMapper.toResponseDto(cliente);
    }

    @Transactional
    public ClienteResponseDto atualizarCliente(Long id, ClienteUpdateDto clienteUpdateDto) {
        log.debug("Tentando atualizar cliente Id: {} com Dto: {}", id, clienteUpdateDto);
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cliente não encontrado para atualização com Id: {}", id);
                    return new RuntimeException("Cliente não encontrado para atualização com Id: " + id);
                });

        clienteMapper.updateClienteFromDto(clienteUpdateDto, clienteExistente);

        Cliente clienteAtualizado = clienteRepository.save(clienteExistente);

        Hibernate.initialize(clienteAtualizado.getContas());

        log.info("Cliente atualizado com sucesso. Id: {}", clienteAtualizado.getId());

        return clienteMapper.toResponseDto(clienteAtualizado);
    }
}
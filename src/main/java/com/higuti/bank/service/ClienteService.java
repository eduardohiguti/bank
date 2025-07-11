package com.higuti.bank.service;

import com.higuti.bank.model.Cliente;
import com.higuti.bank.mapper.ClienteMapper;
import com.higuti.bank.repository.ClienteRepository;
import com.higuti.bank.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Transactional
    public ClienteResponseDto criarCliente(ClienteCreateDto clienteCreateDto) {
        Cliente cliente = clienteMapper.toEntity(clienteCreateDto);

        Cliente clienteSalvo = clienteRepository.save(cliente);

        return clienteMapper.toResponseDto(clienteSalvo);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDto buscarClientePorId(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        return clienteOptional
                .map(clienteMapper::toResponseDto)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com Id: " + id));
    }

    @Transactional
    public ClienteResponseDto atualizarCliente(Long id, ClienteUpdateDto clienteUpdateDto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado para atualização com Id: " + id));

        clienteMapper.updateClienteFromDto(clienteUpdateDto, clienteExistente);

        Cliente clienteAtualizado = clienteRepository.save(clienteExistente);

        return clienteMapper.toResponseDto(clienteAtualizado);
    }
}
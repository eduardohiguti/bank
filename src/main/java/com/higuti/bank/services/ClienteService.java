package com.higuti.bank.services;

import com.higuti.bank.Cliente;
import com.higuti.bank.ClienteMapper;
import com.higuti.bank.ClienteRepository;
import com.higuti.bank.dtos.*;
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

    @Transactional
    public void deletarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado para deleção com Id: " + id);
        }

        clienteRepository.deleteById(id);
    }
}
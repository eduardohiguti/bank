package com.higuti.bank.controller;

import com.higuti.bank.dto.ClienteCreateDto;
import com.higuti.bank.dto.ClienteResponseDto;
import com.higuti.bank.dto.ClienteUpdateDto;
import com.higuti.bank.dto.ContaResponseDto;
import com.higuti.bank.service.ClienteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final static Logger log = LoggerFactory.getLogger(ClienteController.class);

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
        log.info("ClienteController inicializado");
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> createCliente(@RequestBody @Valid ClienteCreateDto clienteCreateDto) {
        log.debug("Requisição POST para criar cliente recebida. Dto: {}", clienteCreateDto);

        ClienteResponseDto newCliente = clienteService.criarCliente(clienteCreateDto);

        log.info("Cliente criado com sucesso. Id: {}. Retornando status 201 Created", newCliente.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(newCliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> getClienteById(@PathVariable Long id) {
        log.debug("Requisição GET para buscar cliente por Id: {} recebida", id);

        ClienteResponseDto cliente = clienteService.buscarClientePorId(id);

        log.info("Cliente com Id: {} encontrado. Retornando status 200 OK", cliente.getId());

        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> updateCliente(@PathVariable Long id, @RequestBody @Valid ClienteUpdateDto clienteUpdateDto) {
        log.debug("Requisição PUT para atualizar cliente Id: {} recebida. Dto: {}", id, clienteUpdateDto);

        ClienteResponseDto updatedCliente = clienteService.atualizarCliente(id, clienteUpdateDto);

        log.info("Cliente com Id: {} atualizado com sucesso. Retornando status 200 OK", updatedCliente.getId());

        return ResponseEntity.ok(updatedCliente);
    }
}

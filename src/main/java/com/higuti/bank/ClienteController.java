package com.higuti.bank;

import com.higuti.bank.dtos.ClienteCreateDto;
import com.higuti.bank.dtos.ClienteResponseDto;
import com.higuti.bank.dtos.ClienteUpdateDto;
import com.higuti.bank.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> createCliente(@RequestBody @Valid ClienteCreateDto clienteCreateDto) {
        ClienteResponseDto newCliente = clienteService.criarCliente(clienteCreateDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(newCliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> getClienteById(@PathVariable Long id) {
        ClienteResponseDto cliente = clienteService.buscarClientePorId(id);

        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> updateCliente(@PathVariable Long id, @RequestBody @Valid ClienteUpdateDto clienteUpdateDto) {
        ClienteResponseDto updatedCliente = clienteService.atualizarCliente(id, clienteUpdateDto);

        return ResponseEntity.ok(updatedCliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

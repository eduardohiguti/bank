package com.higuti.bank.controller;

import com.higuti.bank.dto.ContaCreateDto;
import com.higuti.bank.dto.ContaResponseDto;
import com.higuti.bank.dto.ContaUpdateDto;
import com.higuti.bank.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    public ResponseEntity<ContaResponseDto> createConta(@RequestBody @Valid ContaCreateDto contaCreateDto) {
        ContaResponseDto novaConta = contaService.criarConta(contaCreateDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaResponseDto> getContaById(@PathVariable Long id) {
        ContaResponseDto conta = contaService.buscarContaPorId(id);

        return ResponseEntity.ok(conta);
    }

    @GetMapping
    public ResponseEntity<List<ContaResponseDto>> getAllContas() {
        List<ContaResponseDto> contas = contaService.listarTodasContas();

        return ResponseEntity.ok(contas);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ContaResponseDto>> getContasByClienteId(@PathVariable Long clienteId) {
        List<ContaResponseDto> contas = contaService.buscarContasPorCliente(clienteId);

        return ResponseEntity.ok(contas);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ContaResponseDto> updateConta(@PathVariable Long id, @RequestBody @Valid ContaUpdateDto contaUpdateDto) {
        ContaResponseDto contaAtualizada = contaService.atualizarConta(id, contaUpdateDto);

        return ResponseEntity.ok(contaAtualizada);
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<ContaResponseDto> ativarConta(@PathVariable Long id) {
        ContaResponseDto contaAtivada = contaService.ativarConta(id);

        return ResponseEntity.ok(contaAtivada);
    }

    @PutMapping("/{id}/inativar")
    public ResponseEntity<ContaResponseDto> inativarConta(@PathVariable Long id) {
        ContaResponseDto contaInativada = contaService.inativarConta(id);

        return ResponseEntity.ok(contaInativada);
    }

    @PutMapping("/{id}/encerrar")
    public ResponseEntity<ContaResponseDto> encerrarConta(@PathVariable Long id) {
        ContaResponseDto contaEncerrada = contaService.encerrarConta(id);

        return ResponseEntity.ok(contaEncerrada);
    }
}

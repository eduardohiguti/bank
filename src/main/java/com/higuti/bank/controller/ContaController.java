package com.higuti.bank.controller;

import com.higuti.bank.dto.ContaCreateDto;
import com.higuti.bank.dto.ContaResponseDto;
import com.higuti.bank.dto.ContaUpdateDto;
import com.higuti.bank.service.ContaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private static final Logger log = LoggerFactory.getLogger(ContaController.class);

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
        log.info("ContaController inicializado");
    }

    @PostMapping
    public ResponseEntity<ContaResponseDto> createConta(@RequestBody @Valid ContaCreateDto contaCreateDto) {
        log.debug("Requisição POST para criar conta recebida. Dto: {}", contaCreateDto);

        ContaResponseDto novaConta = contaService.criarConta(contaCreateDto);

        log.info("Conta criada com sucesso: Id: {}. Retornando status 201 Created", novaConta.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaResponseDto> getContaById(@PathVariable Long id) {
        log.debug("Requisição GET para buscar conta por Id: {} recebida", id);

        ContaResponseDto conta = contaService.buscarContaPorId(id);

        log.info("Conta com Id: {} encontrada. Retornando status 200 OK", conta.getId());

        return ResponseEntity.ok(conta);
    }

    @GetMapping
    public ResponseEntity<List<ContaResponseDto>> getAllContas() {
        log.debug("Requisição GET para listar todas as contas recebida");

        List<ContaResponseDto> contas = contaService.listarTodasContas();

        log.info("Listagem de {} contas concluída. Retornando status 200 OK", contas.size());

        return ResponseEntity.ok(contas);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ContaResponseDto>> getContasByClienteId(@PathVariable Long clienteId) {
        log.debug("Requisição GET para buscar contas por Cliente Id: {} recebida", clienteId);

        List<ContaResponseDto> contas = contaService.buscarContasPorCliente(clienteId);

        log.info("Encontradas {} contas para o Cliente Id: {}. Retornando status 200 OK", contas.size(), clienteId);

        return ResponseEntity.ok(contas);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ContaResponseDto> updateConta(@PathVariable Long id, @RequestBody @Valid ContaUpdateDto contaUpdateDto) {
        log.debug("Requisição PUT para atualizar conta Id: {} recebida. Dto: {}", id, contaUpdateDto);

        ContaResponseDto contaAtualizada = contaService.atualizarConta(id, contaUpdateDto);

        log.info("Conta com Id: {} atualizada com sucesso. Retornando status 200 OK", contaAtualizada.getId());

        return ResponseEntity.ok(contaAtualizada);
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<ContaResponseDto> ativarConta(@PathVariable Long id) {
        log.debug("Requisição PUT para ativar conta Id: {} recebida", id);

        ContaResponseDto contaAtivada = contaService.ativarConta(id);

        log.info("Conta com Id: {} ativada com sucesso. Retornando status 200 OK", contaAtivada.getId());

        return ResponseEntity.ok(contaAtivada);
    }

    @PutMapping("/{id}/inativar")
    public ResponseEntity<ContaResponseDto> inativarConta(@PathVariable Long id) {
        log.debug("Requisição PUT para inativar conta Id: {} recebida", id);

        ContaResponseDto contaInativada = contaService.inativarConta(id);

        log.info("Conta com Id: {} inativada com sucesso. Retornando status 200 OK", contaInativada.getId());


        return ResponseEntity.ok(contaInativada);
    }

    @PutMapping("/{id}/encerrar")
    public ResponseEntity<ContaResponseDto> encerrarConta(@PathVariable Long id) {
        log.debug("Requisição PUT para encerrar conta Id: {} recebida", id);

        ContaResponseDto contaEncerrada = contaService.encerrarConta(id);

        log.info("Conta com Id: {} encerrada com sucesso. Retornando status 200 OK", contaEncerrada.getId());

        return ResponseEntity.ok(contaEncerrada);
    }
}

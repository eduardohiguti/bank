package com.higuti.bank.controller;

import com.higuti.bank.dto.TransacaoRequestDto;
import com.higuti.bank.dto.TransacaoResponseDto;
import com.higuti.bank.service.TransacaoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private static final Logger log = LoggerFactory.getLogger(TransacaoController.class);

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
        log.info("TransacaoController inicializado");
    }

    @PostMapping("/transferencia")
    public ResponseEntity<TransacaoResponseDto> realizarTransferencia(@RequestBody @Valid TransacaoRequestDto request) {
        log.debug("Requisição POST para transacoes/transferencia recebida. Request Dto: {}", request);

        TransacaoResponseDto response = transacaoService.realizarTransferencia(request);


        log.info("Transferência concluída com sucesso. Transação Id: {}. Retornando status 201 Created", response.getIdTransacao());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

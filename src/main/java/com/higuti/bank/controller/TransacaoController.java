package com.higuti.bank.controller;

import com.higuti.bank.dto.TransacaoRequestDto;
import com.higuti.bank.dto.TransacaoResponseDto;
import com.higuti.bank.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping("/transferencia")
    public ResponseEntity<TransacaoResponseDto> realizarTransferencia(@RequestBody @Valid TransacaoRequestDto request) {
        TransacaoResponseDto response = transacaoService.realizarTransferencia(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

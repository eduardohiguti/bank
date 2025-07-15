package com.higuti.bank.service;

import com.higuti.bank.dto.TransacaoRequestDto;
import com.higuti.bank.dto.TransacaoResponseDto;
import com.higuti.bank.mapper.TransacaoMapper;
import com.higuti.bank.model.Conta;
import com.higuti.bank.model.ContaStatus;
import com.higuti.bank.model.Transacao;
import com.higuti.bank.model.TransacaoStatus;
import com.higuti.bank.repository.ContaRepository;
import com.higuti.bank.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransacaoService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;
    private final TransacaoMapper transacaoMapper;

    public TransacaoService(ContaRepository contaRepository, TransacaoRepository transacaoRepository, TransacaoMapper transacaoMapper) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
        this.transacaoMapper = transacaoMapper;
    }

    @Transactional
    public TransacaoResponseDto realizarTransferencia(TransacaoRequestDto request) {
        if (request.getNumeroContaOrigem().equals(request.getNumeroContaDestino())
            && request.getAgenciaOrigem().equals(request.getAgenciaDestino())) {
            throw new IllegalArgumentException("Não é permitido transferir para a mesma conta");
        }

        Conta contaOrigem = contaRepository.findByNumeroContaAndAgenciaAndContaStatus(
                request.getNumeroContaOrigem(), request.getAgenciaOrigem(), ContaStatus.ATIVA)
                .orElseThrow(() -> new RuntimeException("Conta de origem não encontrada ou inativa"));

        Conta contaDestino = contaRepository.findByNumeroContaAndAgenciaAndContaStatus(
                request.getNumeroContaDestino(), request.getAgenciaDestino(), ContaStatus.ATIVA)
                .orElseThrow(() -> new RuntimeException("Conta de destino não encontrada ou inativa"));

        if (contaOrigem.getSaldo().compareTo(request.getValor()) < 0) {
            throw new RuntimeException("Saldo insuficiente na conta de origem");
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(request.getValor()));
        contaDestino.setSaldo(contaDestino.getSaldo().add(request.getValor()));

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        Transacao transacao = Transacao.builder()
                .contaOrigem(contaOrigem)
                .contaDestino(contaDestino)
                .valor(request.getValor())
                .dataHora(LocalDateTime.now())
                .status(TransacaoStatus.SUCESSO)
                .build();
        Transacao transacaoSalva = transacaoRepository.save(transacao);

        return transacaoMapper.toResponseDto(transacaoSalva);
    }
}

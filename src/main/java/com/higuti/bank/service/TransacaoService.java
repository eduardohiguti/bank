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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransacaoService {

    private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;
    private final TransacaoMapper transacaoMapper;

    public TransacaoService(ContaRepository contaRepository, TransacaoRepository transacaoRepository, TransacaoMapper transacaoMapper) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
        this.transacaoMapper = transacaoMapper;
        log.info("TransacaoService inicializado");
    }

    @Transactional
    public TransacaoResponseDto realizarTransferencia(TransacaoRequestDto request) {
        log.debug("Iniciando transferência. Request: {}", request);

        if (request.getNumeroContaOrigem().equals(request.getNumeroContaDestino())
            && request.getAgenciaOrigem().equals(request.getAgenciaDestino())) {

            log.warn("Tentativa de transferência para a mesma conta. Origem: {}{}, Destino: {}{}",
                    request.getNumeroContaOrigem(), request.getAgenciaOrigem(),
                    request.getNumeroContaDestino(), request.getAgenciaDestino());

            throw new IllegalArgumentException("Não é permitido transferir para a mesma conta");
        }

        Conta contaOrigem = contaRepository.findByNumeroContaAndAgenciaAndContaStatus(
                request.getNumeroContaOrigem(), request.getAgenciaOrigem(), ContaStatus.ATIVA)
                .orElseThrow(() -> {
                    log.warn("Conta de origem não encontrada ou inativa. Conta: {}{}",
                            request.getNumeroContaOrigem(), request.getAgenciaOrigem());
                    return new RuntimeException("Conta de origem não encontrada ou inativa");
                });

        Conta contaDestino = contaRepository.findByNumeroContaAndAgenciaAndContaStatus(
                request.getNumeroContaDestino(), request.getAgenciaDestino(), ContaStatus.ATIVA)
                .orElseThrow(() -> {
                    log.warn("Conta de destino não encontrada ou inativa. Conta: {}{}",
                            request.getNumeroContaDestino(), request.getAgenciaDestino());
                    return new RuntimeException("Conta de destino não encontrada ou inativa");
                });

        if (contaOrigem.getSaldo().compareTo(request.getValor()) < 0) {
            log.warn("Saldo insuficiente para transferência. Conta Origem Id: {}, Saldo Atual: {}, Valor da Transação: {}",
                    contaOrigem.getId(), contaOrigem.getSaldo(), request.getValor());
            throw new RuntimeException("Saldo insuficiente na conta de origem");
        }

        BigDecimal saldoAnteriorOrigem = contaOrigem.getSaldo();
        BigDecimal saldoAnteriorDestino = contaDestino.getSaldo();

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(request.getValor()));
        contaDestino.setSaldo(contaDestino.getSaldo().add(request.getValor()));

        log.debug("Saldos pré-atualização: Origem Id {} ({} -> {}), Destino Id {} ({} -> {})",
                contaOrigem.getId(), saldoAnteriorOrigem, contaOrigem.getSaldo(),
                contaDestino.getId(), saldoAnteriorDestino, contaDestino.getSaldo());

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        log.info("Saldos das contas atualizados no bando de dados. Origem Id: {}, Destino Id: {}",
                contaOrigem.getId(), contaDestino.getId());

        Transacao transacao = Transacao.builder()
                .contaOrigem(contaOrigem)
                .contaDestino(contaDestino)
                .valor(request.getValor())
                .dataHora(LocalDateTime.now())
                .status(TransacaoStatus.SUCESSO)
                .build();
        Transacao transacaoSalva = transacaoRepository.save(transacao);

        log.info("Transação registrada com sucesso. Id da Transação: {}", transacaoSalva.getId());

        log.info("Transferência concluída com sucesso. Transação Id: {}, Valor: {}",
                transacaoSalva.getId(), transacaoSalva.getValor());

        return transacaoMapper.toResponseDto(transacaoSalva);
    }
}
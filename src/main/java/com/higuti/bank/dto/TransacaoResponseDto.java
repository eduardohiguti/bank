package com.higuti.bank.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TransacaoResponseDto {

    private Long idTransacao;
    private String numeroContaOrigem;
    private String agenciaOrigem;
    private String numeroContaDestino;
    private String agenciaDestino;
    private BigDecimal valor;
    private LocalDateTime dataHora;
    private String status;
    private String mensagem;
}

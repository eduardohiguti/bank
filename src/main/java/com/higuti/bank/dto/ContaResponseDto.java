package com.higuti.bank.dto;

import com.higuti.bank.model.Cliente;
import com.higuti.bank.model.ContaStatus;
import com.higuti.bank.model.ContaTipo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ContaResponseDto {

    private Long id;

    private String numeroConta;

    private String agencia;

    private BigDecimal saldo;

    private ContaTipo contaTipo;

    private ContaStatus contaStatus;

    private Long clienteId;
}

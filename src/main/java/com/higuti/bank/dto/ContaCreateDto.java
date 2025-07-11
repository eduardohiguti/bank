package com.higuti.bank.dto;

import com.higuti.bank.model.Cliente;
import com.higuti.bank.model.ContaTipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ContaCreateDto {

    @NotBlank(message = "O número da conta é obrigatório")
    private String numeroConta;

    @NotBlank(message = "A agência é obrigatória")
    private String agencia;

    @NotNull(message = "O saldo inicial é obrigatório")
    @PositiveOrZero(message = "O saldo inicial deve ser zero ou positivo")
    private BigDecimal saldoInicial;

    @NotNull(message = "O tipo de conta é obrigatório")
    private ContaTipo contaTipo;

    @NotNull(message = "O Id do cliente é obrigatório")
    private Long clienteId;
}

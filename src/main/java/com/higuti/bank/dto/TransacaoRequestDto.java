package com.higuti.bank.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class TransacaoRequestDto {

    @NotBlank(message = "O número da conta de origem é obrigatório")
    private String numeroContaOrigem;

    @NotBlank(message = "O número de agência de origem é obrigatória")
    private String agenciaOrigem;

    @NotBlank(message = "O número da conta de destino é obrigatório")
    private String numeroContaDestino;

    @NotBlank(message = "O número de agência de destino é obrigatória")
    private String agenciaDestino;

    @NotNull(message = "O valor da transação é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor da transação deve ser positivo")
    private BigDecimal valor;
}

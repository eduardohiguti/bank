package com.higuti.bank.dto;

import com.higuti.bank.model.ContaStatus;
import com.higuti.bank.model.ContaTipo;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ContaUpdateDto {

    @PositiveOrZero(message = "O saldo deve ser zero ou positivo")
    private BigDecimal saldo;

    private ContaStatus contaStatus;

    private ContaTipo contaTipo;
}

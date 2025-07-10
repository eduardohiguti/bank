package com.higuti.bank.dto;

import com.higuti.bank.model.Categoria;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClienteUpdateDto {

    @Email(message = "Formato de e-mail inválido")
    private String email;

    private Categoria categoria;
}

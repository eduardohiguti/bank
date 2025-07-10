package com.higuti.bank.dtos;

import com.higuti.bank.Categoria;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClienteCreateDto {

    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @NotBlank(message = "O documento é obrigatório")
    private String documento;

    @Email(message = "Formato de e-mail inválido")
    @NotBlank(message = "O e-mail é obrigatório")
    private String email;

    @NotNull(message = "A categoria é obrigatória")
    private Categoria categoria;
}

package com.higuti.bank.dto;

import com.higuti.bank.model.Categoria;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClienteResponseDto {

    private Long id;

    private String name;

    private String documento;

    private String email;

    private Categoria categoria;
}

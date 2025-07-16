package com.higuti.bank.dto;

import com.higuti.bank.model.ClienteCategoria;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ClienteResponseDto {

    private Long id;

    private String name;

    private String documento;

    private String email;

    private ClienteCategoria categoria;

    private List<ContaResponseDto> contas;
}

package com.higuti.bank.dto;

import com.higuti.bank.model.ClienteCategoria;
import com.higuti.bank.model.Conta;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class ClienteResponseDto {

    private Long id;

    private String name;

    private String documento;

    private String email;

    private ClienteCategoria categoria;

    private Set<Conta> contas;
}

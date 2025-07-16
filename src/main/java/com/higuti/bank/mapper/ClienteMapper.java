package com.higuti.bank.mapper;

import com.higuti.bank.dto.ContaResponseDto;
import com.higuti.bank.model.Cliente;
import com.higuti.bank.dto.ClienteCreateDto;
import com.higuti.bank.dto.ClienteResponseDto;
import com.higuti.bank.dto.ClienteUpdateDto;
import com.higuti.bank.model.Conta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

@Mapper(componentModel = "spring", uses = {ContaMapper.class}, imports = {Collectors.class, Collections.class})
public interface ClienteMapper {

    Cliente toEntity(ClienteCreateDto dto);

    @Mapping(target = "contas", expression = "java(cliente.getContas() != null ? " +
            "cliente.getContas().stream().map(conta -> this.toContaResponseDto(conta)).collect(Collectors.toList()) : " + // Note 'this.toContaResponseDto(conta)'
            "Collections.emptyList())")
    ClienteResponseDto toResponseDto(Cliente cliente);

    ContaResponseDto toContaResponseDto(Conta conta);

    void updateClienteFromDto(ClienteUpdateDto dto, @MappingTarget Cliente cliente);
}
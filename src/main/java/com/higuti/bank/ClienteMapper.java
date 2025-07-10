package com.higuti.bank;

import com.higuti.bank.dtos.ClienteCreateDto;
import com.higuti.bank.dtos.ClienteResponseDto;
import com.higuti.bank.dtos.ClienteUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper (componentModel = "spring")
public interface ClienteMapper {

    //Criar um novo cliente
    Cliente toEntity(ClienteCreateDto dto);

    //Retornar os dados de um cliente
    ClienteResponseDto toResponseDto(Cliente cliente);

    //Atualizar um cliente já existente
    void updateClienteFromDto(ClienteUpdateDto dto, @MappingTarget Cliente cliente);
}

package com.higuti.bank.mapper;

import com.higuti.bank.dto.ClienteCreateDto;
import com.higuti.bank.dto.ClienteResponseDto;
import com.higuti.bank.dto.ClienteUpdateDto;
import com.higuti.bank.model.Cliente;
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

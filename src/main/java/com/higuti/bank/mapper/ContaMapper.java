package com.higuti.bank.mapper;

import com.higuti.bank.dto.ContaCreateDto;
import com.higuti.bank.dto.ContaResponseDto;
import com.higuti.bank.dto.ContaUpdateDto;
import com.higuti.bank.model.Conta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContaMapper {

    @Mapping(source = "saldoInicial", target = "saldo")
    Conta toEntity(ContaCreateDto dto);

    ContaResponseDto toResponseDto(Conta conta);

    void updateContaFromDto(ContaUpdateDto dto, @MappingTarget Conta conta);
}

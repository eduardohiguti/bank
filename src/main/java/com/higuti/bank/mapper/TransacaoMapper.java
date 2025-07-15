package com.higuti.bank.mapper;

import com.higuti.bank.dto.TransacaoResponseDto;
import com.higuti.bank.model.Transacao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransacaoMapper {
    TransacaoResponseDto toResponseDto(Transacao transacaoSalva);
}

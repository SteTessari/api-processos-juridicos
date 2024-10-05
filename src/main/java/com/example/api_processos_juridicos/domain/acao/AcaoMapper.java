package com.example.api_processos_juridicos.domain.acao;

import com.example.api_processos_juridicos.dto.acao.AcaoDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AcaoMapper {

    AcaoMapper INSTANCE = Mappers.getMapper(AcaoMapper.class);

    Acao toObject(AcaoDTO acaoDTO);

    AcaoDTO toDTO(Acao acao);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Acao updateFromDTO(AcaoDTO acaoDTO, @MappingTarget Acao acao);
}

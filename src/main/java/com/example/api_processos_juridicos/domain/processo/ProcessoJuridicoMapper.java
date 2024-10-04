package com.example.api_processos_juridicos.domain.processo;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProcessoJuridicoMapper {
    ProcessoJuridicoMapper INSTANCE = Mappers.getMapper(ProcessoJuridicoMapper.class);

    ProcessoJuridico toObject(ProcessoJuridicoDTO processoJuridicoDTO);

    ProcessoJuridicoDTO toDTO(ProcessoJuridico processoJuridico);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProcessoJuridico updateFromDTO(ProcessoJuridicoDTO processoJuridicoDTO, @MappingTarget ProcessoJuridico processoJuridico);
}

package com.example.api_processos_juridicos.domain.processo;

import com.example.api_processos_juridicos.dto.processo.ProcessoFiltroDTO;
import com.example.api_processos_juridicos.dto.processo.ProcessoJuridicoDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProcessoJuridicoMapper {
    ProcessoJuridicoMapper INSTANCE = Mappers.getMapper(ProcessoJuridicoMapper.class);

    ProcessoJuridico toObject(ProcessoJuridicoDTO processoJuridicoDTO);
    ProcessoJuridico toObject(ProcessoFiltroDTO filtroDTO);

    ProcessoJuridicoDTO toDTO(ProcessoJuridico processoJuridico);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProcessoJuridico updateFromDTO(ProcessoJuridicoDTO processoJuridicoDTO, @MappingTarget ProcessoJuridico processoJuridico);
}

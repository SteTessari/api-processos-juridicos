package com.example.api_processos_juridicos.domain.pessoa;

import com.example.api_processos_juridicos.dto.pessoa.PessoaDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PessoaMapper {

    PessoaMapper INSTANCE = Mappers.getMapper(PessoaMapper.class);

    Pessoa toObject(PessoaDTO pessoaDTO);

    PessoaDTO toDTO(Pessoa pessoa);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Pessoa updateFromDTO(PessoaDTO pessoaDTO, @MappingTarget Pessoa pessoa);
}

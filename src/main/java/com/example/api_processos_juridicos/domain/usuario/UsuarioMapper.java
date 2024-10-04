package com.example.api_processos_juridicos.domain.usuario;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    Usuario toObject(UsuarioDTO usuarioDTO);

    UsuarioDTO toDTO(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Usuario updateFromDTO(UsuarioDTO usuarioDTO, @MappingTarget Usuario usuario);
}

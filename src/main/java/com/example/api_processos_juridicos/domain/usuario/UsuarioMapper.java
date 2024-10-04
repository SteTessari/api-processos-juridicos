package com.example.api_processos_juridicos.domain.usuario;

import com.example.api_processos_juridicos.dto.usuario.UsuarioDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    Usuario toObject(UsuarioDTO usuarioDTO);

    UsuarioDTO toDTO(Usuario usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Usuario updateFromDTO(UsuarioDTO usuarioDTO, @MappingTarget Usuario usuario);
}

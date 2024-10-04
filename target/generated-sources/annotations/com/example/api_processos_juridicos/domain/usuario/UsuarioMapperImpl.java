package com.example.api_processos_juridicos.domain.usuario;

import com.example.api_processos_juridicos.dto.usuario.UsuarioDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-04T17:25:55-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toObject(UsuarioDTO usuarioDTO) {
        if ( usuarioDTO == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setEmail( usuarioDTO.getEmail() );
        usuario.setSenha( usuarioDTO.getSenha() );

        return usuario;
    }

    @Override
    public UsuarioDTO toDTO(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setEmail( usuario.getEmail() );
        usuarioDTO.setSenha( usuario.getSenha() );

        return usuarioDTO;
    }

    @Override
    public Usuario updateFromDTO(UsuarioDTO usuarioDTO, Usuario usuario) {
        if ( usuarioDTO == null ) {
            return usuario;
        }

        if ( usuarioDTO.getEmail() != null ) {
            usuario.setEmail( usuarioDTO.getEmail() );
        }
        if ( usuarioDTO.getSenha() != null ) {
            usuario.setSenha( usuarioDTO.getSenha() );
        }

        return usuario;
    }
}

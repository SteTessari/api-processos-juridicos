package com.example.api_processos_juridicos.domain.usuario;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-04T09:12:44-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toObject(UsuarioDTO usuarioDTO) {
        if ( usuarioDTO == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setNomeCompleto( usuarioDTO.getNomeCompleto() );
        usuario.setInscricaoFederal( usuarioDTO.getInscricaoFederal() );
        usuario.setTipoParte( usuarioDTO.getTipoParte() );
        usuario.setTelefone( usuarioDTO.getTelefone() );
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

        usuarioDTO.setNomeCompleto( usuario.getNomeCompleto() );
        usuarioDTO.setInscricaoFederal( usuario.getInscricaoFederal() );
        usuarioDTO.setTipoParte( usuario.getTipoParte() );
        usuarioDTO.setTelefone( usuario.getTelefone() );
        usuarioDTO.setEmail( usuario.getEmail() );
        usuarioDTO.setSenha( usuario.getSenha() );

        return usuarioDTO;
    }

    @Override
    public Usuario updateFromDTO(UsuarioDTO usuarioDTO, Usuario usuario) {
        if ( usuarioDTO == null ) {
            return usuario;
        }

        if ( usuarioDTO.getNomeCompleto() != null ) {
            usuario.setNomeCompleto( usuarioDTO.getNomeCompleto() );
        }
        if ( usuarioDTO.getInscricaoFederal() != null ) {
            usuario.setInscricaoFederal( usuarioDTO.getInscricaoFederal() );
        }
        if ( usuarioDTO.getTipoParte() != null ) {
            usuario.setTipoParte( usuarioDTO.getTipoParte() );
        }
        if ( usuarioDTO.getTelefone() != null ) {
            usuario.setTelefone( usuarioDTO.getTelefone() );
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

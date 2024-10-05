package com.example.api_processos_juridicos.domain.acao;

import com.example.api_processos_juridicos.dto.acao.AcaoDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-05T13:44:11-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
public class AcaoMapperImpl implements AcaoMapper {

    @Override
    public Acao toObject(AcaoDTO acaoDTO) {
        if ( acaoDTO == null ) {
            return null;
        }

        Acao acao = new Acao();

        acao.setTipoAcao( acaoDTO.getTipoAcao() );
        acao.setDataRegistro( acaoDTO.getDataRegistro() );
        acao.setDescricao( acaoDTO.getDescricao() );

        return acao;
    }

    @Override
    public AcaoDTO toDTO(Acao acao) {
        if ( acao == null ) {
            return null;
        }

        AcaoDTO acaoDTO = new AcaoDTO();

        acaoDTO.setTipoAcao( acao.getTipoAcao() );
        acaoDTO.setDataRegistro( acao.getDataRegistro() );
        acaoDTO.setDescricao( acao.getDescricao() );

        return acaoDTO;
    }

    @Override
    public Acao updateFromDTO(AcaoDTO acaoDTO, Acao acao) {
        if ( acaoDTO == null ) {
            return acao;
        }

        if ( acaoDTO.getTipoAcao() != null ) {
            acao.setTipoAcao( acaoDTO.getTipoAcao() );
        }
        if ( acaoDTO.getDataRegistro() != null ) {
            acao.setDataRegistro( acaoDTO.getDataRegistro() );
        }
        if ( acaoDTO.getDescricao() != null ) {
            acao.setDescricao( acaoDTO.getDescricao() );
        }

        return acao;
    }
}

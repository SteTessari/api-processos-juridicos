package com.example.api_processos_juridicos.domain.pessoa;

import com.example.api_processos_juridicos.dto.pessoa.PessoaDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-08T10:38:00-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
public class PessoaMapperImpl implements PessoaMapper {

    @Override
    public Pessoa toObject(PessoaDTO pessoaDTO) {
        if ( pessoaDTO == null ) {
            return null;
        }

        Pessoa pessoa = new Pessoa();

        pessoa.setNomeCompleto( pessoaDTO.getNomeCompleto() );
        pessoa.setInscricaoFederal( pessoaDTO.getInscricaoFederal() );
        pessoa.setTipoParte( pessoaDTO.getTipoParte() );
        pessoa.setTelefone( pessoaDTO.getTelefone() );
        pessoa.setEmail( pessoaDTO.getEmail() );

        return pessoa;
    }

    @Override
    public PessoaDTO toDTO(Pessoa pessoa) {
        if ( pessoa == null ) {
            return null;
        }

        PessoaDTO pessoaDTO = new PessoaDTO();

        pessoaDTO.setNomeCompleto( pessoa.getNomeCompleto() );
        pessoaDTO.setInscricaoFederal( pessoa.getInscricaoFederal() );
        pessoaDTO.setTipoParte( pessoa.getTipoParte() );
        pessoaDTO.setTelefone( pessoa.getTelefone() );
        pessoaDTO.setEmail( pessoa.getEmail() );

        return pessoaDTO;
    }

    @Override
    public Pessoa updateFromDTO(PessoaDTO pessoaDTO, Pessoa pessoa) {
        if ( pessoaDTO == null ) {
            return pessoa;
        }

        if ( pessoaDTO.getNomeCompleto() != null ) {
            pessoa.setNomeCompleto( pessoaDTO.getNomeCompleto() );
        }
        if ( pessoaDTO.getInscricaoFederal() != null ) {
            pessoa.setInscricaoFederal( pessoaDTO.getInscricaoFederal() );
        }
        if ( pessoaDTO.getTipoParte() != null ) {
            pessoa.setTipoParte( pessoaDTO.getTipoParte() );
        }
        if ( pessoaDTO.getTelefone() != null ) {
            pessoa.setTelefone( pessoaDTO.getTelefone() );
        }
        if ( pessoaDTO.getEmail() != null ) {
            pessoa.setEmail( pessoaDTO.getEmail() );
        }

        return pessoa;
    }
}

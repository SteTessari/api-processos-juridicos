package com.example.api_processos_juridicos.domain.processo;

import com.example.api_processos_juridicos.domain.pessoa.Pessoa;
import com.example.api_processos_juridicos.dto.pessoa.PessoaDTO;
import com.example.api_processos_juridicos.dto.processo.ProcessoFiltroDTO;
import com.example.api_processos_juridicos.dto.processo.ProcessoJuridicoDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-08T10:37:59-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
public class ProcessoJuridicoMapperImpl implements ProcessoJuridicoMapper {

    @Override
    public ProcessoJuridico toObject(ProcessoJuridicoDTO processoJuridicoDTO) {
        if ( processoJuridicoDTO == null ) {
            return null;
        }

        ProcessoJuridico processoJuridico = new ProcessoJuridico();

        processoJuridico.setNumeroProcesso( processoJuridicoDTO.getNumeroProcesso() );
        processoJuridico.setDataAbertura( processoJuridicoDTO.getDataAbertura() );
        processoJuridico.setDescricaoCaso( processoJuridicoDTO.getDescricaoCaso() );
        processoJuridico.setStatus( processoJuridicoDTO.getStatus() );
        processoJuridico.setPartesEnvolvidas( pessoaDTOListToPessoaList( processoJuridicoDTO.getPartesEnvolvidas() ) );

        return processoJuridico;
    }

    @Override
    public ProcessoJuridico toObject(ProcessoFiltroDTO filtroDTO) {
        if ( filtroDTO == null ) {
            return null;
        }

        ProcessoJuridico processoJuridico = new ProcessoJuridico();

        processoJuridico.setDataAbertura( filtroDTO.getDataAbertura() );

        return processoJuridico;
    }

    @Override
    public ProcessoJuridicoDTO toDTO(ProcessoJuridico processoJuridico) {
        if ( processoJuridico == null ) {
            return null;
        }

        ProcessoJuridicoDTO processoJuridicoDTO = new ProcessoJuridicoDTO();

        processoJuridicoDTO.setNumeroProcesso( processoJuridico.getNumeroProcesso() );
        processoJuridicoDTO.setDataAbertura( processoJuridico.getDataAbertura() );
        processoJuridicoDTO.setDescricaoCaso( processoJuridico.getDescricaoCaso() );
        processoJuridicoDTO.setStatus( processoJuridico.getStatus() );
        processoJuridicoDTO.setPartesEnvolvidas( pessoaListToPessoaDTOList( processoJuridico.getPartesEnvolvidas() ) );

        return processoJuridicoDTO;
    }

    @Override
    public ProcessoJuridico updateFromDTO(ProcessoJuridicoDTO processoJuridicoDTO, ProcessoJuridico processoJuridico) {
        if ( processoJuridicoDTO == null ) {
            return processoJuridico;
        }

        if ( processoJuridicoDTO.getNumeroProcesso() != null ) {
            processoJuridico.setNumeroProcesso( processoJuridicoDTO.getNumeroProcesso() );
        }
        if ( processoJuridicoDTO.getDataAbertura() != null ) {
            processoJuridico.setDataAbertura( processoJuridicoDTO.getDataAbertura() );
        }
        if ( processoJuridicoDTO.getDescricaoCaso() != null ) {
            processoJuridico.setDescricaoCaso( processoJuridicoDTO.getDescricaoCaso() );
        }
        if ( processoJuridicoDTO.getStatus() != null ) {
            processoJuridico.setStatus( processoJuridicoDTO.getStatus() );
        }
        if ( processoJuridico.getPartesEnvolvidas() != null ) {
            List<Pessoa> list = pessoaDTOListToPessoaList( processoJuridicoDTO.getPartesEnvolvidas() );
            if ( list != null ) {
                processoJuridico.getPartesEnvolvidas().clear();
                processoJuridico.getPartesEnvolvidas().addAll( list );
            }
        }
        else {
            List<Pessoa> list = pessoaDTOListToPessoaList( processoJuridicoDTO.getPartesEnvolvidas() );
            if ( list != null ) {
                processoJuridico.setPartesEnvolvidas( list );
            }
        }

        return processoJuridico;
    }

    protected Pessoa pessoaDTOToPessoa(PessoaDTO pessoaDTO) {
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

    protected List<Pessoa> pessoaDTOListToPessoaList(List<PessoaDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Pessoa> list1 = new ArrayList<Pessoa>( list.size() );
        for ( PessoaDTO pessoaDTO : list ) {
            list1.add( pessoaDTOToPessoa( pessoaDTO ) );
        }

        return list1;
    }

    protected PessoaDTO pessoaToPessoaDTO(Pessoa pessoa) {
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

    protected List<PessoaDTO> pessoaListToPessoaDTOList(List<Pessoa> list) {
        if ( list == null ) {
            return null;
        }

        List<PessoaDTO> list1 = new ArrayList<PessoaDTO>( list.size() );
        for ( Pessoa pessoa : list ) {
            list1.add( pessoaToPessoaDTO( pessoa ) );
        }

        return list1;
    }
}

package com.example.api_processos_juridicos.domain.pessoa;

import com.example.api_processos_juridicos.dto.pessoa.PessoaDTO;
import com.example.api_processos_juridicos.exceptions.ApiException;
import com.example.api_processos_juridicos.utils.DocumentoUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper = PessoaMapper.INSTANCE;

    private static final String ERRO_PESSOA_NAO_ENCONTRADA = "Pessoa não encontrada.";


    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa criar(PessoaDTO pessoaDTO) {
        String inscricaoFederal = normalizarInscricaoFederal(pessoaDTO.getInscricaoFederal());

        if (!validarInscricaoFederal(inscricaoFederal)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "O CPF/CNPJ de "+ pessoaDTO.getNomeCompleto() + " é inválido.");
        }

        return pessoaRepository.findByInscricaoFederal(inscricaoFederal)
                .orElseGet(() -> gravarPessoa(pessoaDTO, inscricaoFederal));
    }

    private boolean validarInscricaoFederal(String inscricaoFederal) {
        if (DocumentoUtils.isCpf(inscricaoFederal)) {
            return DocumentoUtils.isCpf(inscricaoFederal);
        } else if (DocumentoUtils.isCnpj(inscricaoFederal)) {
            return DocumentoUtils.isCnpj(inscricaoFederal);
        }
        return false;
    }

    private String normalizarInscricaoFederal(String inscricaoFederal) {
        return inscricaoFederal.replaceAll("[^0-9]", "");
    }

    private Pessoa gravarPessoa(PessoaDTO pessoaDTO, String inscricaoFederal) {
        Pessoa novaPessoa = pessoaMapper.toObject(pessoaDTO);
        novaPessoa.setInscricaoFederal(inscricaoFederal);
        return pessoaRepository.save(novaPessoa);
    }

    public PessoaDTO editar(Long idPessoa, PessoaDTO pessoaDTO) {
        Pessoa pessoa = buscarPorId(idPessoa);

        boolean inscricaoFederalAlterada = Objects.nonNull(pessoaDTO.getInscricaoFederal()) &&
                !pessoaDTO.getInscricaoFederal().isBlank() &&
                !pessoa.getInscricaoFederal().equals(pessoaDTO.getInscricaoFederal());

        if (inscricaoFederalAlterada) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "O CPF/CNPJ não pode ser alterado.");
        }

        Pessoa pessoaAtualizada = pessoaMapper.updateFromDTO(pessoaDTO, pessoa);

        return pessoaMapper.toDTO(pessoaRepository.save(pessoaAtualizada));
    }

    public Pessoa buscarPorId(Long idPessoa) {
        return pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new ApiException(HttpStatus.NO_CONTENT, ERRO_PESSOA_NAO_ENCONTRADA));
    }
}

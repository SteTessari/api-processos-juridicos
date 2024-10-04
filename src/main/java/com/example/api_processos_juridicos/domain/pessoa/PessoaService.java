package com.example.api_processos_juridicos.domain.pessoa;

import com.example.api_processos_juridicos.dto.pessoa.PessoaDTO;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper = PessoaMapper.INSTANCE;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa criar(PessoaDTO pessoaDTO) {
        String inscricaoFederal = normalizarInscricaoFederal(pessoaDTO.getInscricaoFederal());

        return pessoaRepository.findByInscricaoFederal(inscricaoFederal)
                .orElseGet(() -> gravarPessoa(pessoaDTO, inscricaoFederal));
    }

    private String normalizarInscricaoFederal(String inscricaoFederal) {
        return inscricaoFederal.replaceAll("[^0-9]", "");
    }

    private Pessoa gravarPessoa(PessoaDTO pessoaDTO, String inscricaoFederal) {
        Pessoa novaPessoa = pessoaMapper.toObject(pessoaDTO);
        novaPessoa.setInscricaoFederal(inscricaoFederal);
        return pessoaRepository.save(novaPessoa);
    }
}

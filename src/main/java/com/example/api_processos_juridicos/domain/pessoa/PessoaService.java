package com.example.api_processos_juridicos.domain.pessoa;

import com.example.api_processos_juridicos.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService extends TokenService {

    @Autowired
    private PessoaRepository pessoaRepository;

    private final PessoaMapper pessoaMapper = PessoaMapper.INSTANCE;

    public void criar(PessoaDTO pessoaDTO) {
        Optional<Pessoa> pessoaEncontrada = pessoaRepository.findByInscricaoFederal(pessoaDTO.getInscricaoFederal());

        if (pessoaEncontrada.isEmpty()) {
            Pessoa pessoa = pessoaMapper.toObject(pessoaDTO);
            pessoa.setInscricaoFederal(pessoaDTO.getInscricaoFederal().replaceAll("[^0-9]", ""));

            gravar(pessoa);
        }
    }

    private Pessoa gravar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }
}

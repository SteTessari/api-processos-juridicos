package com.example.api_processos_juridicos.domain.acao;

import com.example.api_processos_juridicos.exceptions.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcaoService {

    @Autowired
    private AcaoRepository acaoRepository;


    public Acao registrarAcao(Acao acao) {
        return acaoRepository.save(acao);
    }

    public List<Acao> buscarAcoesDoProcesso(String numeroProcesso) {
        List<Acao> acoes = acaoRepository.findByProcesso_NumeroProcesso(numeroProcesso);

        if (!acoes.isEmpty())
            return acoes;
        else
            throw new ApiException(HttpStatus.NO_CONTENT, "Nenhum processo encontrado.");
    }
}

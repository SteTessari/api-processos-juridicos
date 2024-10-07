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

    private static final String ERRO_ACAO_DO_PROCESSO_NAO_ENCONTRADA = "Nenhuma ação encontrada para o processo informado.";

    public Acao registrarAcao(Acao acao) {
        return acaoRepository.save(acao);
    }

    public List<Acao> buscarAcoesDoProcesso(String numeroProcesso) {
        List<Acao> acoes = acaoRepository.findByProcesso_NumeroProcesso(numeroProcesso);

        if (!acoes.isEmpty())
            return acoes;
        else
            throw new ApiException(HttpStatus.NO_CONTENT, ERRO_ACAO_DO_PROCESSO_NAO_ENCONTRADA);
    }

    public List<Acao> buscarAcoesDoProcessoPorTipo(String numeroProcesso, TipoAcao tipoAcao) {
        List<Acao> acoes = acaoRepository.findByProcesso_NumeroProcessoAndTipoAcao(numeroProcesso, tipoAcao);

        if (!acoes.isEmpty())
            return acoes;
        else
            throw new ApiException(HttpStatus.NO_CONTENT, ERRO_ACAO_DO_PROCESSO_NAO_ENCONTRADA);    }
}

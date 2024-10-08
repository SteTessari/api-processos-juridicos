package com.example.api_processos_juridicos.testeAutomatizado.acao;


import com.example.api_processos_juridicos.domain.acao.Acao;
import com.example.api_processos_juridicos.domain.acao.AcaoRepository;
import com.example.api_processos_juridicos.domain.acao.AcaoService;
import com.example.api_processos_juridicos.domain.acao.TipoAcao;
import com.example.api_processos_juridicos.domain.processo.ProcessoJuridico;
import com.example.api_processos_juridicos.exceptions.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class AcaoServiceTest {

    @Mock
    private AcaoRepository acaoRepository;

    @InjectMocks
    private AcaoService acaoService;

    private Acao acao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        acao = new Acao();
        ProcessoJuridico processoJuridico = new ProcessoJuridico();
        processoJuridico.setNumeroProcesso("12345");

        acao.setProcesso(processoJuridico);
        acao.setDescricao("Descrição da Ação");
        acao.setDataRegistro(LocalDate.now());
        acao.setTipoAcao(TipoAcao.AUDIENCIA);
    }

    @Test
    void registrarAcao_deveSalvarAcaoComSucesso() {
        when(acaoRepository.save(any(Acao.class))).thenReturn(acao);

        Acao acaoRegistrada = acaoService.registrarAcao(acao);

        assertNotNull(acaoRegistrada);
        assertEquals(acao.getProcesso(), acaoRegistrada.getProcesso());
        verify(acaoRepository, times(1)).save(acao);
    }

    @Test
    void buscarAcoesDoProcesso_deveRetornarListaDeAcoes() {
        List<Acao> acoes = Arrays.asList(acao);
        when(acaoRepository.findByProcesso_NumeroProcesso("12345")).thenReturn(acoes);

        List<Acao> resultado = acaoService.buscarAcoesDoProcesso("12345");

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(acaoRepository, times(1)).findByProcesso_NumeroProcesso("12345");
    }

    @Test
    void buscarAcoesDoProcesso_deveLancarExcecaoQuandoNaoExistirAcoes() {
        when(acaoRepository.findByProcesso_NumeroProcesso("12345")).thenReturn(Collections.emptyList());

        ApiException exception = assertThrows(ApiException.class, () -> acaoService.buscarAcoesDoProcesso("12345"));

        assertEquals(HttpStatus.NO_CONTENT, exception.getCode());
        assertEquals("Nenhuma ação encontrada para o processo informado.", exception.getMessage());
        verify(acaoRepository, times(1)).findByProcesso_NumeroProcesso("12345");
    }

    @Test
    void buscarAcoesDoProcessoPorTipo_deveRetornarListaDeAcoes() {
        List<Acao> acoes = Arrays.asList(acao);
        when(acaoRepository.findByProcesso_NumeroProcessoAndTipoAcao("12345", TipoAcao.AUDIENCIA)).thenReturn(acoes);

        List<Acao> resultado = acaoService.buscarAcoesDoProcessoPorTipo("12345", TipoAcao.AUDIENCIA);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(acaoRepository, times(1)).findByProcesso_NumeroProcessoAndTipoAcao("12345", TipoAcao.AUDIENCIA);
    }

    @Test
    void buscarAcoesDoProcessoPorTipo_deveLancarExcecaoQuandoNaoExistirAcoes() {
        when(acaoRepository.findByProcesso_NumeroProcessoAndTipoAcao("12345", TipoAcao.AUDIENCIA)).thenReturn(Collections.emptyList());

        ApiException exception = assertThrows(ApiException.class, () -> acaoService.buscarAcoesDoProcessoPorTipo("12345", TipoAcao.AUDIENCIA));

        assertEquals(HttpStatus.NO_CONTENT, exception.getCode());
        assertEquals("Nenhuma ação encontrada para o processo informado.", exception.getMessage());
        verify(acaoRepository, times(1)).findByProcesso_NumeroProcessoAndTipoAcao("12345", TipoAcao.AUDIENCIA);
    }
}

package com.example.api_processos_juridicos.testeAutomatizado.acao;

import com.example.api_processos_juridicos.controllers.AcaoController;
import com.example.api_processos_juridicos.domain.acao.Acao;
import com.example.api_processos_juridicos.domain.acao.AcaoMapper;
import com.example.api_processos_juridicos.domain.acao.AcaoService;
import com.example.api_processos_juridicos.domain.acao.TipoAcao;
import com.example.api_processos_juridicos.domain.processo.ProcessoJuridico;
import com.example.api_processos_juridicos.domain.processo.ProcessoJuridicoService;
import com.example.api_processos_juridicos.dto.acao.AcaoDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class AcaoControllerTest {

    @InjectMocks
    private AcaoController acaoController;

    @Mock
    private AcaoService acaoService;

    @Mock
    private ProcessoJuridicoService processoJuridicoService;
    private Validator validator;
    private final AcaoMapper acaoMapper = AcaoMapper.INSTANCE;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testRegistrar() {
        AcaoDTO acaoDTO = new AcaoDTO();
        acaoDTO.setNumeroProcesso("1234");
        acaoDTO.setTipoAcao(TipoAcao.AUDIENCIA);
        acaoDTO.setDescricao("teste");
        acaoDTO.setDataRegistro(LocalDate.now());

        ProcessoJuridico processoJuridico = new ProcessoJuridico();
        processoJuridico.setNumeroProcesso("1234");
        Acao acao = acaoMapper.toObject(acaoDTO);
        acao.setProcesso(processoJuridico);

        when(processoJuridicoService.buscarProcessoPorNumero(acaoDTO.getNumeroProcesso())).thenReturn(processoJuridico);
        when(acaoService.registrarAcao(any(Acao.class))).thenReturn(acao);

         AcaoDTO result = acaoController.registrar(acaoDTO);

         assertNotNull(result);
        verify(processoJuridicoService).buscarProcessoPorNumero(acaoDTO.getNumeroProcesso());
        verify(acaoService).registrarAcao(any(Acao.class));
    }
    @Test
    void testRegistrarSemInformarCamposObrigatorios() {
        AcaoDTO acaoDTO = new AcaoDTO();

        Set<ConstraintViolation<AcaoDTO>> violations = validator.validate(acaoDTO);

        assertFalse(violations.isEmpty());
        assertEquals(4, violations.size());
    }

    @Test
    void testBuscarAcoesDoProcesso() {
        String numeroProcesso = "1234";
        List<Acao> acoes = Collections.singletonList(new Acao());

        when(acaoService.buscarAcoesDoProcesso(numeroProcesso)).thenReturn(acoes);

        List<Acao> result = acaoController.buscarAcoesDoProcesso(numeroProcesso);

        assertEquals(acoes.size(), result.size());
        verify(acaoService).buscarAcoesDoProcesso(numeroProcesso);
    }

    @Test
    void testBuscarAcoesDoProcessoPorTipo() {
        String numeroProcesso = "1234";
        TipoAcao tipoAcao = TipoAcao.AUDIENCIA;
        List<Acao> acoes = Collections.singletonList(new Acao());

        when(acaoService.buscarAcoesDoProcessoPorTipo(numeroProcesso, tipoAcao)).thenReturn(acoes);

        List<Acao> result = acaoController.buscarAcoesDoProcessoPorTipo(numeroProcesso, tipoAcao);

        assertEquals(acoes.size(), result.size());
        verify(acaoService).buscarAcoesDoProcessoPorTipo(numeroProcesso, tipoAcao);
    }
}

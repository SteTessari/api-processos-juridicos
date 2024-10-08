package com.example.api_processos_juridicos.testeAutomatizado.processo;


import com.example.api_processos_juridicos.domain.pessoa.Pessoa;
import com.example.api_processos_juridicos.domain.processo.ProcessoJuridico;
import com.example.api_processos_juridicos.domain.processo.ProcessoJuridicoRepository;
import com.example.api_processos_juridicos.domain.processo.ProcessoJuridicoService;
import com.example.api_processos_juridicos.domain.processo.StatusProcesso;
import com.example.api_processos_juridicos.dto.processo.ProcessoFiltroDTO;
import com.example.api_processos_juridicos.dto.processo.ProcessoJuridicoDTO;
import com.example.api_processos_juridicos.exceptions.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class ProcessoJuridicoServiceTest {

    @Mock
    private ProcessoJuridicoRepository processoJuridicoRepository;

    @InjectMocks
    private ProcessoJuridicoService processoJuridicoService;

    private ProcessoJuridicoDTO processoJuridicoDTO;
    private ProcessoJuridico processoJuridico;
    private Pessoa pessoa1;
    private Pessoa pessoa2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        processoJuridicoDTO = new ProcessoJuridicoDTO();
        processoJuridicoDTO.setNumeroProcesso("12345");
        processoJuridicoDTO.setDataAbertura(LocalDate.now().minusDays(1)); // Data de abertura válida
        processoJuridicoDTO.setDescricaoCaso("Caso de Teste");
        processoJuridicoDTO.setStatus(StatusProcesso.ATIVO);
        processoJuridicoDTO.setPartesEnvolvidas(Collections.emptyList());

        processoJuridico = new ProcessoJuridico();
        processoJuridico.setNumeroProcesso("12345");
        processoJuridico.setDataAbertura(LocalDate.now().minusDays(1)); // Data de abertura válida
        processoJuridico.setDescricaoCaso("Caso de Teste");
        processoJuridico.setStatus(StatusProcesso.ATIVO);
        processoJuridico.setPartesEnvolvidas(Collections.emptyList());

        pessoa1 = new Pessoa();
        pessoa1.setInscricaoFederal("123456789");

        pessoa2 = new Pessoa();
        pessoa2.setInscricaoFederal("987654321");
    }

    @Test
    void criarProcesso() {
        when(processoJuridicoRepository.findByNumeroProcesso("12345")).thenReturn(Optional.empty());
        when(processoJuridicoRepository.save(any())).thenReturn(processoJuridico);

        ProcessoJuridicoDTO resultado = processoJuridicoService.criarProcesso(processoJuridicoDTO, List.of(new Pessoa()));

        assertNotNull(resultado);
        assertEquals("12345", resultado.getNumeroProcesso());
        verify(processoJuridicoRepository).findByNumeroProcesso("12345");
        verify(processoJuridicoRepository).save(any(ProcessoJuridico.class));
    }

    @Test
    void criarProcessoNumeroExistente() {
        when(processoJuridicoRepository.findByNumeroProcesso("12345")).thenReturn(Optional.of(new ProcessoJuridico()));

        ApiException exception = assertThrows(ApiException.class, () -> {
            processoJuridicoService.criarProcesso(processoJuridicoDTO, List.of(new Pessoa()));
        });

        assertEquals("O número do processo informado já existe.", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getCode());
    }

    @Test
    void criarProcessoDataAberturaInvalida() {
        processoJuridicoDTO.setDataAbertura(LocalDate.now().plusDays(1)); // Data futura

        ApiException exception = assertThrows(ApiException.class, () -> {
            processoJuridicoService.criarProcesso(processoJuridicoDTO, List.of(new Pessoa()));
        });

        assertEquals("A data de abertura não pode ser posterior à data atual.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getCode());
    }

    @Test
    void buscarProcessoDTOPorNumero() {
        ProcessoJuridico processoJuridico = new ProcessoJuridico();
        processoJuridico.setNumeroProcesso("12345");

        when(processoJuridicoRepository.findByNumeroProcesso("12345")).thenReturn(Optional.of(processoJuridico));

        ProcessoJuridicoDTO resultado = processoJuridicoService.buscarProcessoDTOPorNumero("12345");

        assertNotNull(resultado);
        assertEquals("12345", resultado.getNumeroProcesso());
        verify(processoJuridicoRepository).findByNumeroProcesso("12345");
    }

    @Test
    void buscarProcessoDTOPorNumeroNaoEncontrado() {
        when(processoJuridicoRepository.findByNumeroProcesso("54321")).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> {
            processoJuridicoService.buscarProcessoDTOPorNumero("54321");
        });

        assertEquals("Nenhum processo encontrado.", exception.getMessage());
        assertEquals(HttpStatus.NO_CONTENT, exception.getCode());
    }

    @Test
    void editarProcesso() {
        ProcessoJuridico processoJuridicoExistente = new ProcessoJuridico();
        processoJuridicoExistente.setNumeroProcesso("12345");

        when(processoJuridicoRepository.findByNumeroProcesso("12345")).thenReturn(Optional.of(processoJuridicoExistente));
        when(processoJuridicoRepository.save(any())).thenReturn(processoJuridicoExistente);

        ProcessoJuridicoDTO resultado = processoJuridicoService.editarProcesso("12345", processoJuridicoDTO);

        assertNotNull(resultado);
        assertEquals("12345", resultado.getNumeroProcesso());
        verify(processoJuridicoRepository).findByNumeroProcesso("12345");
        verify(processoJuridicoRepository).save(any(ProcessoJuridico.class));
    }

    @Test
    void editarProcessoNaoEncontrado() {
        when(processoJuridicoRepository.findByNumeroProcesso("54321")).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> {
            processoJuridicoService.editarProcesso("54321", processoJuridicoDTO);
        });

        assertEquals("Nenhum processo encontrado.", exception.getMessage());
        assertEquals(HttpStatus.NO_CONTENT, exception.getCode());
    }

    @Test
    void listarProcessosPorStatus() {
        when(processoJuridicoRepository.findByStatus(StatusProcesso.ATIVO)).thenReturn(List.of(new ProcessoJuridico()));

        List<ProcessoJuridicoDTO> resultado = processoJuridicoService.listarProcessosPorStatus(StatusProcesso.ATIVO);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(processoJuridicoRepository).findByStatus(StatusProcesso.ATIVO);
    }

    @Test
    void listarProcessosPorDataAbertura() {
        LocalDate dataAbertura = LocalDate.now().minusDays(5);
        when(processoJuridicoRepository.findByDataAberturaGreaterThanEqual(dataAbertura)).thenReturn(List.of(new ProcessoJuridico()));

        List<ProcessoJuridicoDTO> resultado = processoJuridicoService.listarProcessosPorDataAbertura(dataAbertura);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(processoJuridicoRepository).findByDataAberturaGreaterThanEqual(dataAbertura);
    }

    @Test
    void arquivarProcesso() {
        ProcessoJuridico processoJuridico = new ProcessoJuridico();
        processoJuridico.setNumeroProcesso("12345");
        processoJuridico.setStatus(StatusProcesso.ATIVO);

        when(processoJuridicoRepository.findByNumeroProcesso("12345")).thenReturn(Optional.of(processoJuridico));
        when(processoJuridicoRepository.save(any())).thenReturn(processoJuridico);

        processoJuridicoService.arquivar("12345");

        assertEquals(StatusProcesso.ARQUIVADO, processoJuridico.getStatus());
        verify(processoJuridicoRepository).findByNumeroProcesso("12345");
        verify(processoJuridicoRepository).save(processoJuridico);
    }

    @Test
    void arquivarProcessoNaoEncontrado() {
        when(processoJuridicoRepository.findByNumeroProcesso("54321")).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> {
            processoJuridicoService.arquivar("54321");
        });

        assertEquals("Nenhum processo encontrado.", exception.getMessage());
        assertEquals(HttpStatus.NO_CONTENT, exception.getCode());
    }

    @Test
    void filtrarProcessos() {
        ProcessoFiltroDTO filtroDTO = new ProcessoFiltroDTO();
        filtroDTO.setInscricoesFederaisDasPartes(List.of("123456789"));

        ProcessoJuridico processo1 = new ProcessoJuridico();
        processo1.setPartesEnvolvidas(List.of(pessoa1));

        ProcessoJuridico processo2 = new ProcessoJuridico();
        processo2.setPartesEnvolvidas(List.of(pessoa2));

        when(processoJuridicoRepository.findAll(Mockito.any(Example.class))).thenReturn(List.of(processo1, processo2));

        List<ProcessoJuridico> resultado = processoJuridicoService.filtrar(filtroDTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(processo1, resultado.get(0));
        verify(processoJuridicoRepository).findAll(Mockito.any(Example.class));
    }

    @Test
    void filtrarProcessosSemInscricaoFederal() {
        ProcessoFiltroDTO filtroDTO = new ProcessoFiltroDTO();

        ProcessoJuridico processo1 = new ProcessoJuridico();
        processo1.setPartesEnvolvidas(List.of(pessoa1));

        ProcessoJuridico processo2 = new ProcessoJuridico();
        processo2.setPartesEnvolvidas(List.of(pessoa2));

        when(processoJuridicoRepository.findAll(Mockito.any(Example.class))).thenReturn(List.of(processo1, processo2));

        List<ProcessoJuridico> resultado = processoJuridicoService.filtrar(filtroDTO);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(processoJuridicoRepository).findAll(Mockito.any(Example.class));
    }

    @Test
    void listarProcessosPorInscricaoFederalDasPartes() {
        List<String> inscricoesFederais = List.of("123456789", "987654321");

        ProcessoJuridico processo1 = new ProcessoJuridico();
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setInscricaoFederal("123456789");
        processo1.setPartesEnvolvidas(List.of(pessoa1));

        ProcessoJuridico processo2 = new ProcessoJuridico();
        Pessoa pessoa2 = new Pessoa();
        pessoa2.setInscricaoFederal("987654321");
        processo2.setPartesEnvolvidas(List.of(pessoa2));

        // Mockando o comportamento do repositório
        when(processoJuridicoRepository.findByPartesEnvolvidas_InscricaoFederalIn(inscricoesFederais)).thenReturn(List.of(processo1, processo2));

        List<ProcessoJuridicoDTO> resultado = processoJuridicoService.listarProcessosPorInscricaoFederalDasPartes(inscricoesFederais);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(processoJuridicoRepository).findByPartesEnvolvidas_InscricaoFederalIn(inscricoesFederais);
    }

}
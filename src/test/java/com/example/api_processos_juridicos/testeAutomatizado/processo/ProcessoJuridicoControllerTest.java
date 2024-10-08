package com.example.api_processos_juridicos.testeAutomatizado.processo;


import com.example.api_processos_juridicos.controllers.ProcessoJuridicoController;
import com.example.api_processos_juridicos.domain.pessoa.Pessoa;
import com.example.api_processos_juridicos.domain.pessoa.PessoaService;
import com.example.api_processos_juridicos.domain.pessoa.TipoParte;
import com.example.api_processos_juridicos.domain.processo.ProcessoJuridicoService;
import com.example.api_processos_juridicos.domain.processo.StatusProcesso;
import com.example.api_processos_juridicos.dto.pessoa.PessoaDTO;
import com.example.api_processos_juridicos.dto.processo.ProcessoJuridicoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class ProcessoJuridicoControllerTest {

    @Mock
    private ProcessoJuridicoService processoJuridicoService;

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private ProcessoJuridicoController processoJuridicoController;
    private Validator validator;

    private ProcessoJuridicoDTO processoJuridicoDTO;
    private PessoaDTO pessoaDTO;
    private Pessoa pessoa;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicialização de objetos para testes
        processoJuridicoDTO = new ProcessoJuridicoDTO();
        processoJuridicoDTO.setNumeroProcesso("12345");
        processoJuridicoDTO.setDataAbertura(LocalDate.now());
        processoJuridicoDTO.setDescricaoCaso("Teste");
        processoJuridicoDTO.setStatus(StatusProcesso.ATIVO);
        processoJuridicoDTO.setPartesEnvolvidas(new ArrayList<>());

        pessoaDTO = new PessoaDTO();
        pessoaDTO.setNomeCompleto("Teste Nome");
        pessoaDTO.setInscricaoFederal("28.941.462/0001-22");
        pessoaDTO.setEmail("teste@gmail.com");
        pessoaDTO.setTelefone("17 988234564");
        pessoaDTO.setTipoParte(TipoParte.AUTOR);

        pessoa = new Pessoa();
        pessoa.setNomeCompleto("Teste Nome");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        mockMvc = MockMvcBuilders.standaloneSetup(processoJuridicoController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void criarProcesso() {
        processoJuridicoDTO.setPartesEnvolvidas(List.of(pessoaDTO));
        when(pessoaService.criar(pessoaDTO)).thenReturn(pessoa);
        when(processoJuridicoService.criarProcesso(any(ProcessoJuridicoDTO.class), anyList())).thenReturn(processoJuridicoDTO);

        ProcessoJuridicoDTO resultado = processoJuridicoController.criarProcesso(processoJuridicoDTO);

        assertNotNull(resultado);
        assertEquals("12345", resultado.getNumeroProcesso());
        verify(pessoaService).criar(any(PessoaDTO.class));
        verify(processoJuridicoService).criarProcesso(any(ProcessoJuridicoDTO.class), anyList());
    }

    @Test
    void buscarProcesso() {
        when(processoJuridicoService.buscarProcessoDTOPorNumero("12345")).thenReturn(processoJuridicoDTO);

        ProcessoJuridicoDTO resultado = processoJuridicoController.buscarProcesso("12345");

        assertNotNull(resultado);
        assertEquals("12345", resultado.getNumeroProcesso());
        verify(processoJuridicoService).buscarProcessoDTOPorNumero("12345");
    }

    @Test
    void editarProcesso() {
        when(processoJuridicoService.editarProcesso("12345", processoJuridicoDTO)).thenReturn(processoJuridicoDTO);

        ProcessoJuridicoDTO resultado = processoJuridicoController.editarProcesso("12345", processoJuridicoDTO);

        assertNotNull(resultado);
        assertEquals("12345", resultado.getNumeroProcesso());
        verify(processoJuridicoService).editarProcesso("12345", processoJuridicoDTO);
    }

    @Test
    void criarProcessoComValidacaoErro() {
        ProcessoJuridicoDTO processoInvalido = new ProcessoJuridicoDTO();
        processoInvalido.setNumeroProcesso("");
        processoInvalido.setDataAbertura(null);
        processoInvalido.setDescricaoCaso("");
        processoInvalido.setStatus(null);
        processoInvalido.setPartesEnvolvidas(new ArrayList<>());

        Set<ConstraintViolation<ProcessoJuridicoDTO>> violations = validator.validate(processoInvalido);

        assertFalse(violations.isEmpty(), "Deveria haver violações de validação.");
        assertEquals(5, violations.size(), "O número de violações deve ser 5.");

        List<String> errosEsperados = new ArrayList<>(List.of(
                "Por favor informe o número do processo",
                "Por favor informe a data de abertura",
                "Por favor informe a descrição do caso",
                "Por favor informe o status do processo",
                "Por favor informe as partes envolvidas"
        ));

        List<String> errosObtidos = new ArrayList<>(violations.stream()
                .map(ConstraintViolation::getMessage)
                .toList());

        errosEsperados.sort(String::compareTo);
        errosObtidos.sort(String::compareTo);

        assertArrayEquals(errosEsperados.toArray(), errosObtidos.toArray(), "As mensagens de erro devem corresponder.");
    }

    @Test
    void buscarProcessoPorStatus() {
        when(processoJuridicoService.listarProcessosPorStatus(StatusProcesso.ATIVO)).thenReturn(List.of(new ProcessoJuridicoDTO()));

        List<ProcessoJuridicoDTO> processoJuridicoDTOS = processoJuridicoController.buscarPorStatus(StatusProcesso.ATIVO);

        assertNotNull(processoJuridicoDTOS);
        verify(processoJuridicoService).listarProcessosPorStatus(StatusProcesso.ATIVO);
    }

    @Test
    void buscarProcessoPorDataAbertura() {
        when(processoJuridicoService.listarProcessosPorDataAbertura(LocalDate.now())).thenReturn(List.of(new ProcessoJuridicoDTO()));

        List<ProcessoJuridicoDTO> processoJuridicoDTOS = processoJuridicoController.buscarPorDataDeAbertura(LocalDate.now());

        assertNotNull(processoJuridicoDTOS);
        verify(processoJuridicoService).listarProcessosPorDataAbertura(LocalDate.now());
    }

    @Test
    void buscarPorInscricaoFederalDasPartes() throws Exception {
        List<ProcessoJuridicoDTO> processos = List.of(processoJuridicoDTO);
        List<String> inscricoesFederais = List.of("28.941.462/0001-22");
        when(processoJuridicoService.listarProcessosPorInscricaoFederalDasPartes(inscricoesFederais)).thenReturn(processos);

        mockMvc.perform(MockMvcRequestBuilders.post("/processos-juridicos/buscar/por-inscricao-federal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inscricoesFederais)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numeroProcesso").value("12345"));

        verify(processoJuridicoService).listarProcessosPorInscricaoFederalDasPartes(inscricoesFederais);
    }

    @Test
    void arquivar() throws Exception {
        String numeroProcesso = "12345";
        mockMvc.perform(MockMvcRequestBuilders.put("/processos-juridicos/arquivar/{numeroProcesso}", numeroProcesso))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Processo arquivado com sucesso!"));

        verify(processoJuridicoService).arquivar(numeroProcesso);
    }

}
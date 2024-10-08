package com.example.api_processos_juridicos.testeAutomatizado.pessoa;

import com.example.api_processos_juridicos.controllers.PessoaController;
import com.example.api_processos_juridicos.domain.pessoa.PessoaService;
import com.example.api_processos_juridicos.dto.pessoa.PessoaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class PessoaControllerTest {

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private PessoaController pessoaController;

    private PessoaDTO pessoaDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pessoaDTO = new PessoaDTO();
        pessoaDTO.setNomeCompleto("Teste Nome");
    }

    @Test
    void editarPessoa() {
        Long idPessoa = 1L;
        PessoaDTO pessoaAtualizada = new PessoaDTO();
        pessoaAtualizada.setNomeCompleto("Nome Atualizado");

        when(pessoaService.editar(idPessoa, pessoaDTO)).thenReturn(pessoaAtualizada);

        PessoaDTO resultado = pessoaController.editar(idPessoa, pessoaDTO);

        assertNotNull(resultado);
        assertEquals("Nome Atualizado", resultado.getNomeCompleto());
        verify(pessoaService).editar(idPessoa, pessoaDTO);
    }
}
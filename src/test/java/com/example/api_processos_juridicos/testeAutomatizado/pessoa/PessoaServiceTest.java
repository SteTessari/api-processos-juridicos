package com.example.api_processos_juridicos.testeAutomatizado.pessoa;

import com.example.api_processos_juridicos.domain.pessoa.Pessoa;
import com.example.api_processos_juridicos.domain.pessoa.PessoaMapper;
import com.example.api_processos_juridicos.domain.pessoa.PessoaRepository;
import com.example.api_processos_juridicos.domain.pessoa.PessoaService;
import com.example.api_processos_juridicos.dto.pessoa.PessoaDTO;
import com.example.api_processos_juridicos.exceptions.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private PessoaMapper pessoaMapper;

    @InjectMocks
    private PessoaService pessoaService;

    private PessoaDTO pessoaDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pessoaDTO = new PessoaDTO();
        pessoaDTO.setNomeCompleto("Teste Nome");
    }

    @Test
    void criarPessoaComCpfValido() {
        String cpfValido = "12345678909"; // Exemplo de CPF válido
        pessoaDTO.setInscricaoFederal(cpfValido);

        when(pessoaRepository.findByInscricaoFederal(cpfValido)).thenReturn(Optional.empty());
        when(pessoaMapper.toObject(pessoaDTO)).thenReturn(new Pessoa());
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(new Pessoa());

        Pessoa pessoaCriada = pessoaService.criar(pessoaDTO);

        assertNotNull(pessoaCriada);
        verify(pessoaRepository).findByInscricaoFederal(cpfValido);
        verify(pessoaRepository).save(any(Pessoa.class));
    }


    @Test
    void criarPessoaComCnpjValido() {
        String cnpjValido = "28941462000122"; // Exemplo de CNPJ válido
        pessoaDTO.setInscricaoFederal(cnpjValido);

        when(pessoaRepository.findByInscricaoFederal(cnpjValido)).thenReturn(Optional.empty());
        when(pessoaMapper.toObject(pessoaDTO)).thenReturn(new Pessoa());
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(new Pessoa());

        Pessoa pessoaCriada = pessoaService.criar(pessoaDTO);

        assertNotNull(pessoaCriada);
        verify(pessoaRepository).findByInscricaoFederal(cnpjValido);
        verify(pessoaRepository).save(any(Pessoa.class));
    }

    @Test
    void criarPessoaComCpfInvalido() {
        String cpfInvalido = "11111111111"; // CPF repetido
        pessoaDTO.setInscricaoFederal(cpfInvalido);

        ApiException exception = assertThrows(ApiException.class, () -> {
            pessoaService.criar(pessoaDTO);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getCode());
        assertEquals("O CPF/CNPJ de Teste Nome é inválido.", exception.getMessage());
    }

    @Test
    void editarPessoaComAlteracaoInscricaoFederal() {
        Long idPessoa = 1L;
        Pessoa pessoaExistente = new Pessoa();
        pessoaExistente.setInscricaoFederal("12345678909");
        pessoaDTO.setInscricaoFederal("98765432100"); // CPF diferente

        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(pessoaExistente));

        ApiException exception = assertThrows(ApiException.class, () -> {
            pessoaService.editar(idPessoa, pessoaDTO);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getCode());
        assertEquals("O CPF/CNPJ não pode ser alterado.", exception.getMessage());
    }

    @Test
    void editarPessoa() {
        Long idPessoa = 1L;
        Pessoa pessoaExistente = new Pessoa();
        pessoaExistente.setInscricaoFederal("12345678909");

        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(pessoaExistente));

        pessoaService.editar(idPessoa, pessoaDTO);

        assertNull(pessoaDTO.getInscricaoFederal());
        verify(pessoaRepository).save(any(Pessoa.class));
    }

    @Test
    void buscarPessoaPorId() {
        Long idPessoa = 1L;
        Pessoa pessoaExistente = new Pessoa();

        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(pessoaExistente));

        Pessoa pessoaBuscada = pessoaService.buscarPorId(idPessoa);

        assertNotNull(pessoaBuscada);
        assertEquals(pessoaExistente, pessoaBuscada);
    }

    @Test
    void buscarPessoaPorIdNaoEncontrada() {
        Long idPessoa = 1L;

        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> {
            pessoaService.buscarPorId(idPessoa);
        });

        assertEquals(HttpStatus.NO_CONTENT, exception.getCode());
        assertEquals("Pessoa não encontrada.", exception.getMessage());
    }
}


package com.example.api_processos_juridicos.testeAutomatizado.usuario;


import com.example.api_processos_juridicos.domain.usuario.Usuario;
import com.example.api_processos_juridicos.domain.usuario.UsuarioRepository;
import com.example.api_processos_juridicos.domain.usuario.UsuarioService;
import com.example.api_processos_juridicos.dto.usuario.LoginDTO;
import com.example.api_processos_juridicos.dto.usuario.UsuarioDTO;
import com.example.api_processos_juridicos.exceptions.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioDTO usuarioDTO;
    private LoginDTO loginDTO;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail("test@example.com");
        usuarioDTO.setSenha("Senha@123");

        loginDTO = new LoginDTO("test@example.com", "Senha@123");

        usuario = new Usuario();
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());

    }

    @Test
    void testCriarUsuarioComSucesso() {
        when(usuarioRepository.findByEmail(usuarioDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(usuarioDTO.getSenha())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        usuarioService.criar(usuarioDTO);

        verify(usuarioRepository).findByEmail(usuarioDTO.getEmail());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void testCriarUsuarioJaCadastrado() {
        when(usuarioRepository.findByEmail(usuarioDTO.getEmail())).thenReturn(Optional.of(usuario));

        ApiException exception = assertThrows(ApiException.class, () -> usuarioService.criar(usuarioDTO));

        assertEquals(HttpStatus.CONFLICT, exception.getCode());
        assertEquals("O usuário já possui cadastro.", exception.getMessage());
    }


    @Test
    void testLoginUsuarioNaoEncontrado() {
        when(usuarioRepository.findByEmail(loginDTO.email())).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> usuarioService.login(loginDTO));

        assertEquals(HttpStatus.NOT_FOUND, exception.getCode());
        assertEquals("Usuário não encontrado.", exception.getMessage());
    }

    @Test
    void testLoginSenhaInvalida() {
        when(usuarioRepository.findByEmail(loginDTO.email())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(loginDTO.senha(), usuario.getSenha())).thenReturn(false);

        ApiException exception = assertThrows(ApiException.class, () -> usuarioService.login(loginDTO));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getCode());
        assertEquals("Email ou senha inválidos.", exception.getMessage());
    }
}

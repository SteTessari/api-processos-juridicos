package com.example.api_processos_juridicos.usuario.controller;

import com.example.api_processos_juridicos.controllers.usuario.UsuarioController;
import com.example.api_processos_juridicos.domain.usuario.UsuarioService;
import com.example.api_processos_juridicos.dto.usuario.LoginDTO;
import com.example.api_processos_juridicos.dto.usuario.UsuarioDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testCadastrarUsuario_UsuarioValido() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("user@example.com", "password123");

        doNothing().when(usuarioService).criar(usuarioDTO);

        String response = usuarioController.cadastrarUsuario(usuarioDTO);

        assertEquals("Usuário inserido com sucesso!", response);
    }

    @Test
    void testCadastrarUsuario_EmailInvalido() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("invalid-email", "password123");

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("deve ser um endereço de e-mail bem formado", violations.iterator().next().getMessage());
    }

    @Test
    void testCadastrarUsuario_EmailVazio() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("", "password123");

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Por favor informe o email", violations.iterator().next().getMessage());
    }

    @Test
    void testCadastrarUsuario_SenhaCurta() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("user@example.com", "123");

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("A Senha deve conter pelo menos 6 dígitos.", violations.iterator().next().getMessage());
    }

    @Test
    void testLogin() {
        LoginDTO loginDTO = new LoginDTO("teste123@gmail.com", "Teste123@");

        String expectedResponse = "Login realizado com sucesso!";
        when(usuarioService.login(loginDTO)).thenReturn(expectedResponse);

        String response = usuarioController.login(loginDTO);

        assertEquals(expectedResponse, response);
    }
}
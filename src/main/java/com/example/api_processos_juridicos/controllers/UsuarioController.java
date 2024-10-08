package com.example.api_processos_juridicos.controllers;

import com.example.api_processos_juridicos.domain.usuario.UsuarioService;
import com.example.api_processos_juridicos.dto.usuario.LoginDTO;
import com.example.api_processos_juridicos.dto.usuario.UsuarioDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public String cadastrarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        usuarioService.criar(usuarioDTO);
        return "Usuário inserido com sucesso!";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        return usuarioService.login(loginDTO);
    }
}

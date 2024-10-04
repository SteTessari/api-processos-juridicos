package com.example.api_processos_juridicos.controllers.usuario;

import com.example.api_processos_juridicos.domain.usuario.LoginDTO;
import com.example.api_processos_juridicos.domain.usuario.UsuarioDTO;
import com.example.api_processos_juridicos.domain.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

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

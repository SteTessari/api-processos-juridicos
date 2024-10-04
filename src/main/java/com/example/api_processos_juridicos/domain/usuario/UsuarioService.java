package com.example.api_processos_juridicos.domain.usuario;

import com.example.api_processos_juridicos.exceptions.ApiException;
import com.example.api_processos_juridicos.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UsuarioService extends TokenService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UsuarioMapper usuarioMapper = UsuarioMapper.INSTANCE;

    public void criar(UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByEmail(usuarioDTO.getEmail());

        if (usuarioEncontrado.isPresent())
            throw new ApiException(HttpStatus.CONFLICT, "O usuário já possui cadastro.");

        if (!isSenhaValida(usuarioDTO))
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "A senha deve conter pelo menos 1 letra maiúscula, 1 letra minúscula e 1 caractere especial.");

        Usuario usuario = usuarioMapper.toObject(usuarioDTO);
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        gravar(usuario);
    }

    public String login(LoginDTO loginDTO) {
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.email())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        if (passwordEncoder.matches(loginDTO.senha(), usuario.getSenha())) {

            return gerarToken(usuario);
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Email ou senha inválidos.");
        }
    }
    private boolean isSenhaValida(UsuarioDTO usuarioDTO) {
        String regexSenhaValida = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
        return Pattern.matches(regexSenhaValida, usuarioDTO.getSenha());
    }

    private Usuario gravar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
}

package com.example.api_processos_juridicos.security;

import com.example.api_processos_juridicos.domain.usuario.Usuario;
import com.example.api_processos_juridicos.domain.usuario.UsuarioRepository;
import com.example.api_processos_juridicos.dto.token.JwtTokenDTO;
import com.example.api_processos_juridicos.exceptions.ApiException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Collections;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String token = this.recoverToken(request);
            JwtTokenDTO jwtTokenDTO = tokenService.validarToken(token);

            if (Objects.nonNull(jwtTokenDTO)) {
                Usuario usuario = usuarioRepository.findById(jwtTokenDTO.getIdUsuario()).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
                var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Não Autorizado: Token inválido");
        }
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
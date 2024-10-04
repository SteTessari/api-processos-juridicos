package com.example.api_processos_juridicos.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.api_processos_juridicos.domain.usuario.Usuario;
import com.example.api_processos_juridicos.dto.token.JwtTokenDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    protected String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            Map<String, Object> headerClaims = new HashMap<>();
            headerClaims.put("typ", "JWT");
            headerClaims.put("alg", "HS256");

            return JWT.create()
                    .withHeader(headerClaims)
                    .withIssuer("login-auth-api")
                    .withClaim("idUsuario", usuario.getId())
                    .withExpiresAt(gerarDataExpiracaoToken())
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar o token.", e);
        }
    }


    public JwtTokenDTO validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token);

            Long idUsuario = decodedJWT.getClaim("idUsuario").asLong();

            return new JwtTokenDTO(idUsuario);

        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private Instant gerarDataExpiracaoToken() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }

}

package com.example.api_processos_juridicos.security;

import com.example.api_processos_juridicos.domain.usuario.TipoParte;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class JwtTokenDTO implements Serializable {
    private Long id;
    private TipoParte tipoParte;

}

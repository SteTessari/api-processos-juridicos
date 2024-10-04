package com.example.api_processos_juridicos.dto.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class JwtTokenDTO implements Serializable {
    private Long idUsuario;

}

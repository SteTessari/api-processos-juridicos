package com.example.api_processos_juridicos.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO implements Serializable {
    @NotBlank
    private String nomeCompleto;

    @NotBlank
    private String inscricaoFederal;

    @NotNull
    private TipoParte tipoParte;

    @NotBlank
    private String telefone;

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6, message = "A Senha deve conter pelo menos 6 dígitos.")
    private String senha;

}

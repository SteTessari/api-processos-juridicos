package com.example.api_processos_juridicos.domain.usuario;

import com.example.api_processos_juridicos.domain.pessoa.TipoParte;
import jakarta.validation.constraints.Email;
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
    @NotBlank(message = "Por favor informe o nome completo")
    private String nomeCompleto;

    @NotBlank(message = "Por favor informe o CPF/CNPJ")
    private String inscricaoFederal;

    @NotNull
    private TipoParte tipoParte;

    @NotBlank(message = "Por favor informe o telefone")
    private String telefone;

    @Email
    @NotBlank(message = "Por favor informe o email")
    private String email;

    @NotBlank(message = "Por favor informe a senha")
    @Size(min = 6, message = "A Senha deve conter pelo menos 6 dígitos.")
    private String senha;

}

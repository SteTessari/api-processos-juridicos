package com.example.api_processos_juridicos.domain.processo;

import com.example.api_processos_juridicos.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoJuridicoDTO implements Serializable {
    @NotBlank(message = "Por favor informe o número do processo")
    private String numeroProcesso;

    @NotNull(message = "Por favor informe a data de abertura")
    private LocalDate dataAbertura;

    @NotBlank(message = "Por favor informe a descrição do caso")
    private String descricaoCaso;

    @NotNull(message = "Por favor informe o status do processo")
    private StatusProcesso status;

    @NotEmpty(message = "Por favor informe as partes envolvidas")
    private List<Usuario> partesEnvolvidas = new ArrayList<>();
}

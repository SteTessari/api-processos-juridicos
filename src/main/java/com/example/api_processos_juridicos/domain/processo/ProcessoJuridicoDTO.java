package com.example.api_processos_juridicos.domain.processo;

import com.example.api_processos_juridicos.domain.usuario.Usuario;
import jakarta.persistence.*;
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

    private String numeroProcesso;

    private LocalDate dataAbertura;

    private String descricaoCaso;

    private StatusProcesso status;

    private List<Usuario> partesEnvolvidas = new ArrayList<>();
}

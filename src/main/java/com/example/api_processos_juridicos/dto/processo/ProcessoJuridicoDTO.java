package com.example.api_processos_juridicos.dto.processo;

import com.example.api_processos_juridicos.domain.processo.StatusProcesso;
import com.example.api_processos_juridicos.dto.pessoa.PessoaDTO;
import jakarta.validation.Valid;
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

    @Valid
    @NotEmpty(message = "Por favor informe as partes envolvidas")
    private List<PessoaDTO> partesEnvolvidas = new ArrayList<>();
}

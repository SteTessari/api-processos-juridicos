package com.example.api_processos_juridicos.dto.acao;

import com.example.api_processos_juridicos.domain.acao.TipoAcao;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcaoDTO implements Serializable {
    @NotNull(message = "Por favor informe o tipo da ação")
    private TipoAcao tipoAcao;
    @NotNull(message = "Por favor informe a data de registro")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataRegistro;
    @NotBlank(message = "Por favor informe a descrição")
    private String descricao;
    @NotBlank(message = "Por favor informe o número do processo")
    private String numeroProcesso;

}

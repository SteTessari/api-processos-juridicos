package com.example.api_processos_juridicos.dto.processo;

import com.example.api_processos_juridicos.domain.processo.StatusProcesso;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoFiltroDTO implements Serializable {
    private StatusProcesso statusProcesso;
    private LocalDate dataAbertura;
    private List<String> inscricoesFederaisDasPartes;
}

package com.example.api_processos_juridicos.domain.acao;

import com.example.api_processos_juridicos.domain.processo.ProcessoJuridico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "acao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Acao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoAcao tipoAcao;  // Petição, Audiência, Sentença

    private LocalDate dataRegistro;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "processo_id")
    private ProcessoJuridico processo;

    public Acao(TipoAcao tipoAcao, LocalDate dataRegistro, String descricao, ProcessoJuridico processo) {
        this.tipoAcao = tipoAcao;
        this.dataRegistro = dataRegistro;
        this.descricao = descricao;
        this.processo = processo;
    }
}

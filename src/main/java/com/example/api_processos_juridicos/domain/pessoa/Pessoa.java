package com.example.api_processos_juridicos.domain.pessoa;

import com.example.api_processos_juridicos.domain.processo.ProcessoJuridico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pessoa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(nullable = false, unique = true)
    private String inscricaoFederal;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoParte tipoParte;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "processo_id")
    private ProcessoJuridico processoJuridico;
}

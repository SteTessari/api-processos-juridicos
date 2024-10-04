package com.example.api_processos_juridicos.domain.processo;

import com.example.api_processos_juridicos.domain.pessoa.Pessoa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "processo_juridico")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoJuridico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroProcesso;

    private LocalDate dataAbertura;

    private String descricaoCaso;

    @Enumerated(EnumType.STRING)
    private StatusProcesso status;

    @OneToMany(mappedBy = "processoJuridico", cascade = CascadeType.ALL)
    private List<Pessoa> partesEnvolvidas = new ArrayList<>();
}

package com.example.api_processos_juridicos.domain.acao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcaoRepository extends JpaRepository<Acao, Long> {
    List<Acao> findByProcesso_NumeroProcesso(String numeroProcesso);

    List<Acao> findByProcesso_NumeroProcessoAndTipoAcao(String numeroProcesso, TipoAcao tipoAcao);
}

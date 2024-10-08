package com.example.api_processos_juridicos.domain.processo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessoJuridicoRepository extends JpaRepository<ProcessoJuridico, Long> {
    List<ProcessoJuridico> findByPartesEnvolvidas_InscricaoFederalIn(Collection<String> inscricaoFederals);
    List<ProcessoJuridico> findByDataAberturaGreaterThanEqual(LocalDate dataAbertura);
    Optional<ProcessoJuridico> findByNumeroProcesso(String numeroProcesso);

    List<ProcessoJuridico> findByStatus(StatusProcesso status);
    
}

package com.example.api_processos_juridicos.domain.processo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessoJuridicoRepository extends JpaRepository<ProcessoJuridico, Long> {
    Optional<ProcessoJuridico> findByNumeroProcesso(String numeroProcesso);

    List<ProcessoJuridico> findByStatus(StatusProcesso status);
}

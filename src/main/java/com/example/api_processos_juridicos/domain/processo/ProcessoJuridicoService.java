package com.example.api_processos_juridicos.domain.processo;

import com.example.api_processos_juridicos.exceptions.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProcessoJuridicoService {

    @Autowired
    private ProcessoJuridicoRepository processoJuridicoRepository;

    private final ProcessoJuridicoMapper processoJuridicoMapper = ProcessoJuridicoMapper.INSTANCE;

    public ProcessoJuridicoDTO criarProcesso(ProcessoJuridicoDTO processo) {
        buscarProcessoPorNumero(processo.getNumeroProcesso());

        ProcessoJuridico processoJuridico = processoJuridicoMapper.toObject(processo);
        processoJuridico = processoJuridicoRepository.save(processoJuridico);

        return processoJuridicoMapper.toDTO(processoJuridico);
    }

    public ProcessoJuridicoDTO buscarProcessoPorNumero(String numeroProcesso) {
        Optional<ProcessoJuridico> processoJuridicoOptional = processoJuridicoRepository.findByNumeroProcesso(numeroProcesso);

        if (processoJuridicoOptional.isEmpty())
            throw new ApiException(HttpStatus.NO_CONTENT, "Nenhum processo encontrado.");

        return processoJuridicoMapper.toDTO(processoJuridicoOptional.get());
    }

    public List<ProcessoJuridicoDTO> listarProcessosPorStatus(StatusProcesso status) {
        List<ProcessoJuridico> processoJuridicoList = processoJuridicoRepository.findByStatus(status);
        return processoJuridicoList.stream().map(processoJuridicoMapper::toDTO).collect(Collectors.toList());
    }
}

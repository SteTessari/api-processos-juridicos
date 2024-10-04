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
        buscarProcessoDTOPorNumero(processo.getNumeroProcesso());

        ProcessoJuridico processoJuridico = processoJuridicoMapper.toObject(processo);
        processoJuridico = processoJuridicoRepository.save(processoJuridico);

        return processoJuridicoMapper.toDTO(processoJuridico);
    }

    public ProcessoJuridicoDTO buscarProcessoDTOPorNumero(String numeroProcesso) {
        Optional<ProcessoJuridico> processoJuridicoOptional = processoJuridicoRepository.findByNumeroProcesso(numeroProcesso);

        if (processoJuridicoOptional.isEmpty())
            throw new ApiException(HttpStatus.NO_CONTENT, "Nenhum processo encontrado.");

        return processoJuridicoMapper.toDTO(processoJuridicoOptional.get());
    }
    private ProcessoJuridico buscarProcessoPorNumero(String numeroProcesso) {
        Optional<ProcessoJuridico> processoJuridicoOptional = processoJuridicoRepository.findByNumeroProcesso(numeroProcesso);

        if (processoJuridicoOptional.isEmpty())
            throw new ApiException(HttpStatus.NO_CONTENT, "Nenhum processo encontrado.");

        return processoJuridicoOptional.get();
    }

    public List<ProcessoJuridicoDTO> listarProcessosPorStatus(StatusProcesso status) {
        List<ProcessoJuridico> processoJuridicoList = processoJuridicoRepository.findByStatus(status);
        return processoJuridicoList.stream().map(processoJuridicoMapper::toDTO).collect(Collectors.toList());
    }

    public ProcessoJuridicoDTO editarProcesso(String numeroProcesso, ProcessoJuridicoDTO processoJuridicoAtualizadoDTO) {
        ProcessoJuridico processoJuridico = buscarProcessoPorNumero(numeroProcesso);

        ProcessoJuridico processoJuridicoAtualizado = processoJuridicoMapper.updateFromDTO(processoJuridicoAtualizadoDTO, processoJuridico);
        processoJuridicoAtualizado = processoJuridicoRepository.save(processoJuridicoAtualizado);

        return processoJuridicoMapper.toDTO(processoJuridicoAtualizado);
    }
}

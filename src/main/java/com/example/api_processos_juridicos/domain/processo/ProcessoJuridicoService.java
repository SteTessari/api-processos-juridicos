package com.example.api_processos_juridicos.domain.processo;

import com.example.api_processos_juridicos.domain.pessoa.Pessoa;
import com.example.api_processos_juridicos.dto.processo.ProcessoFiltroDTO;
import com.example.api_processos_juridicos.dto.processo.ProcessoJuridicoDTO;
import com.example.api_processos_juridicos.exceptions.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProcessoJuridicoService {

    private final ProcessoJuridicoRepository processoJuridicoRepository;
    private static final ProcessoJuridicoMapper processoJuridicoMapper = ProcessoJuridicoMapper.INSTANCE;

    private static final String ERRO_NUMERO_PROCESSO_EXISTENTE = "O número do processo informado já existe.";
    private static final String ERRO_DATA_ABERTURA_INVALIDA = "A data de abertura não pode ser posterior à data atual.";
    private static final String ERRO_NENHUM_PROCESSO_ENCONTRADO = "Nenhum processo encontrado.";


    public ProcessoJuridicoDTO criarProcesso(ProcessoJuridicoDTO processoDTO, List<Pessoa> partesEnvolvidas) {
        verificarSeNumeroDoProcessoJaExiste(processoDTO.getNumeroProcesso());
        validarDataAbertura(processoDTO.getDataAbertura());

        ProcessoJuridico processoJuridico = processoJuridicoMapper.toObject(processoDTO);
        processoJuridico.setPartesEnvolvidas(partesEnvolvidas);

        processoJuridico = processoJuridicoRepository.save(processoJuridico);
        return processoJuridicoMapper.toDTO(processoJuridico);
    }

    public ProcessoJuridicoDTO buscarProcessoDTOPorNumero(String numeroProcesso) {
        return processoJuridicoRepository.findByNumeroProcesso(numeroProcesso)
                .map(processoJuridicoMapper::toDTO)
                .orElseThrow(() -> new ApiException(HttpStatus.NO_CONTENT, ERRO_NENHUM_PROCESSO_ENCONTRADO));
    }

    public ProcessoJuridico buscarProcessoPorNumero(String numeroProcesso) {
        return processoJuridicoRepository.findByNumeroProcesso(numeroProcesso)
                .orElseThrow(() -> new ApiException(HttpStatus.NO_CONTENT, ERRO_NENHUM_PROCESSO_ENCONTRADO));
    }

    public ProcessoJuridicoDTO editarProcesso(String numeroProcesso, ProcessoJuridicoDTO processoAtualizadoDTO) {
        ProcessoJuridico processoJuridico = buscarProcessoPorNumero(numeroProcesso);
        ProcessoJuridico processoJuridicoAtualizado = processoJuridicoMapper.updateFromDTO(processoAtualizadoDTO, processoJuridico);

        processoJuridicoAtualizado = processoJuridicoRepository.save(processoJuridicoAtualizado);
        return processoJuridicoMapper.toDTO(processoJuridicoAtualizado);
    }

    public List<ProcessoJuridicoDTO> listarProcessosPorStatus(StatusProcesso status) {
        return converterParaDTO(processoJuridicoRepository.findByStatus(status));
    }

    public List<ProcessoJuridicoDTO> listarProcessosPorDataAbertura(LocalDate dataAbertura) {
        return converterParaDTO(processoJuridicoRepository.findByDataAberturaGreaterThanEqual(dataAbertura));
    }

    public List<ProcessoJuridicoDTO> listarProcessosPorInscricaoFederalDasPartes(List<String> inscricoesFederaisDasPartes) {
        return converterParaDTO(processoJuridicoRepository.findByPartesEnvolvidas_InscricaoFederalIn(inscricoesFederaisDasPartes));
    }

    private void verificarSeNumeroDoProcessoJaExiste(String numeroProcesso) {
        if (processoJuridicoRepository.findByNumeroProcesso(numeroProcesso).isPresent()) {
            throw new ApiException(HttpStatus.CONFLICT, ERRO_NUMERO_PROCESSO_EXISTENTE);
        }
    }

    private void validarDataAbertura(LocalDate dataAbertura) {
        if (dataAbertura.isAfter(LocalDate.now())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ERRO_DATA_ABERTURA_INVALIDA);
        }
    }

    private List<ProcessoJuridicoDTO> converterParaDTO(List<ProcessoJuridico> processos) {
        return processos.stream()
                .map(processoJuridicoMapper::toDTO)
                .toList();
    }

    public void arquivar(String numeroProcesso) {
        ProcessoJuridico processoJuridico = buscarProcessoPorNumero(numeroProcesso);

        processoJuridico.setStatus(StatusProcesso.ARQUIVADO);

        processoJuridicoRepository.save(processoJuridico);
    }

    public List<ProcessoJuridico> filtrar(ProcessoFiltroDTO filtroDTO) {
        ProcessoJuridico processoJuridico = processoJuridicoMapper.toObject(filtroDTO);
        List<ProcessoJuridico> processos = processoJuridicoRepository.findAll(Example.of(processoJuridico));

        if (Objects.nonNull(filtroDTO.getInscricoesFederaisDasPartes())) {
            processos = processos.stream()
                    .filter(processo -> processo.getPartesEnvolvidas().stream()
                            .anyMatch(pessoa -> filtroDTO.getInscricoesFederaisDasPartes().contains(pessoa.getInscricaoFederal()))
                    )
                    .toList();
        }

        return processos;
    }
}

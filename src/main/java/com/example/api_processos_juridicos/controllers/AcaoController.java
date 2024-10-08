package com.example.api_processos_juridicos.controllers;

import com.example.api_processos_juridicos.domain.acao.Acao;
import com.example.api_processos_juridicos.domain.acao.AcaoMapper;
import com.example.api_processos_juridicos.domain.acao.AcaoService;
import com.example.api_processos_juridicos.domain.acao.TipoAcao;
import com.example.api_processos_juridicos.domain.processo.ProcessoJuridico;
import com.example.api_processos_juridicos.domain.processo.ProcessoJuridicoService;
import com.example.api_processos_juridicos.dto.acao.AcaoDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acao")
@RequiredArgsConstructor
public class AcaoController {
    private final AcaoService acaoService;
    private final ProcessoJuridicoService processoJuridicoService;
    private static final AcaoMapper acaoMapper = AcaoMapper.INSTANCE;


    @PostMapping
    public AcaoDTO registrar(@Valid @RequestBody AcaoDTO acaoDTO) {
        Acao acao = acaoMapper.toObject(acaoDTO);

        ProcessoJuridico processoJuridico = processoJuridicoService.buscarProcessoPorNumero(acaoDTO.getNumeroProcesso());
        acao.setProcesso(processoJuridico);

        return acaoMapper.toDTO(acaoService.registrarAcao(acao));
    }

    @GetMapping("/{numeroProcesso}")
    public List<Acao> buscarAcoesDoProcesso(@PathVariable String numeroProcesso) {
        return acaoService.buscarAcoesDoProcesso(numeroProcesso);
    }

    @GetMapping("/por-tipo/{numeroProcesso}")
    public List<Acao> buscarAcoesDoProcessoPorTipo(@PathVariable String numeroProcesso, @RequestParam("tipo") TipoAcao tipoAcao) {
        return acaoService.buscarAcoesDoProcessoPorTipo(numeroProcesso, tipoAcao);
    }
}

package com.example.api_processos_juridicos.controllers.usuario;

import com.example.api_processos_juridicos.domain.acao.Acao;
import com.example.api_processos_juridicos.domain.acao.AcaoMapper;
import com.example.api_processos_juridicos.domain.acao.AcaoService;
import com.example.api_processos_juridicos.domain.processo.ProcessoJuridico;
import com.example.api_processos_juridicos.domain.processo.ProcessoJuridicoService;
import com.example.api_processos_juridicos.dto.acao.AcaoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acao")
public class AcaoController {
    @Autowired
    private AcaoService acaoService;
    @Autowired
    private ProcessoJuridicoService processoJuridicoService;

    private final AcaoMapper acaoMapper = AcaoMapper.INSTANCE;


    @PostMapping
    public Acao registrar(@Valid @RequestBody AcaoDTO acaoDTO){
        Acao acao = acaoMapper.toObject(acaoDTO);

        ProcessoJuridico processoJuridico = processoJuridicoService.buscarProcessoPorNumero(acaoDTO.getNumeroProcesso());
        acao.setProcesso(processoJuridico);

        return acaoService.registrarAcao(acao);
    }

    @GetMapping("/{numeroProcesso}")
    public List<Acao> buscarAcoesDoProcesso(@PathVariable String numeroProcesso){
        return acaoService.buscarAcoesDoProcesso(numeroProcesso);
    }
}

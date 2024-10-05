package com.example.api_processos_juridicos.controllers.usuario;

import com.example.api_processos_juridicos.domain.pessoa.Pessoa;
import com.example.api_processos_juridicos.domain.pessoa.PessoaService;
import com.example.api_processos_juridicos.domain.processo.ProcessoJuridico;
import com.example.api_processos_juridicos.domain.processo.ProcessoJuridicoService;
import com.example.api_processos_juridicos.domain.processo.StatusProcesso;
import com.example.api_processos_juridicos.dto.pessoa.PessoaDTO;
import com.example.api_processos_juridicos.dto.processo.ProcessoFiltroDTO;
import com.example.api_processos_juridicos.dto.processo.ProcessoJuridicoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/processos-juridicos")
public class ProcessoJuridicoController {

    @Autowired
    private ProcessoJuridicoService processoJuridicoService;
    @Autowired
    private PessoaService pessoaService;


    @PostMapping
    public ProcessoJuridicoDTO criarProcesso(@Valid @RequestBody ProcessoJuridicoDTO processo) {
        List<Pessoa> partesEnvolvidas = new ArrayList<>();

        for (PessoaDTO parteEnvolvida : processo.getPartesEnvolvidas()) {
            partesEnvolvidas.add(pessoaService.criar(parteEnvolvida));
        }

        return processoJuridicoService.criarProcesso(processo, partesEnvolvidas);
    }

    @GetMapping("/buscar/{numero}")
    public ProcessoJuridicoDTO buscarProcesso(@PathVariable String numero) {
        return processoJuridicoService.buscarProcessoDTOPorNumero(numero);
    }

    @PostMapping("/{numeroProcesso}")
    public ProcessoJuridicoDTO editarProcesso(@PathVariable String numeroProcesso, @RequestBody ProcessoJuridicoDTO processo) {
        return processoJuridicoService.editarProcesso(numeroProcesso, processo);
    }

    @GetMapping("/buscar/por-status")
    public List<ProcessoJuridicoDTO> buscarPorStatus(@RequestParam StatusProcesso statusProcesso) {
        return processoJuridicoService.listarProcessosPorStatus(statusProcesso);
    }

    @GetMapping("/buscar/por-data-abertura")
    public List<ProcessoJuridicoDTO> buscarPorDataDeAbertura(@RequestParam LocalDate dataAbertura) {
        return processoJuridicoService.listarProcessosPorDataAbertura(dataAbertura);
    }

    @PostMapping("/buscar/por-inscricao-federal")
    public List<ProcessoJuridicoDTO> buscarPorInscricaoFederalDasPartes(@RequestBody List<String> inscricoesFederais) {
        return processoJuridicoService.listarProcessosPorInscricaoFederalDasPartes(inscricoesFederais);
    }

    @PutMapping("/arquivar/{numeroProcesso}")
    public String arquivar(@PathVariable String numeroProcesso) {
        processoJuridicoService.arquivar(numeroProcesso);
        return "Processo arquivado com sucesso!";
    }

    @PostMapping("/filtrar")
    public List<ProcessoJuridico> filtrar(@RequestBody ProcessoFiltroDTO filtroDTO) {
        return processoJuridicoService.filtrar(filtroDTO);
    }
}

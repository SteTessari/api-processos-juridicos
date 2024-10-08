package com.example.api_processos_juridicos.controllers;

import com.example.api_processos_juridicos.domain.pessoa.PessoaService;
import com.example.api_processos_juridicos.dto.pessoa.PessoaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoa")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    @PutMapping("/editar/{idPessoa}")
    public PessoaDTO editar(@PathVariable Long idPessoa, @RequestBody PessoaDTO pessoaDTO) {
        return pessoaService.editar(idPessoa, pessoaDTO);
    }
}

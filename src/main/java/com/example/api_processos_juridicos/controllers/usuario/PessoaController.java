package com.example.api_processos_juridicos.controllers.usuario;

import com.example.api_processos_juridicos.domain.pessoa.PessoaService;
import com.example.api_processos_juridicos.dto.pessoa.PessoaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PutMapping("/editar/{idPessoa}")
    public PessoaDTO editar(@PathVariable Long idPessoa, @RequestBody PessoaDTO pessoaDTO){
        return pessoaService.editar(idPessoa, pessoaDTO);
    }
}

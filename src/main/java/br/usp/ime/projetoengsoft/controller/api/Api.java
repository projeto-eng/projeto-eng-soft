package br.usp.ime.projetoengsoft.controller.api;

import br.usp.ime.projetoengsoft.dto.ApiDto;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
class Api {

    @GetMapping("/configuracoes")
    public ApiDto get() {
        ApiDto config = new ApiDto("Projeto de Engenharia de Software", "MAC0332 - Engenharia de Software (2024)");
        return config;
    }
}



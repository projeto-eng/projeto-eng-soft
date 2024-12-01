package br.usp.ime.projetoengsoft.controller.api;

import br.usp.ime.projetoengsoft.dto.ConfiguracaoDto;
import br.usp.ime.projetoengsoft.service.ConfiguracaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/configuracoes")
class Configuracoes {

    @Autowired
    private ConfiguracaoService configuracaoService;

    @GetMapping()
    public ResponseEntity<ConfiguracaoDto> getConfiguracao() {
        return new ResponseEntity<>(configuracaoService.findByModo("DESENVOLVIMENTO"), HttpStatus.OK);
    }
}



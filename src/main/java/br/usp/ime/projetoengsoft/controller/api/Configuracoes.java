package br.usp.ime.projetoengsoft.controller.api;

import br.usp.ime.projetoengsoft.dto.ConfiguracaoDto;
import br.usp.ime.projetoengsoft.model.Configuracao;
import br.usp.ime.projetoengsoft.repository.ConfiguracaoRepository;
import br.usp.ime.projetoengsoft.service.ConfiguracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configuracoes")
class Configuracoes {

    @Autowired
    private ConfiguracaoService configuracaoService;

    @GetMapping()
    public ConfiguracaoDto get() {
        return configuracaoService.findByModo("DESENVOLVIMENTO");
    }
}



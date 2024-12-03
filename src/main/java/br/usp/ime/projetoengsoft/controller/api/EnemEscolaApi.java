package br.usp.ime.projetoengsoft.controller.api;

import java.util.List;
import java.util.Optional;

import br.usp.ime.projetoengsoft.model.EnemEscola;
import br.usp.ime.projetoengsoft.model.Escola;
import br.usp.ime.projetoengsoft.model.Uf;
import br.usp.ime.projetoengsoft.service.EnemEscolaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enem-escola")
public class EnemEscolaApi {
    @Autowired
    private EnemEscolaService service;

    @GetMapping()
    public ResponseEntity<List<EnemEscola>> buscaEnemEscola(@RequestParam Integer codigoEscola) {
        return new ResponseEntity<>(service.buscaEnemEscolaPorCodigoEscola(codigoEscola), HttpStatus.OK);
    }

    @GetMapping("/ufs")
    public ResponseEntity<List<Uf>> buscaUfs() {
        return new ResponseEntity<>(service.buscaUfs(), HttpStatus.OK);
    }

    @GetMapping("/escolas")
    public ResponseEntity<List<Escola>> buscaEscolas(
        @RequestParam Optional<String> nome,
        @RequestParam Optional<String> siglaUf,
        @RequestParam Optional<String> codigoMunicipio) {
        
        if (nome.isPresent()) {
            return new ResponseEntity<>(service.buscaEscolaPorNome(nome.get()), HttpStatus.OK);
        }

        if (siglaUf.isPresent()) {
            return new ResponseEntity<>(service.buscaEscolasPorUf(siglaUf.get()), HttpStatus.OK);
        }

        if (codigoMunicipio.isPresent()) {
            return new ResponseEntity<>(service.buscaEscolaPorCodigoMunicipio(Integer.parseInt(codigoMunicipio.get())), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

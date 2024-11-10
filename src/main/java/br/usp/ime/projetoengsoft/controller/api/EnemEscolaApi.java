package br.usp.ime.projetoengsoft.controller.api;

import java.util.List;

import br.usp.ime.projetoengsoft.dto.EscolaReq;
import br.usp.ime.projetoengsoft.model.EnemEscola;
import br.usp.ime.projetoengsoft.model.Escola;
import br.usp.ime.projetoengsoft.model.Municipio;
import br.usp.ime.projetoengsoft.model.Uf;
import br.usp.ime.projetoengsoft.service.EnemEscolaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<Escola>> buscaEscolas(@RequestBody EscolaReq escolaReq) {
        if (escolaReq.getNome() != null) {
            return new ResponseEntity<>(service.buscaEscolaPorNome(escolaReq.getNome()), HttpStatus.OK);
        }

        if (escolaReq.getSiglaUf() != null) {
            return new ResponseEntity<>(service.buscaEscolasPorUf(escolaReq.getSiglaUf()), HttpStatus.OK);
        }

        if (escolaReq.getCodigoMunicipio() != null) {
            return new ResponseEntity<>(service.buscaEscolaPorCodigoMunicipio(escolaReq.getCodigoMunicipio()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/municipios")
    public ResponseEntity<List<Municipio>> buscaMunicipios(@RequestParam String siglaUf) {
        return new ResponseEntity<>(service.buscaMunicipiosPorSiglaUf(siglaUf), HttpStatus.OK);
    }
}

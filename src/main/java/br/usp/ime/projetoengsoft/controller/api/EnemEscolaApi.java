package br.usp.ime.projetoengsoft.controller.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.usp.ime.projetoengsoft.model.EnemEscola;
import br.usp.ime.projetoengsoft.model.Escola;
import br.usp.ime.projetoengsoft.model.Municipio;
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

        List<Escola> escolas = new ArrayList<>();
        
        List<Escola> escolasNome = new ArrayList<>();
        List<Escola> escolasUf = new ArrayList<>();
        List<Escola> escolasMunicipio = new ArrayList<>();

        if (nome.isPresent()) {
            escolasNome.addAll(service.buscaEscolaPorNome(nome.get()));
        }

        if (siglaUf.isPresent()) {
            escolasUf.addAll(service.buscaEscolasPorUf(siglaUf.get()));
        }

        if (codigoMunicipio.isPresent()) {
            escolasMunicipio.addAll(service.buscaEscolaPorCodigoMunicipio(Integer.parseInt(codigoMunicipio.get())));
        }

        // Interseção
        if (nome.isPresent() && siglaUf.isPresent() && codigoMunicipio.isPresent()) {
            escolasNome.retainAll(escolasUf);
            escolasNome.retainAll(escolasMunicipio);
            escolas.addAll(escolasNome);
        } else if (nome.isPresent() && siglaUf.isPresent()) {
            escolasNome.retainAll(escolasUf);
            escolas.addAll(escolasNome);
        } else if (nome.isPresent() && codigoMunicipio.isPresent()) {
            escolasNome.retainAll(escolasMunicipio);
            escolas.addAll(escolasNome);
        } else if (siglaUf.isPresent() && codigoMunicipio.isPresent()) {
            escolasUf.retainAll(escolasMunicipio);
            escolas.addAll(escolasUf);
        } else if (nome.isPresent()) {
            escolas.addAll(escolasNome);
        } else if (siglaUf.isPresent()) {
            escolas.addAll(escolasUf);
        } else if (codigoMunicipio.isPresent()) {
            escolas.addAll(escolasMunicipio);
        }

        if (!escolas.isEmpty()) {
            return new ResponseEntity<>(escolas, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/municipios")
    public ResponseEntity<List<Municipio>> buscaMunicipios(@RequestParam String siglaUf) {
        return new ResponseEntity<>(service.buscaMunicipiosPorSiglaUf(siglaUf), HttpStatus.OK);
    }
}

package br.usp.ime.projetoengsoft.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.usp.ime.projetoengsoft.model.EnemEscola;
import br.usp.ime.projetoengsoft.model.Escola;
import br.usp.ime.projetoengsoft.model.Municipio;
import br.usp.ime.projetoengsoft.model.Uf;
import br.usp.ime.projetoengsoft.repository.EnemEscolaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnemEscolaService {

    @Autowired
    private final EnemEscolaRepository repository;

    public EnemEscolaService(EnemEscolaRepository repository) {
        this.repository = repository;
    }

    public List<EnemEscola> buscaEnemEscolaPorCodigoEscola(Integer codigo) {
        return repository.findByCodigoEscola(codigo);
    }

    public List<Municipio> buscaMunicipiosPorSiglaUf(String siglaUf) {
        return repository.findAllMunicipiosBySiglaUf(siglaUf);
    }

    public List<Uf> buscaUfs() {
        return repository.findAllUf();
    }

    public List<Escola> buscaEscolasPorUf(String siglaUf) {
        return repository.findAllEscolaBySiglaUf(siglaUf);
    }

    public List<Escola> buscaEscolaPorCodigoMunicipio(Integer codigo) {
        return repository.findAllEscolaByCodigoMunicipio(codigo);
    }

    public List<Escola> buscaEscolaPorNome(String nome) {
        return repository.findAllEscolaByNomeContaining(nome);
    }
}

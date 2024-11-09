package br.usp.ime.projetoengsoft.service;

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
}

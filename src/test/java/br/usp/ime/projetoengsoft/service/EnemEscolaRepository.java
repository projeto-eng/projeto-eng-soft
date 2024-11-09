package br.usp.ime.projetoengsoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
public class EnemEscolaRepository {
    @Autowired
    private EnemEscolaRepository repository;


}

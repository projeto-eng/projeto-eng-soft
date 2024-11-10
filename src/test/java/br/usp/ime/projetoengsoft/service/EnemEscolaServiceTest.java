package br.usp.ime.projetoengsoft.service;

import br.usp.ime.projetoengsoft.repository.EnemEscolaRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EnemEscolaServiceTest {
    @Autowired
    private EnemEscolaService service;

    @SpyBean
    private EnemEscolaRepository repository;

    @Test
    public void servicoComErroNoBanco() {
        when(repository.findByCodigoEscola(any())).thenThrow(new RuntimeException());
        when(repository.findAllEscolaByCodigoMunicipio(any())).thenThrow(new RuntimeException());
        when(repository.findAllEscolaByNomeContaining("teste")).thenThrow(new RuntimeException());
        when(repository.findAllEscolaBySiglaUf(any())).thenThrow(new RuntimeException());
        when(repository.findAllUf()).thenThrow(new RuntimeException());
        when(repository.findAllMunicipiosBySiglaUf(any())).thenThrow(new RuntimeException());

        assertThrows(Exception.class, () -> service.buscaEnemEscolaPorCodigoEscola(124));
        assertThrows(Exception.class, () -> service.buscaEscolaPorCodigoMunicipio(124));
        assertThrows(Exception.class, () -> service.buscaEscolaPorNome("teste"));
        assertThrows(Exception.class, () -> service.buscaEscolasPorUf("SP"));
        assertThrows(Exception.class, () -> service.buscaUfs());
        assertThrows(Exception.class, () -> service.buscaMunicipiosPorSiglaUf("SP"));
    }
}

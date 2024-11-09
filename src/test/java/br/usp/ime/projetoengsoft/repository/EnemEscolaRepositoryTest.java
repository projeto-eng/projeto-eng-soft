package br.usp.ime.projetoengsoft.repository;

import java.util.List;

import br.usp.ime.projetoengsoft.model.EnemEscola;
import br.usp.ime.projetoengsoft.model.Escola;
import br.usp.ime.projetoengsoft.model.Municipio;
import br.usp.ime.projetoengsoft.model.Uf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
public class EnemEscolaRepositoryTest {

    @Autowired
    private EnemEscolaRepository enemEscolaRepository;

    @Test
    public void tamanhoDoBancoDeDados() {
        Assertions.assertEquals(172305, enemEscolaRepository.count());
    }

    @Test
    public void buscaSiglasUf() {
        List<Uf> listaUf = enemEscolaRepository.findAllUf();
        Assertions.assertEquals(27, listaUf.size());
        listaUf.forEach(uf -> {
            Assertions.assertNotNull(uf.getSigla());
            Assertions.assertFalse(uf.getSigla().isEmpty());
        });
    }

    @Test
    public void buscaMuniciosPorSiglaUf() {
        List<Uf> listaUf = enemEscolaRepository.findAllUf();
        listaUf.forEach(uf -> {
            List<Municipio> listaMunicipios = enemEscolaRepository.findAllMunicipiosBySiglaUf(uf.getSigla());
            Assertions.assertFalse(listaMunicipios.isEmpty());
        });
    }

    @Test
    public void buscaTodosEnemEscolaPorCodigoEscola() {
        List<EnemEscola> enemEscola = enemEscolaRepository.findByCodigoEscola(11000058);
        Assertions.assertFalse(enemEscola.isEmpty());
    }

    @Test
    public void buscaEscolasPorSiglaUf() {
        List<Uf> listaUf = enemEscolaRepository.findAllUf();
        List<Escola> listaEscola = enemEscolaRepository.findAllEscolaBySiglaUf(listaUf.getFirst().getSigla());
        Assertions.assertFalse(listaEscola.isEmpty());
    }

    @Test
    public void buscaEscolasPorCodigoMunicipio() {
        List<Uf> listaUf = enemEscolaRepository.findAllUf();
        List<Municipio> listaMunicipio = enemEscolaRepository.findAllMunicipiosBySiglaUf(listaUf.getFirst().getSigla());
        List<Escola> listaEscolas = enemEscolaRepository.findAllEscolaByCodigoMunicipio(listaMunicipio.getFirst().getId());
        Assertions.assertFalse(listaEscolas.isEmpty());
        listaEscolas.forEach(escola -> {
            Assertions.assertTrue(escola.getNomes().length > 0);
        });
    }

    @Test
    public void buscaEscolasPorNome() {
        List<Uf> listaUf = enemEscolaRepository.findAllUf();
        Escola escola = enemEscolaRepository.findAllEscolaBySiglaUf(listaUf.getFirst().getSigla()).getFirst();
        List<Escola> listaEscolas = enemEscolaRepository.findAllEscolaByNomeContaining(escola.getNomes()[0]);
        Assertions.assertFalse(listaEscolas.isEmpty());
        Assertions.assertEquals(escola.getId(), listaEscolas.getFirst().getId());
    }

    @Test
    public void buscaMunicipiosPorSiglaInexistente() {
        List<Municipio> listaMunicipios = enemEscolaRepository.findAllMunicipiosBySiglaUf("NA");
        Assertions.assertTrue(listaMunicipios.isEmpty());
    }

    @Test
    public void buscaTodosEnemEscolaPorCodigoEscolaInvalido() {
        List<EnemEscola> enemEscola = enemEscolaRepository.findByCodigoEscola(-1);
        Assertions.assertTrue(enemEscola.isEmpty());
    }

    @Test
    public void buscaEscolasPorSiglaUfInvalida() {
        List<Escola> listaEscola = enemEscolaRepository.findAllEscolaBySiglaUf("NA");
        Assertions.assertTrue(listaEscola.isEmpty());
    }

    @Test
    public void buscaEscolasPorCodigoMunicipioIncorreto() {
        List<Escola> listaEscolas = enemEscolaRepository.findAllEscolaByCodigoMunicipio(-1);
        Assertions.assertTrue(listaEscolas.isEmpty());
    }

    @Test
    public void buscaEscolaPorNomeInvalido() {
        List<Escola> listaEscolas = enemEscolaRepository.findAllEscolaByNomeContaining("DFDFDJN");
        Assertions.assertTrue(listaEscolas.isEmpty());
    }
}

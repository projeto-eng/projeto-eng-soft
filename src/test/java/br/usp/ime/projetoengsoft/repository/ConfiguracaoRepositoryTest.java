package br.usp.ime.projetoengsoft.repository;

import java.util.List;

import jakarta.validation.ValidationException;

import br.usp.ime.projetoengsoft.migration.MongoInitializer;
import br.usp.ime.projetoengsoft.model.Configuracao;
import br.usp.ime.projetoengsoft.model.Pessoa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ContextConfiguration(initializers = MongoInitializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConfiguracaoRepositoryTest {

    @Autowired
    private ConfiguracaoRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;

    @Test
    public void criaConfiguracaoValida() {
        List<Pessoa> desenvolvedores = pessoaRepository.findPessoaByFuncaoIs("Desenvolvedor Web");
        assertNotNull(desenvolvedores);
        assertFalse(desenvolvedores.isEmpty());

        Configuracao config = Configuracao
                .builder()
                .host("http://localhost:3000")
                .modo("PRODUCAO")
                .descricao("descricao do projeto")
                .integrantes(desenvolvedores)
                .build();

        config = repository.save(config);
        Configuracao configSalva = repository.findById(config.getId()).get();
        assertNotNull(configSalva);
        assertEquals(config, configSalva);
    }

    @Test
    public void criaConfiguracaoSemIntegrantes() {
        Configuracao config = Configuracao
                .builder()
                .host("http://localhost:3000")
                .modo("PRODUCAO")
                .descricao("descricao do projeto")
                .integrantes(List.of())
                .build();
        assertThrows(ValidationException.class, () -> repository.save(config));
    }

    @Test
    public void criaConfiguracaoComIntegranteInvalido() {
        Configuracao config = Configuracao
                .builder()
                .host("http://localhost:3000")
                .modo("PRODUCAO")
                .descricao("descricao do projeto")
                .integrantes(List.of(new Pessoa("-1", "Teste", "Função Teste", "http://github.com")))
                .build();
        assertThrows(ValidationException.class, () -> repository.save(config));
    }

    @Test
    public void criaConfiguracaoSemHost() {
        // Host nulo
        Configuracao config = Configuracao
                .builder()
                .modo("PRODUCAO")
                .descricao("descricao do projeto")
                .integrantes(pessoaRepository.findPessoaByFuncaoIs("Desenvolvedor Web"))
                .build();
        assertThrows(ValidationException.class, () -> repository.save(config));

        // Host em branco
        Configuracao config2 = Configuracao
                .builder()
                .host("")
                .modo("PRODUCAO")
                .descricao("descricao do projeto")
                .integrantes(pessoaRepository.findPessoaByFuncaoIs("Desenvolvedor Web"))
                .build();
        assertThrows(ValidationException.class, () -> repository.save(config2));
    }
}

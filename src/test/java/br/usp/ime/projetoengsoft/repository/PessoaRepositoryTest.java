package br.usp.ime.projetoengsoft.repository;

import java.util.List;

import jakarta.validation.ValidationException;

import br.usp.ime.projetoengsoft.migration.MongoInitializer;
import br.usp.ime.projetoengsoft.model.Pessoa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(initializers = MongoInitializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PessoaRepositoryTest {
    @Autowired
    private PessoaRepository repository;

    @Test
    public void criaPessoaValida() {
        Pessoa pessoa = Pessoa
                .builder()
                .nome("Teste")
                .funcao("Teste")
                .urlGithub("http://github.com")
                .build();
        pessoa = repository.save(pessoa);
        Pessoa pessoaSalva = repository.findById(pessoa.getId()).get();
        assertEquals(pessoa, pessoaSalva);
    }

    @Test
    public void criaPessoaComUrlInvalida() {
        Pessoa pessoa = Pessoa
                .builder()
                .nome("Teste")
                .funcao("Teste")
                .urlGithub("github.com")
                .build();
        assertThrows(ValidationException.class, () -> repository.save(pessoa));
    }

    @Test
    public void buscaDesenvolvedores() {
        List<Pessoa> desenvolvedores = repository.findPessoaByFuncaoIs("Desenvolvedor Web");
        assertFalse(desenvolvedores.isEmpty());
        assertFalse(desenvolvedores.size() > 6);
        desenvolvedores.forEach(Assertions::assertNotNull);
        desenvolvedores.forEach(x -> System.out.println(x.toString()));
    }

    @Test
    public void atualizaPessoaValida() {
        Pessoa pessoa = Pessoa
                .builder()
                .nome("Teste")
                .funcao("Teste")
                .urlGithub("http://github.com")
                .build();
        pessoa = repository.save(pessoa);
        pessoa.setNome("Teste Update");
        repository.save(pessoa);
        Pessoa pessoaSalva = repository.findById(pessoa.getId()).get();
        assertEquals(pessoa, pessoaSalva);
    }

    @Test
    public void deletaPessoaValida() {
        Pessoa pessoa = Pessoa
                .builder()
                .nome("Teste")
                .funcao("Teste")
                .urlGithub("http://github.com")
                .build();
        pessoa = repository.save(pessoa);
        String id = pessoa.getId();
        repository.delete(pessoa);
        assertTrue(repository.findById(id).isEmpty());
    }
}

package br.usp.ime.projetoengsoft;

import br.usp.ime.projetoengsoft.repository.ConfiguracaoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfiguracaoDtoRepositoryTest {
    @Autowired
    private ConfiguracaoRepository repository;

    @Test
    public void teste() {
        br.usp.ime.projetoengsoft.model.Configuracao configuracao = new br.usp.ime.projetoengsoft.model.Configuracao();
        configuracao.setDescricao("OK");
        repository.save(configuracao);
    }
}

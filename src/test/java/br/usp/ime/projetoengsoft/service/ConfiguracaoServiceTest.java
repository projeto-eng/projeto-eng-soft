package br.usp.ime.projetoengsoft.service;

import java.util.List;

import br.usp.ime.projetoengsoft.dto.ConfiguracaoDto;
import br.usp.ime.projetoengsoft.migration.MongoInitializer;
import br.usp.ime.projetoengsoft.model.Configuracao;
import br.usp.ime.projetoengsoft.model.Pessoa;
import br.usp.ime.projetoengsoft.repository.ConfiguracaoRepository;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ContextConfiguration(initializers = MongoInitializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConfiguracaoServiceTest {
    @Autowired
    ConfiguracaoService configuracaoService;
    @SpyBean
    private ConfiguracaoRepository configuracaoRepository;
    private final ModelMapper mapper = new ModelMapper();

    @Test
    public void buscaConfiguracaoValidaPeloModo() {
        Configuracao configEntrada = Configuracao
                .builder()
                .id("1")
                .modo("MODO_TESTE")
                .host("http://localhost:8080")
                .descricao("Teste")
                .integrantes(List.of(new Pessoa("1", "Integrante Teste", "Teste", "http://github.com")))
                .build();
        ConfiguracaoDto configSaida = mapper.map(configEntrada, ConfiguracaoDto.class);
        doReturn(configEntrada).when(configuracaoRepository).findByModo("MODO_TESTE");
        ConfiguracaoDto resultado = configuracaoService.findByModo("MODO_TESTE");
        assertEquals(configSaida, resultado);
    }

    @Test
    public void buscaConfiguracaoInvalida() {
        assertThrows(Exception.class, () -> configuracaoService.findByModo("MODO_INVALIDO"));
    }

    @Test
    public void buscaConfiguracaoValidaERepositorioProduzExcecao() {
        when(configuracaoRepository.findByModo("DESENVOLVIMENTO")).thenThrow(new RuntimeException());
        assertThrows(Exception.class, () -> configuracaoService.findByModo("DESENVOLVIMENTO"));
    }
}

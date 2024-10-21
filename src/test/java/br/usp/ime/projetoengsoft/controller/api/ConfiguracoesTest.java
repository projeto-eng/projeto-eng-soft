package br.usp.ime.projetoengsoft.controller.api;

import java.nio.charset.StandardCharsets;
import java.util.List;

import br.usp.ime.projetoengsoft.dto.ConfiguracaoDto;
import br.usp.ime.projetoengsoft.exception.ControllerExceptionHandler;
import br.usp.ime.projetoengsoft.exception.MensagemErro;
import br.usp.ime.projetoengsoft.migration.MongoInitializer;
import br.usp.ime.projetoengsoft.model.Configuracao;
import br.usp.ime.projetoengsoft.model.Pessoa;
import br.usp.ime.projetoengsoft.service.ConfiguracaoService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(initializers = MongoInitializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConfiguracoesTest {
    ControllerExceptionHandler exceptionHandler = new ControllerExceptionHandler();
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private ConfiguracaoService configuracaoService;
    private final ModelMapper mapper = new ModelMapper();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void obtemConfiguracaoValida() throws Exception {
        Configuracao configEngrada = Configuracao
                .builder()
                .id("1")
                .nomeApp("teste")
                .modo("MODO_TESTE")
                .host("http://localhost")
                .descricao("Teste")
                .integrantes(List.of(Pessoa.builder().id("1").nome("teste").urlGithub("http://github.com").build()))
                .build();

        ConfiguracaoDto configSaida = mapper.map(configEngrada, ConfiguracaoDto.class);
        when(configuracaoService.findByModo("DESENVOLVIMENTO")).thenReturn(configSaida);
        MvcResult result = mockMvc.perform(get("/api/configuracoes")
                                          .contentType("application/json"))
                                  .andExpect(status().isOk())
                                  .andReturn();
        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ConfiguracaoDto configResultado = objectMapper.readValue(json, new TypeReference<>() { });
        assertEquals(configSaida, configResultado);
    }

    @Test
    public void obtemConfiguracaoQuandoOcorreExcessao() throws Exception {
        MensagemErro msgSaida = new MensagemErro(500, "Erro ao processar a solicitação.");
        when(configuracaoService.findByModo("DESENVOLVIMENTO")).thenThrow(new RuntimeException());
        MvcResult result = mockMvc.perform(get("/api/configuracoes")
                                          .contentType("application/json")).andReturn();
        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        MensagemErro configResultado = objectMapper.readValue(json, new TypeReference<>() { });
        System.out.println(json);
        assertEquals(msgSaida, configResultado);
    }
}

package br.usp.ime.projetoengsoft.controller.api;

import java.nio.charset.StandardCharsets;
import java.util.List;

import br.usp.ime.projetoengsoft.exception.MensagemErro;
import br.usp.ime.projetoengsoft.model.EnemEscola;
import br.usp.ime.projetoengsoft.model.Escola;
import br.usp.ime.projetoengsoft.model.Municipio;
import br.usp.ime.projetoengsoft.model.Uf;
import br.usp.ime.projetoengsoft.service.EnemEscolaService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@WebAppConfiguration
public class EnemEscolaApiTest {
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private EnemEscolaService enemEscolaService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void buscaEnemEscolaPorCodigoEscola() throws Exception {
        EnemEscola enemEscola = EnemEscola
                .builder()
                .NO_ESCOLA_EDUCACENSO("Escola teste")
                .codigoEscola(1234)
                .build();
        when(enemEscolaService.buscaEnemEscolaPorCodigoEscola(1234)).thenReturn(List.of(enemEscola));
        MvcResult result = mockMvc.perform(get("/api/enem-escola?codigoEscola=1234")
                                          .contentType("application/json"))
                                  .andExpect(status().isOk())
                                  .andReturn();
        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<EnemEscola> listaEnemEscola = objectMapper.readValue(json, new TypeReference<>() { });
        Assertions.assertEquals(enemEscola, listaEnemEscola.getFirst());
    }

    @Test
    public void buscaUfs() throws Exception {
        Uf uf1 = new Uf(123, "ABC");
        Uf uf2 = new Uf(1234, "ABC");
        List<Uf> listaUfs = List.of(uf1, uf2);
        when(enemEscolaService.buscaUfs()).thenReturn(listaUfs);
        MvcResult result = mockMvc.perform(get("/api/enem-escola/ufs")
                                          .contentType("application/json"))
                                  .andExpect(status().isOk())
                                  .andReturn();
        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<Uf> listaUfsRes = objectMapper.readValue(json, new TypeReference<>() { });
        Assertions.assertEquals(listaUfs, listaUfsRes);
    }

    @Test
    public void buscaEscolasPorNome() throws Exception {
        Escola e1 = new Escola(123, List.of("escola1").toArray(new String[0]));
        Escola e2 = new Escola(1234, List.of("escola2").toArray(new String[0]));
        List<Escola> listaEscola = List.of(e1, e2);
        when(enemEscolaService.buscaEscolaPorNome("escola")).thenReturn(listaEscola);
        MvcResult result = mockMvc.perform(get("/api/enem-escola/escolas")
                                          .content("{ \"nome\" : \"escola\" }")
                                          .contentType("application/json")
                                  )
                                  .andExpect(status().isOk())
                                  .andReturn();
        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<Escola> listaEscolaRes = objectMapper.readValue(json, new TypeReference<>() { });
        Assertions.assertEquals(listaEscola, listaEscolaRes);
    }

    @Test
    public void buscaEscolasPorSiglaUf() throws Exception {
        Escola e1 = new Escola(123, List.of("escola1").toArray(new String[0]));
        Escola e2 = new Escola(1234, List.of("escola2").toArray(new String[0]));
        List<Escola> listaEscola = List.of(e1, e2);
        when(enemEscolaService.buscaEscolasPorUf("ABC")).thenReturn(listaEscola);
        MvcResult result = mockMvc.perform(get("/api/enem-escola/escolas")
                                          .content("{ \"siglaUf\" : \"ABC\" }")
                                          .contentType("application/json")
                                  )
                                  .andExpect(status().isOk())
                                  .andReturn();
        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<Escola> listaEscolaRes = objectMapper.readValue(json, new TypeReference<>() { });
        Assertions.assertEquals(listaEscola, listaEscolaRes);
    }

    @Test
    public void buscaEscolasPorMunicipio() throws Exception {
        Escola e1 = new Escola(123, List.of("escola1").toArray(new String[0]));
        Escola e2 = new Escola(1234, List.of("escola2").toArray(new String[0]));
        List<Escola> listaEscola = List.of(e1, e2);
        when(enemEscolaService.buscaEscolaPorCodigoMunicipio(123)).thenReturn(listaEscola);
        MvcResult result = mockMvc.perform(get("/api/enem-escola/escolas")
                                          .content("{ \"codigoMunicipio\" : 123 }")
                                          .contentType("application/json")
                                  )
                                  .andExpect(status().isOk())
                                  .andReturn();
        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<Escola> listaEscolaRes = objectMapper.readValue(json, new TypeReference<>() { });
        Assertions.assertEquals(listaEscola, listaEscolaRes);
    }

    @Test
    public void buscaMunicipiosPorSiglaUf() throws Exception {
        Municipio m1 = new Municipio(123, "Municipio 1");
        Municipio m2 = new Municipio(1234, "Municipio 2");
        List<Municipio> listaMunicipio = List.of(m1, m2);
        when(enemEscolaService.buscaMunicipiosPorSiglaUf("ABC")).thenReturn(listaMunicipio);
        MvcResult result = mockMvc.perform(get("/api/enem-escola/municipios?siglaUf=ABC")
                                          .contentType("application/json")
                                  )
                                  .andExpect(status().isOk())
                                  .andReturn();
        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<Municipio> listaMunicipioRes = objectMapper.readValue(json, new TypeReference<>() { });
        Assertions.assertEquals(listaMunicipio, listaMunicipioRes);
    }

    @Test
    public void buscaDadosComServicoQueGeraExcessao() throws Exception {
        MensagemErro msgSaida = new MensagemErro(500, "Erro ao processar a solicitação.");

        when(enemEscolaService.buscaEnemEscolaPorCodigoEscola(any())).thenThrow(new RuntimeException());
        when(enemEscolaService.buscaEscolasPorUf(any())).thenThrow(new RuntimeException());
        when(enemEscolaService.buscaMunicipiosPorSiglaUf(any())).thenThrow(new RuntimeException());
        when(enemEscolaService.buscaEscolaPorNome("teste")).thenThrow(new RuntimeException());
        when(enemEscolaService.buscaUfs()).thenThrow(new RuntimeException());
        when(enemEscolaService.buscaEscolaPorCodigoMunicipio(any())).thenThrow(new RuntimeException());

        String[] urls = {"/api/enem-escola/escola", "/api/enem-escola/municipio", "/api/enem-escola/ufs", "/api/enem-escola"};

        for (String url : urls) {
            MvcResult result = mockMvc.perform(get(url)
                    .content("{ \"nome\" : \"teste\" }")
                    .contentType("application/json")).andReturn();
            String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
            MensagemErro configResultado = objectMapper.readValue(json, new TypeReference<>() { });
            assertEquals(msgSaida, configResultado);
        }
    }
}

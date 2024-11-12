package br.usp.ime.projetoengsoft.controller.api;

import br.usp.ime.projetoengsoft.dto.UserDto;
import br.usp.ime.projetoengsoft.model.User;
import br.usp.ime.projetoengsoft.service.LoginService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@WebAppConfiguration
public class LoginApiTest {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private LoginService loginService;
    private ObjectMapper objectMapper = new ObjectMapper();
    //private ObjectWriter objectWriter = new ObjectWriter();

    @Test
    public void getUser() throws Exception{
        User toGet = User.builder()
                .user("teste@gmail.com")
                .pass("teste123")
                .nome("Teste")
                .sobrenome("da Silva")
                .escola("USP")
                .UF("SP")
                .dataNascimento(new GregorianCalendar(2024, 11, 10).getTime())
                .funcao("aluno")
                .build();

        User fromService = loginService.getUser(toGet.getUser());

        MvcResult result = mockMvc.perform(get("/api/accounts/get-user?user=teste@gmail.com").contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        UserDto fromMockDto = objectMapper.readValue(json, new TypeReference<>() { });
        User fromMock = new User(fromMockDto);

        Assertions.assertEquals(fromService.getNome(), fromMock.getNome());
        Assertions.assertEquals(fromService.getUser(), fromMock.getUser());
        Assertions.assertEquals(fromService.getPass(), fromMock.getPass());
    }

    @Test
    public void checkPass() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/accounts/check-pass?user=teste@gmail.com&pass=teste123")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Assertions.assertEquals("OK", response);

        mockMvc.perform(get("/api/accounts/check-pass?user=teste@gmail.com&pass=teste13")
                        .contentType("application/json"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void criaUser() throws Exception {
        User toCreate = User.builder()
                .user("mockCreate@gmail.com")
                .pass("teste123")
                .nome("MockCreate")
                .sobrenome("da Silva")
                .escola("USP")
                .UF("SP")
                .dataNascimento(new GregorianCalendar(2024, 11, 10).getTime())
                .funcao("aluno")
                .build();
        if (loginService.getUser(toCreate.getUser())  != null) loginService.deleteUser(toCreate);

        String json = new Gson().toJson(toCreate);

        mockMvc.perform(post("/api/accounts/create-user")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        User onDb = loginService.getUser(toCreate.getUser());
        Assertions.assertNotNull(onDb);
        Assertions.assertNotEquals(toCreate, onDb);
    }

    @Test
    public void updateUser() throws  Exception {
        User toUpdate = User.builder()
                .user("mockUpdate@gmail.com")
                .pass("teste123")
                .nome("MockUpdate")
                .sobrenome("da Silva")
                .escola("USP")
                .UF("SP")
                .dataNascimento(new GregorianCalendar(2024, 11, 10).getTime())
                .funcao("aluno")
                .build();
        User updated = User.builder()
                .user("mockUpdate@gmail.com")
                .pass("teste123")
                .nome("MockUpdated")
                .sobrenome("da Silva")
                .escola("USP")
                .UF("SP")
                .dataNascimento(new GregorianCalendar(2024, 11, 10).getTime())
                .funcao("aluno")
                .build();

        if (loginService.getUser(toUpdate.getUser()) == null)  loginService.criaUser(toUpdate);
        else loginService.updateUser(toUpdate);

        String json = new Gson().toJson(updated);
        mockMvc.perform(put("/api/accounts/update-user")
                    .contentType("application/json")
                    .content(json))
                .andExpect(status().isOk())
                .andReturn();

        User onDb = loginService.getUser(toUpdate.getUser());
        Assertions.assertNotNull(onDb);
        Assertions.assertEquals(toUpdate.getUser(), onDb.getUser());
        Assertions.assertNotEquals(toUpdate.getNome(), onDb.getNome());
        Assertions.assertEquals(updated.getNome(), onDb.getNome());
    }

    @Test
    public void deleteUser() throws Exception {
        User toDelete = User.builder()
                .user("mockDelete@gmail.com")
                .pass("teste123")
                .nome("MockDelete")
                .sobrenome("da Silva")
                .escola("USP")
                .UF("SP")
                .dataNascimento(new GregorianCalendar(2024, 11, 10).getTime())
                .funcao("aluno")
                .build();

        if (loginService.getUser(toDelete.getUser()) == null) loginService.criaUser(toDelete);

        mockMvc.perform(delete("/api/accounts/delete-user?user=mockDelete@gmail.com&pass=wrong")
                        .contentType("application/json"))
                .andExpect(status().isUnauthorized())
                .andReturn();

        mockMvc.perform(delete("/api/accounts/delete-user?user=mockDelete@gmail.com&pass=teste123")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        User onDb = loginService.getUser(toDelete.getUser());

        Assertions.assertNull(onDb);

    }
}

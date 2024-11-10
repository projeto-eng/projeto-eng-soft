package br.usp.ime.projetoengsoft.service;

import br.usp.ime.projetoengsoft.model.User;
import br.usp.ime.projetoengsoft.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Date;
import java.util.GregorianCalendar;

@SpringBootTest
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @SpyBean
    private UserRepository userRepository;

    @Test
    public void criaUserTeste() {
        User user = User.builder()
                .user("testService@teste.com")
                .pass("123")
                .nome("testeService")
                .sobrenome("da Silva")
                .dataNascimento(new Date())
                .escola("USP")
                .funcao("estudante")
                .build();
        String response = loginService.criaUser(user);
        Assertions.assertEquals("OK", response);
        User noBanco = loginService.getUser(user.getUser());
        Assertions.assertNotNull(noBanco);
        Assertions.assertEquals(user.getNome(), noBanco.getNome());
    }

    @Test
    public void updateUserTeste() {
        Date date = new GregorianCalendar(2024, 01, 01).getTime();

        User initialUser = User.builder()
                .user("testUpdate@teste.com")
                .pass("123")
                .nome("toUpdate")
                .sobrenome("da Silva")
                .dataNascimento(date)
                .escola("USP")
                .funcao("estudante")
                .build();
        User updateUser = User.builder()
                .user("testUpdate@teste.com")
                .pass("123")
                .nome("updated")
                .sobrenome("da Silva")
                .dataNascimento(date)
                .escola("USP")
                .funcao("estudante")
                .build();

        User initial = userRepository.findByUser(initialUser.getUser());
        if ( initial != null && initial.getNome().equals(initialUser.getNome()))
            userRepository.delete(initialUser);
        if ( initial != null && initial.getNome().equals(updateUser.getNome()))
            userRepository.delete(updateUser);

        loginService.criaUser(initialUser);
        String response = loginService.updateUser(updateUser);
        Assertions.assertEquals("OK", response);
        User noBanco = userRepository.findByUser(updateUser.getUser());
        Assertions.assertNotNull(noBanco);
        Assertions.assertNotEquals(initialUser.getNome(), noBanco.getNome());
        Assertions.assertEquals(updateUser.getNome(), noBanco.getNome());
    }

    @Test
    public void deletaUser() {
        Date date = new GregorianCalendar(2024, 01, 01).getTime();
        User toDeleteUser = User.builder()
                .user("testDelete@teste.com")
                .pass("123")
                .nome("toDelete")
                .sobrenome("da Silva")
                .dataNascimento(date)
                .escola("USP")
                .funcao("estudante")
                .build();

        if (userRepository.findByUser(toDeleteUser.getUser()) != null) userRepository.delete(toDeleteUser);

        userRepository.save(toDeleteUser);

        String response = loginService.deleteUser(toDeleteUser);

        Assertions.assertEquals("OK", response);

        User noBanco = userRepository.findByUser(toDeleteUser.getUser());
        Assertions.assertNull(noBanco);
    }
}

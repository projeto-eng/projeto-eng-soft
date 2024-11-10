package br.usp.ime.projetoengsoft.repository;


import br.usp.ime.projetoengsoft.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

@SpringBootTest
@WebAppConfiguration
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void buscaUsuario() {
        // user encontrado
        User user = userRepository.findByUser("teste@gmail.com");
        Assertions.assertEquals("Teste", user.getNome());
        // user não encontrado
        User user2 = userRepository.findByUser("errado@gmail.com");
        Assertions.assertNull(user2);
    }

    @Test
    public void criaUsuario() {
        User user = User.builder()
                .user("testeCria@teste.com")
                .pass("123")
                .nome("teste")
                .sobrenome("da Silva")
                .dataNascimento(new Date())
                .escola("USP")
                .funcao("estudante")
                .build();
        userRepository.save(user);
        User criado = userRepository.findByUser("testeCria@teste.com");
        Assertions.assertNotNull(criado);
        Assertions.assertEquals("teste", criado.getNome());


    }

}

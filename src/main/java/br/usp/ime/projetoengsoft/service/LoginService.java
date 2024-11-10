package br.usp.ime.projetoengsoft.service;

import br.usp.ime.projetoengsoft.model.User;
import br.usp.ime.projetoengsoft.repository.UserRepository;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public LoginService (UserRepository repository) {
        this.userRepository = repository;
    }

    public String criaUser (User user) {
        if (this.userRepository.findByUser(user.getUser()) != null) {
            return "USER_FOUND";
        }
        this.userRepository.save(user);
        if (this.userRepository.findByUser(user.getUser()) != null) {
            return "OK";
        }
        return "UNKWON_ERROR";
    }

    public User getUser(String userName) {
        return this.userRepository.findByUser(userName);
    }

    public String updateUser(User newUser) {
        User oldUser = this.userRepository.findByUser(newUser.getUser());
        if (oldUser == null) return "USR_NOT_FOUND";

        this.userRepository.delete(oldUser);
        oldUser = this.userRepository.findByUser(newUser.getUser());
        if (oldUser != null) return "DELETE_ERROR";

        this.userRepository.save(newUser);
        oldUser = this.userRepository.findByUser(newUser.getUser());
        if (oldUser == null || !oldUser.getNome().equals(newUser.getNome()))
            return "INSERTION_ERROR";

        return "OK";
    }

    public String deleteUser(User toDelete) {
        User oldUser = this.userRepository.findByUser(toDelete.getUser());
        if (oldUser == null) return "USR_NOT_FOUND";

        if (!oldUser.getNome().equals(toDelete.getNome()))
            return "NOT_SAME_USER";

        if (!oldUser.getPass().equals(toDelete.getPass()))
            return "WRONG_PASS";

        this.userRepository.delete(oldUser);

        User afterDelete = userRepository.findByUser(oldUser.getUser());
        if (afterDelete != null) return "UNKOWN_ERROR";
        return "OK";
    }
}

package br.usp.ime.projetoengsoft.repository;

import br.usp.ime.projetoengsoft.model.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUser(String user);



}

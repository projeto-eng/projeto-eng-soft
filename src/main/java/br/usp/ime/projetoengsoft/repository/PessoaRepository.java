package br.usp.ime.projetoengsoft.repository;

import java.util.List;

import br.usp.ime.projetoengsoft.model.Pessoa;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends MongoRepository<Pessoa, String> {
    List<Pessoa> findPessoaByFuncaoIs(String funcao);
}

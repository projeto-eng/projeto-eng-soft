package br.usp.ime.projetoengsoft.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoRepository extends MongoRepository<br.usp.ime.projetoengsoft.model.Configuracao, String> {
    br.usp.ime.projetoengsoft.model.Configuracao findByModo(String modo);
}

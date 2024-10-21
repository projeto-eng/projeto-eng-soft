package br.usp.ime.projetoengsoft.repository;

import br.usp.ime.projetoengsoft.model.Configuracao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoRepository extends MongoRepository<Configuracao, String> {
    Configuracao findByModo(String modo);
}

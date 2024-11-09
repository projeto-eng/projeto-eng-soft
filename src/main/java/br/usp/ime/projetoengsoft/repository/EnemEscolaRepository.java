package br.usp.ime.projetoengsoft.repository;

import java.util.List;

import br.usp.ime.projetoengsoft.model.EnemEscola;
import br.usp.ime.projetoengsoft.model.Escola;
import br.usp.ime.projetoengsoft.model.Municipio;
import br.usp.ime.projetoengsoft.model.Uf;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnemEscolaRepository extends MongoRepository<EnemEscola, String> {

    List<EnemEscola> findByCodigoEscola(Integer codigo);

    @Aggregation(pipeline = {
            "{ $match : { SG_UF_ESCOLA : ?0 }  }",
            "{ $group: { _id : $CO_MUNICIPIO_ESCOLA, nome: { $addToSet: $NO_MUNICIPIO_ESCOLA } } }",
    })
    List<Municipio> findAllMunicipiosBySiglaUf(String sigla);

    @Aggregation(pipeline = {
            "{ $group: { _id : $CO_UF_ESCOLA, sigla: { $addToSet: $SG_UF_ESCOLA } } }",
    })
    List<Uf> findAllUf();

    @Aggregation(pipeline = {
            "{ $match : { SG_UF_ESCOLA : ?0 }  }",
            "{ $group: { _id : $CO_ESCOLA_EDUCACENSO, nomes: { $addToSet: $NO_ESCOLA_EDUCACENSO } } }",
    })
    List<Escola> findAllEscolaBySiglaUf(String sigla);

    @Aggregation(pipeline = {
            "{ $match : { CO_MUNICIPIO_ESCOLA : ?0 }  }",
            "{ $unwind: $NO_ESCOLA_EDUCACENSO }",
            "{ $group: { _id : $CO_ESCOLA_EDUCACENSO, nomes: { $addToSet: $NO_ESCOLA_EDUCACENSO } } }",
    })
    List<Escola> findAllEscolaByCodigoMunicipio(Integer codigo);

    @Aggregation(pipeline = {
            "{ $match : { NO_ESCOLA_EDUCACENSO : { $regex: ?0, $options: 'i' } }  }",
            "{ $unwind: $NO_ESCOLA_EDUCACENSO }",
            "{ $group: { _id : $CO_ESCOLA_EDUCACENSO, nomes: { $addToSet: $NO_ESCOLA_EDUCACENSO } } }",
    })
    List<Escola> findAllEscolaByNomeContaining(String nome);
}
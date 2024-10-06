package br.usp.ime.projetoengsoft.migrate;

import br.usp.ime.projetoengsoft.model.Configuracao;
import br.usp.ime.projetoengsoft.repository.ConfiguracaoRepository;
import io.mongock.api.annotations.*;

@ChangeUnit(id="configuracoes-1", order = "001", author = "mongock")
public class DbMigration {
    @Execution
    public void execution(ConfiguracaoRepository configuracaoRepository) {
        Configuracao configuracao = Configuracao.builder()
                .host("http://localhost:8080")
                .modo("DESENVOLVIMENTO")
                .nomeApp("Projeto de Engenharia de Software")
                .descricao("Lorem ipsum dolor sit amet, consectetur adipisci elit, sed eiusmod tempor incidunt ut " +
                        "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrum exercitationem ullam " +
                        "corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur. Quis aute iure " +
                        "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur " +
                        "sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id " +
                        "est laborum.")
                .build();
        configuracaoRepository.save(configuracao);
    }

    @RollbackExecution
    public void rollbackExecutionMethodName() {
        System.out.println("rollback execution seeding database...");
    }
}

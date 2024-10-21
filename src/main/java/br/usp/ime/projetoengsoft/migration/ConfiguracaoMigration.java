package br.usp.ime.projetoengsoft.migration;

import br.usp.ime.projetoengsoft.model.Configuracao;
import br.usp.ime.projetoengsoft.repository.ConfiguracaoRepository;
import br.usp.ime.projetoengsoft.repository.PessoaRepository;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;

@ChangeUnit(id = "configuracoes-1", order = "002", author = "mongock")
public class ConfiguracaoMigration {
    @Execution
    public void execution(ConfiguracaoRepository configuracaoRepository, PessoaRepository pessoaRepository) {
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

        configuracao.setIntegrantes(pessoaRepository.findPessoaByFuncaoIs("Desenvolvedor Web"));
        configuracaoRepository.save(configuracao);
    }

    @RollbackExecution
    public void rollbackExecutionMethodName() {
        System.out.println("rollback execution seeding database...");
    }
}

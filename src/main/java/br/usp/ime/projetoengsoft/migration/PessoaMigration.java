package br.usp.ime.projetoengsoft.migration;

import java.util.List;

import br.usp.ime.projetoengsoft.model.Pessoa;
import br.usp.ime.projetoengsoft.repository.PessoaRepository;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;

@ChangeUnit(id = "pessoa-1", order = "001", author = "mongock")
public class PessoaMigration {
    @Execution
    public void execution(PessoaRepository repository) {
        Pessoa p1 = Pessoa.builder().nome("Gabriel Ferreira").funcao("Desenvolvedor Web").urlGithub("http://github.com").build();
        Pessoa p2 = Pessoa.builder().nome("Gilvane da Silva Sousa").funcao("Desenvolvedor Web").urlGithub("http://github.com").build();
        Pessoa p3 = Pessoa.builder().nome("João Petrosino").funcao("Desenvolvedor Web").urlGithub("http://github.com").build();
        Pessoa p4 = Pessoa.builder().nome("Lucas irineu").funcao("Desenvolvedor Web").urlGithub("http://github.com").build();
        Pessoa p5 = Pessoa.builder().nome("Marcelo Nascimento").funcao("Desenvolvedor Web").urlGithub("http://github.com").build();
        Pessoa p6 = Pessoa.builder().nome("Pedro Zamecki").funcao("Desenvolvedor Web").urlGithub("http://github.com").build();
        repository.saveAll(List.of(p1, p2, p3, p4, p5, p6));
    }

    @RollbackExecution
    public void rollbackExecutionMethodName() {
        System.out.println("rollback execution seeding database...");
    }
}

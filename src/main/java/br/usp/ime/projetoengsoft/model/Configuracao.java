package br.usp.ime.projetoengsoft.model;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import br.usp.ime.projetoengsoft.validation.ListaPessoaValida;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@Builder
@Document(collection = "configuracao")
public class Configuracao {
    @Id
    private String id;
    @NotEmpty
    @NotNull
    private String host;
    private String nomeApp;
    private String descricao;
    @Indexed(unique = true)
    private String modo;
    @DBRef
    @NotNull
    @NotEmpty
    @ListaPessoaValida
    private List<Pessoa> integrantes;
}

package br.usp.ime.projetoengsoft.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(collection = "accounts")
public class User {
    @Id
    private String id;
    @Field("user")
    private String user;
    @Field("pass")
    private String pass;
    @Field("nome")
    private String nome;
    @Field("sobrenome")
    private String sobrenome;
    @Field("nascimento")
    private Date dataNascimento;
    @Field("escola")
    private String escola;
    @Field("funcao")
    private String funcao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }
}

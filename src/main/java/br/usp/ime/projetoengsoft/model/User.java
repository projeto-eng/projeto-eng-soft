package br.usp.ime.projetoengsoft.model;

import br.usp.ime.projetoengsoft.dto.UserDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @Field("UF")
    private String UF;
    @Field("nascimento")
    private Date dataNascimento;
    @Field("escola")
    private String escola;
    @Field("funcao")
    private String funcao;

    public User(UserDto newUser) {
        this.user = newUser.getUser();
        this.pass = newUser.getPass();
        this.nome = newUser.getNome();
        this.sobrenome = newUser.getSobrenome();
        try {
            this.dataNascimento = new SimpleDateFormat("yyyy-MM-dd").parse(newUser.getDataNascimento());
        }
        catch (java.text.ParseException e){
            e.printStackTrace();
            this.dataNascimento = new GregorianCalendar(1990, Calendar.JANUARY, 1).getTime();
        }

        this.escola = newUser.getEscola();
        this.funcao = newUser.getFuncao();
    }
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

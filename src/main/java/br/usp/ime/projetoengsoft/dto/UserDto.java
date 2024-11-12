package br.usp.ime.projetoengsoft.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class UserDto {
    private String id;
    private String user;
    private String pass;
    private String nome;
    private String sobrenome;
    private String dataNascimento;
    private String escola;
    private String UF;
    private String funcao;

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getEscola() {
        return escola;
    }

    public String getFuncao() {
        return funcao;
    }
}

package br.usp.ime.projetoengsoft.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class UserDto {
    private String user;
    private String nome;
    private String sobrenome;

}

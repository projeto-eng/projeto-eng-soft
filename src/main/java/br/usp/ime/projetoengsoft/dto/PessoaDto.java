package br.usp.ime.projetoengsoft.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class PessoaDto {
    private String nome;
    private String funcao;
    private String urlGithub;
}

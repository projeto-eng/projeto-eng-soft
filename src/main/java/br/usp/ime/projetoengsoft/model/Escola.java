package br.usp.ime.projetoengsoft.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Escola {
    private Integer id;
    private String[] nomes;
}

package br.usp.ime.projetoengsoft.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@EqualsAndHashCode
public class EscolaReq {
    private String nome;
    private String siglaUf;
    private Integer codigoMunicipio;
}

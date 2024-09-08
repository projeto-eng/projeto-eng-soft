package br.usp.ime.projetoengsoft.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiDto {
    private String nomeProjeto;
    private String descricaoProjeto;

    public ApiDto(String nomeProjeto, String descricaoProjeto) {
        this.nomeProjeto = nomeProjeto;
        this.descricaoProjeto = descricaoProjeto;
    }
}

package br.usp.ime.projetoengsoft.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfiguracaoDto {
    private String host;
    private String nomeApp;
    private String descricao;
    private String modo;
}

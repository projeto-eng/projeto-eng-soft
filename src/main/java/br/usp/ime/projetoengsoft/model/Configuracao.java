package br.usp.ime.projetoengsoft.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "configuracao")
public class Configuracao {
    @Id
    private String id;
    private String host;
    private String nomeApp;
    private String descricao;
    @Indexed(unique = true)
    private String modo;
}

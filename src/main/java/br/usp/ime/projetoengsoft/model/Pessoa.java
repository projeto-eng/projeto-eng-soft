package br.usp.ime.projetoengsoft.model;

import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(collection = "pessoa")
public class Pessoa {
    @Id
    private String id;
    private String nome;
    private String funcao;
    @Pattern(regexp = "^|((https|http):\\/\\/.*)$")
    private String urlGithub;
}

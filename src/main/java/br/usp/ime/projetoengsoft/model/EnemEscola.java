package br.usp.ime.projetoengsoft.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "MICRODADOS_ENEM_ESCOLA")
public class EnemEscola {
    @Id
    private String id;
    @Field("NU_ANO")
    private Integer ano;
    @Field("CO_UF_ESCOLA")
    private String codigoUf;
    private String SG_UF_ESCOLA;
    @Field("CO_MUNICIPIO_ESCOLA")
    private Integer codigoMunicipio;
    @Field("NO_MUNICIPIO_ESCOLA")
    private String nomeMunicipio;
    @Field("CO_ESCOLA_EDUCACENSO")
    private Integer codigoEscola;
    private String NO_ESCOLA_EDUCACENSO;
    private String TP_DEPENDENCIA_ADM_ESCOLA;
    private String TP_LOCALIZACAO_ESCOLA;
    private Double NU_MATRICULAS;
    private Double NU_PARTICIPANTES_NEC_ESP;
    private Double NU_PARTICIPANTES;
    private Double NU_TAXA_PARTICIPACAO;
    private Double NU_MEDIA_CN;
    private Double NU_MEDIA_CH;
    private Double NU_MEDIA_LP;
    private Double NU_MEDIA_MT;
    private Double NU_MEDIA_RED;
    private Double NU_MEDIA_OBJ;
    private Double NU_MEDIA_TOT;
    private String INSE;
    private String PC_FORMACAO_DOCENTE;
    private Double NU_TAXA_PERMANENCIA;
    private Double NU_TAXA_APROVACAO;
}

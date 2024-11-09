package br.usp.ime.projetoengsoft.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@ToString
@EqualsAndHashCode
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
    private String NU_MATRICULAS;
    private String NU_PARTICIPANTES_NEC_ESP;
    private String NU_PARTICIPANTES;
    private String NU_TAXA_PARTICIPACAO;
    private String NU_MEDIA_CN;
    private String NU_MEDIA_CH;
    private String NU_MEDIA_LP;
    private String NU_MEDIA_MT;
    private String NU_MEDIA_RED;
    private String NU_MEDIA_OBJ;
    private String NU_MEDIA_TOT;
    private String INSE;
    private String PC_FORMACAO_DOCENTE;
    private String NU_TAXA_PERMANENCIA;
    private String NU_TAXA_APROVACAO;
}

package br.usp.ime.projetoengsoft.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MensagemErro {
    Integer status;
    String mensagem;
}

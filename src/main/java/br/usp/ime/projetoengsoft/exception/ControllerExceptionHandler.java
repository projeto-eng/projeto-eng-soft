package br.usp.ime.projetoengsoft.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = { ValidationException.class })
    public ResponseEntity<MensagemErro> validationException(ValidationException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        MensagemErro msg = new MensagemErro(status.value(), "Os dados enviados não são validos.");
        return new ResponseEntity<>(msg, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MensagemErro> globalExceptionHandler(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        MensagemErro msg = new MensagemErro(status.value(), "Erro ao processar a solicitação.");
        return new ResponseEntity<>(msg, status);
    }
}

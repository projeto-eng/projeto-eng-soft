package br.usp.ime.projetoengsoft.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ListaPessoaValidaImp.class)
@Documented
public @interface ListaPessoaValida {
    String message() default "Lista de pessoas não é válida.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

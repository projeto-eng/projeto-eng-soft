package br.usp.ime.projetoengsoft.validation;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import br.usp.ime.projetoengsoft.model.Pessoa;
import br.usp.ime.projetoengsoft.repository.PessoaRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class ListaPessoaValidaImp implements ConstraintValidator<ListaPessoaValida, List<Pessoa>> {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public boolean isValid(List<Pessoa> list, ConstraintValidatorContext context) {
        for (Pessoa pessoa : list) {
            if (pessoaRepository.findById(pessoa.getId()).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void initialize(ListaPessoaValida constraintAnnotation) {
    }
}

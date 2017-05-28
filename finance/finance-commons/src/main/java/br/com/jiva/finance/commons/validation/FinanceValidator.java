package br.com.jiva.finance.commons.validation;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class FinanceValidator {

    public static void validate(Object object) throws IllegalArgumentException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object>> errors = validator.validate(object);
        if (errors.size() > 0) {
            throw new IllegalArgumentException("invalid arguments");
        }
    }

}

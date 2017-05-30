package br.com.jiva.finance.model;

import br.com.jiva.finance.model.enuns.RegisterType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static br.com.jiva.finance.commons.validation.FinanceValidator.validate;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Register {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @NotNull
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RegisterType type;

    @NotNull
    @DecimalMin("0.01")
    private Double value;

    public static Register of(Long id, String description, RegisterType type, Double value) {
        Register register = new Register(id, description, type, value);
        validate(register);
        return register;
    }

    public static Register of(String description, RegisterType type, Double value) {
        Register register = new Register(description, type, value);
        validate(register);
        return register;
    }

    private Register(Long id, String description, RegisterType type, Double value) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.value = value;
    }

    private Register(String description, RegisterType type, Double value) {
        this.description = description;
        this.type = type;
        this.value = value;
    }
}

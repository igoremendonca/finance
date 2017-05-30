package br.com.jiva.finance.web.controller.to;

import br.com.jiva.finance.web.controller.to.enuns.RegisterTypeTO;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@EqualsAndHashCode
public class RegisterTO {

    private Long id;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    private RegisterTypeTO type;

    @NotNull
    @DecimalMin("0.01")
    private Double value;

    public void setId(Long id) {
        this.id = id;
    }
}

package br.com.jiva.finance.web.util;


import br.com.jiva.finance.model.Register;
import br.com.jiva.finance.model.enuns.RegisterType;
import br.com.jiva.finance.web.controller.to.RegisterTO;
import br.com.jiva.finance.web.controller.to.enuns.RegisterTypeTO;

import java.util.List;
import java.util.stream.Collectors;

public class RegisterConverter {

    public static Register fromRegisterTO(RegisterTO registerTO) {
        if (registerTO == null) {
            return null;
        }
        return Register.of(registerTO.getId(),
                registerTO.getDescription(),
                (registerTO.getType() != null ? RegisterType.valueOf(registerTO.getType().name()) : null),
                registerTO.getValue());
    }

    public static RegisterTO fromRegister(Register register) {
        if (register == null) {
            return null;
        }
        return RegisterTO.builder()
                .id(register.getId())
                .description(register.getDescription())
                .type(RegisterTypeTO.valueOf(register.getType().name()))
                .value(register.getValue())
                .build();
    }

    public static List<RegisterTO> fromRegisterList(List<Register> registers) {
        if (registers == null) {
            return null;
        }
        return registers.stream().map(RegisterConverter::fromRegister).collect(Collectors.toList());
    }

}

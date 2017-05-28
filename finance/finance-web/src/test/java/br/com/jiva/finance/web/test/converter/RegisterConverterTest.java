package br.com.jiva.finance.web.test.converter;

import br.com.jiva.finance.model.Register;
import br.com.jiva.finance.web.controller.to.RegisterTO;
import br.com.jiva.finance.web.controller.to.enuns.RegisterTypeTO;
import br.com.jiva.finance.web.util.RegisterConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static br.com.jiva.finance.model.enuns.RegisterType.EXPENSE;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class RegisterConverterTest {

    private final Long id = 1L;
    private final String description = "description";
    private Double value = 100D;

    private Register register;
    private RegisterTO registerTO;

    @Before
    public void setup () {
        register = Register.of(id, description, EXPENSE, value);
        registerTO = RegisterTO.builder()
                .id(id)
                .description(description)
                .type(RegisterTypeTO.EXPENSE)
                .value(value)
                .build();
    }

    @Test
    public void should_convert_registerTO_to_register() {
        Register result = RegisterConverter.fromRegisterTO(registerTO);
        assertEquals(result, register);
    }

    @Test
    public void should_convert_registerTO_to_null() {
        assertNull(RegisterConverter.fromRegisterTO(null));
    }

    @Test
    public void should_not_convert_registerTO_to_register() {
        String otherDescription = "otherDescription";
        Double otherValue = 150D;

        registerTO = RegisterTO.builder()
                .id(id)
                .description(otherDescription)
                .type(RegisterTypeTO.EXPENSE)
                .value(otherValue)
                .build();

        Register result = RegisterConverter.fromRegisterTO(registerTO);
        assertNotEquals(result, register);
    }

    @Test
    public void should_convert_register_to_registerTO() {
        RegisterTO result = RegisterConverter.fromRegister(register);
        assertEquals(result, registerTO);
    }

    @Test
    public void should_convert_register_to_null() {
        assertNull(RegisterConverter.fromRegister(null));
    }

    @Test
    public void should_not_convert_register_to_registerTO() {
        String otherDescription = "otherDescription";
        Double otherValue = 150D;

        register = Register.of(id, otherDescription, EXPENSE, otherValue);
        RegisterTO result = RegisterConverter.fromRegister(register);
        assertNotEquals(result, registerTO);
    }

}

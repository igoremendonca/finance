package br.com.jiva.finance.repository.test;

import br.com.jiva.finance.model.Register;
import br.com.jiva.finance.repository.RegisterRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.jiva.finance.model.enuns.RegisterType.EXPENSE;
import static br.com.jiva.finance.model.enuns.RegisterType.RECIPE;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FinanceRepositoryTest.class)
@Transactional
public class RegisterRepositoryTest {

    @Autowired
    private RegisterRepository registerRepository;

    private Register register;
    String description = "Register 1";
    Double value = 100D;

    @Before
    public void setup() {
        register = Register.of(description, EXPENSE, value);
    }

    @Test
    public void should_save_register() {
        register = registerRepository.save(register);
        assertNotNull(register.getId());
    }

    @Test
    public void should_get_register() {
        register = registerRepository.save(register);
        assertNotNull(register.getId());

        Register registerFound = findRegister(register.getId());
        assertEquals(register, registerFound);
    }

    @Test
    public void should_update_register() {
        register = registerRepository.save(register);
        assertNotNull(register.getId());

        String newRegisterDescription = "New Description Register";
        Register newRegister = Register.of(register.getId(), newRegisterDescription, EXPENSE, register.getValue());
        registerRepository.save(newRegister);

        Register registerFound = findRegister(newRegister.getId());
        assertEquals(registerFound.getId(), newRegister.getId());
        assertEquals(newRegisterDescription, newRegister.getDescription());
    }

    @Test
    public void should_delete_register() {
        register = registerRepository.save(register);
        assertNotNull(register.getId());

        registerRepository.delete(register);
        Register registerFound = findRegister(register.getId());
        assertNull(registerFound);
    }

    @Test
    public void should_find_register_by_type() {
        register = registerRepository.save(register);
        assertNotNull(register.getId());

        List<Register> registerFound = registerRepository.findByType(EXPENSE);
        assertNotNull(registerFound);
        assertEquals(registerFound.size(), 1);
        assertEquals(register, registerFound.get(0));
    }

    @Test
    public void should_find_value_register_by_type() {
        for (int i = 0; i < 3; i++) {
            registerRepository.save(getNewRegister());
        }
        Double valueByType = registerRepository.findSumValueByType(EXPENSE);
        assertNotNull(valueByType);
        assertEquals(valueByType, new Double(3 * value));
    }

    private Register findRegister(Long id) {
        return registerRepository.findOne(id);
    }

    public Register getNewRegister() {
        return Register.of(description, EXPENSE, value);
    }
}

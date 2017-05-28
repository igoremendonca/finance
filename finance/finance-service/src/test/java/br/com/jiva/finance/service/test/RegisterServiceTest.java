package br.com.jiva.finance.service.test;

import br.com.jiva.finance.commons.exception.FinanceException;
import br.com.jiva.finance.commons.utils.JsonConverter;
import br.com.jiva.finance.model.Register;
import br.com.jiva.finance.repository.RegisterRepository;
import br.com.jiva.finance.service.RegisterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static br.com.jiva.finance.model.enuns.RegisterType.EXPENSE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest (classes = FinanceServiceTestConfig.class)
public class RegisterServiceTest {

    @Autowired
    private RegisterService registerService;

    @MockBean
    private RegisterRepository registerRepository;

    Register registerParameter;
    Register registerResult;

    @Before
    public void setup() {
        Long id = 1L;
        Double value = 100D;
        String description = "Register 1";

        registerParameter = Register.of(description, EXPENSE, value);
        registerResult = Register.of(id, description, EXPENSE, value);
    }

    @Test
    public void should_create_register() throws FinanceException {
        when(registerRepository.save(registerParameter)).thenReturn(registerResult);

        Register result = registerService.create(registerParameter);
        assertNotNull(result);
        assertNotNull(result.getId());
    }

    @Test (expected = FinanceException.class)
    public void should_not_create_register_when_invalid_arguments() throws FinanceException {
        Register register = JsonConverter.fromJson("{\"id\":1,\"type\":\"EXPENSE\",\"value\":100.0}", Register.class);
        registerService.create(register);
    }

    @Test
    public void should_get_register_by_id() {
        when(registerRepository.findOne(registerResult.getId())).thenReturn(registerResult);

        Register result = registerService.find(registerResult.getId());
        assertNotNull(result);
        assertEquals(registerResult, result);
    }

    @Test (expected = FinanceException.class)
    public void should_not_get_register_by_id_when_register_not_exists() {
        registerService.find(0L);
    }

    @Test
    public void should_update_register() {
        String newDescription = "New Description";
        Register registerUpdated = Register.of(registerResult.getId(), newDescription, EXPENSE, 200D);
        when(registerRepository.findOne(registerResult.getId())).thenReturn(registerResult);
        when(registerRepository.save(registerUpdated)).thenReturn(registerUpdated);

        registerUpdated = registerService.update(registerResult.getId(), registerUpdated);
        assertNotNull(registerUpdated);
        assertEquals(registerUpdated.getId(), registerResult.getId());
        assertEquals(registerUpdated.getDescription(), newDescription);
    }

    @Test (expected = FinanceException.class)
    public void should_not_update_register_when_register_with_description_null() {
        Register registerUpdated = JsonConverter.fromJson("{\"id\":1,\"type\":\"EXPENSE\",\"value\":100.0}", Register.class);
        registerService.update(registerUpdated.getId(), registerUpdated);
    }

    @Test
    public void should_delete_register() {
        when(registerRepository.findOne(registerResult.getId())).thenReturn(registerResult);
        registerService.delete(registerResult.getId());
    }

}

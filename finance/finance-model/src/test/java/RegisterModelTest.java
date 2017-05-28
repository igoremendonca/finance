import br.com.jiva.finance.model.Register;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static br.com.jiva.finance.model.enuns.RegisterType.EXPENSE;

@RunWith(JUnit4.class)
public class RegisterModelTest {

    @Test
    public void should_create_register() {
        String description = "Register 1";
        Double value = 100D;

        Register.of(description, EXPENSE, value);
    }

    @Test (expected = IllegalArgumentException.class)
    public void should_not_create_register_invalid_description() throws IllegalArgumentException {
        Double value = 100D;
        Register.of(null, EXPENSE, value);
    }

    @Test (expected = IllegalArgumentException.class)
    public void should_not_create_register_invalid_value() throws IllegalArgumentException {
        String description = "Register 1";
        Register.of(description, EXPENSE, null);
    }
}

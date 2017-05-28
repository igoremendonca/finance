package br.com.jiva.finance.web.test.integrated;

import br.com.jiva.finance.commons.exception.FinanceException;
import br.com.jiva.finance.commons.utils.JsonConverter;
import br.com.jiva.finance.model.Register;
import br.com.jiva.finance.model.enuns.RegisterType;
import br.com.jiva.finance.web.controller.to.RegisterTO;
import br.com.jiva.finance.web.controller.to.enuns.RegisterTypeTO;
import br.com.jiva.finance.web.test.FinanceControllerTestConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static br.com.jiva.finance.commons.enuns.ExceptionCode.NOT_FOUND_OBJECT;
import static br.com.jiva.finance.commons.utils.JsonConverter.toJson;
import static br.com.jiva.finance.web.util.RegisterConverter.fromRegisterTO;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinanceControllerTestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisterControllerIT {

    @LocalServerPort
    private int port;

    final Long id = 1L;
    final String description = "description";
    Double value = 100D;
    private Register register = Register.of(id, description, RegisterType.EXPENSE, value);

    final private String PATH = "/register";

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void should_create_register() throws Exception {
        RegisterTO registerTO = getNewRegisterTO();

        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(toJson(registerTO))
                .post(PATH)
                .then()
                .statusCode(201)
                .body("id", notNullValue());
    }

    @Test
    public void should_not_create_register_when_register_is_invalid() throws Exception {
        RegisterTO registerTO = getNewInvalidRegisterTO();

        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(toJson(registerTO))
                .post(PATH)
                .then()
                .statusCode(400);
    }

    @Test
    public void should_update_register() throws Exception {
        RegisterTO registerTO = getNewRegisterTO();
        Integer registerId = createRegister(registerTO);

        RegisterTO registerUpdate = getRegisterToForUpdate(registerTO);

        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(toJson(registerUpdate))
                .put(String.format("%s/%s", PATH, registerId))
                .then()
                .statusCode(200)
                .body("id", equalTo(registerId),
                        "description", equalTo(registerUpdate.getDescription()));
    }

    @Test
    public void should_find_register() throws Exception {
        RegisterTO registerTO = getNewRegisterTO();
        Integer registerId = createRegister(registerTO);

        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .get(String.format("%s/%s", PATH, registerId))
                .then()
                .statusCode(200)
                .body("id", equalTo(registerId));
    }

    @Test
    public void should_not_find_register_when_register_not_exists() throws Exception {
        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .get(String.format("%s/%s", PATH, 300))
                .then()
                .statusCode(404);
    }

    @Test
    public void should_delete_register() throws Exception {
        RegisterTO registerTO = getNewRegisterTO();
        Integer registerId = createRegister(registerTO);

        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .delete(String.format("%s/%s", PATH, registerId))
                .then()
                .statusCode(202);
    }

    private RegisterTO getRegisterToForUpdate(RegisterTO registerTO) {
        String newDescription = "New Description for register";
        Double newValue = 350D;
        return RegisterTO.builder()
                .description(newDescription)
                .type(RegisterTypeTO.EXPENSE)
                .value(newValue)
                .build();
    }

    private Integer createRegister(RegisterTO registerTO) {
        return given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(toJson(registerTO))
                .post(PATH)
                .then()
                .statusCode(201)
                .extract()
                .path("id");
    }

    private RegisterTO getNewRegisterTO() {
        return RegisterTO.builder()
                .description(description)
                .type(RegisterTypeTO.EXPENSE)
                .value(value)
                .build();
    }

    private RegisterTO getNewInvalidRegisterTO() {
        return RegisterTO.builder()
                .type(RegisterTypeTO.EXPENSE)
                .value(value)
                .build();
    }

    private RegisterTO getRegisterTO() {
        return RegisterTO.builder()
                .id(id)
                .description(description)
                .type(RegisterTypeTO.EXPENSE)
                .value(value)
                .build();
    }


}

package br.com.jiva.finance.web.test.controller;


import br.com.jiva.finance.commons.exception.FinanceException;
import br.com.jiva.finance.commons.utils.JsonConverter;
import br.com.jiva.finance.model.Register;
import br.com.jiva.finance.model.enuns.RegisterType;
import br.com.jiva.finance.service.RegisterService;
import br.com.jiva.finance.web.controller.RegisterController;
import br.com.jiva.finance.web.controller.to.RegisterTO;
import br.com.jiva.finance.web.controller.to.enuns.RegisterTypeTO;
import br.com.jiva.finance.web.test.FinanceControllerTestConfig;
import br.com.jiva.finance.web.test.util.ExceptionTestHandlerExceptionResolver;
import br.com.jiva.finance.web.util.RegisterConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static br.com.jiva.finance.commons.enuns.ExceptionCode.NOT_FOUND_OBJECT;
import static br.com.jiva.finance.commons.utils.JsonConverter.toJson;
import static br.com.jiva.finance.web.util.RegisterConverter.fromRegisterTO;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinanceControllerTestConfig.class)
public class RegisterControllerTest {

    @Autowired
    private RegisterController registerController;

    @MockBean
    private RegisterService registerService;

    private MockMvc mockMvc;

    final Long id = 1L;
    final String description = "description";
    Double value = 100D;
    private Register register = Register.of(id, description, RegisterType.EXPENSE, value);
    private RegisterTO registerTO;

    final private String PATH = "/register";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(registerController)
                .setHandlerExceptionResolvers(ExceptionTestHandlerExceptionResolver.create())
                .build();
    }

    @Test
    public void should_create_register() throws Exception {
        RegisterTO registerTO = getNewRegisterTO();
        when(registerService.create(fromRegisterTO(registerTO))).thenReturn(register);

        mockMvc.perform(post(PATH)
                .contentType(APPLICATION_JSON)
                .content(toJson(registerTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_not_create_register_when_register_is_invalid() throws Exception {
        RegisterTO registerTO = getNewInvalidRegisterTO();

        mockMvc.perform(post(PATH)
                .contentType(APPLICATION_JSON)
                .content(toJson(registerTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_update_register() throws Exception {
        RegisterTO registerTO = getRegisterTO();
        when(registerService.create(fromRegisterTO(registerTO))).thenReturn(register);

        mockMvc.perform(put(String.format("%s/%d",PATH,registerTO.getId()))
                .contentType(APPLICATION_JSON)
                .content(toJson(registerTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void should_not_update_register_when_register_is_invalid() throws Exception {
        RegisterTO registerTO = getInvalidRegisterTO();

        mockMvc.perform(put(String.format("%s/%d",PATH,registerTO.getId()))
                .contentType(APPLICATION_JSON)
                .content(toJson(registerTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_find_register() throws Exception {
        RegisterTO registerTO = getRegisterTO();
        when(registerService.find(registerTO.getId())).thenReturn(register);

        mockMvc.perform(get(String.format("%s/%d",PATH,registerTO.getId())))
                .andExpect(status().isOk());
    }

    @Test
    public void should_not_find_register_when_register_not_exists() throws Exception {
        RegisterTO registerTO = getRegisterTO();
        when(registerService.find(registerTO.getId())).thenThrow(new FinanceException(NOT_FOUND_OBJECT, "Register Not Found"));

        mockMvc.perform(get(String.format("%s/%d",PATH,registerTO.getId())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_delete_register() throws Exception {
        RegisterTO registerTO = getRegisterTO();
        mockMvc.perform(delete(String.format("%s/%d",PATH,registerTO.getId())))
                .andExpect(status().isAccepted());
    }

    @Test
    public void should_not_delete_register_when_register_not_exists() throws Exception {
        RegisterTO registerTO = getRegisterTO();
        doThrow(new FinanceException(NOT_FOUND_OBJECT, "Register Not Found")).when(registerService).delete(registerTO.getId());

        mockMvc.perform(delete(String.format("%s/%d",PATH,registerTO.getId())))
                .andExpect(status().isNotFound());
    }

    private RegisterTO getInvalidRegisterTO() {
        return RegisterTO.builder()
                .id(id)
                .type(RegisterTypeTO.EXPENSE)
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
                .build();
    }

}

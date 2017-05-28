package br.com.jiva.finance.web.controller;

import br.com.jiva.finance.service.RegisterService;
import br.com.jiva.finance.web.controller.to.RegisterTO;
import br.com.jiva.finance.web.util.RegisterConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static br.com.jiva.finance.web.util.RegisterConverter.fromRegister;
import static br.com.jiva.finance.web.util.RegisterConverter.fromRegisterList;
import static br.com.jiva.finance.web.util.RegisterConverter.fromRegisterTO;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @RequestMapping(value = {"/",""}, method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = CREATED)
    public RegisterTO create(@Valid @RequestBody RegisterTO registerTO) {
        return fromRegister(registerService.create(fromRegisterTO(registerTO)));
    }

    @RequestMapping(value = {"/",""}, method = RequestMethod.GET)
    @ResponseBody
    public List<RegisterTO> findAll() {
        return fromRegisterList(registerService.findAll());
    }

    @RequestMapping(value = {"/{registerId}"}, method = RequestMethod.GET)
    @ResponseBody
    public RegisterTO find(@PathVariable("registerId") Long registerId) {
        return fromRegister(registerService.find(registerId));
    }

    @RequestMapping(value = {"/{registerId}"}, method = RequestMethod.PUT)
    @ResponseBody
    public RegisterTO update(@PathVariable("registerId") Long registerId, @Valid @RequestBody RegisterTO registerTO) {
        registerTO.setId(registerId);
        return fromRegister(registerService.update(registerId, fromRegisterTO(registerTO)));
    }

    @RequestMapping(value = {"/{registerId}"}, method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(value = ACCEPTED)
    public void delete(@PathVariable("registerId") Long registerId) {
        registerService.delete(registerId);
    }

}

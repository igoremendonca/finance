package br.com.jiva.finance.web.controller;

import br.com.jiva.finance.model.enuns.RegisterType;
import br.com.jiva.finance.service.RegisterService;
import br.com.jiva.finance.web.controller.to.GraphTO;
import br.com.jiva.finance.web.controller.to.RegisterTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static br.com.jiva.finance.web.util.RegisterConverter.*;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @RequestMapping(value = {"/",""}, method = RequestMethod.POST)
    public ResponseEntity<RegisterTO> create(@Valid @RequestBody RegisterTO registerTO) {
        RegisterTO result = fromRegister(registerService.create(fromRegisterTO(registerTO)));
        return new ResponseEntity<RegisterTO>(result, CREATED);
    }

    @RequestMapping(value = {"/",""}, method = RequestMethod.GET)
    public ResponseEntity<List<RegisterTO>> findAll() {
        List<RegisterTO> results = fromRegisterList(registerService.findAll());
        return new ResponseEntity<List<RegisterTO>>(results, OK);
    }

    @RequestMapping(value = {"/{registerId}"}, method = RequestMethod.GET)
    public ResponseEntity<RegisterTO> find(@PathVariable("registerId") Long registerId) {
        RegisterTO result = fromRegister(registerService.find(registerId));
        return new ResponseEntity<RegisterTO>(result, OK);
    }

    @RequestMapping(value = {"/{registerId}"}, method = RequestMethod.PUT)
    public ResponseEntity<RegisterTO> update(@PathVariable("registerId") Long registerId, @Valid @RequestBody RegisterTO registerTO) {
        registerTO.setId(registerId);
        RegisterTO result = fromRegister(registerService.update(registerId, fromRegisterTO(registerTO)));
        return new ResponseEntity<RegisterTO>(result, OK);
    }

    @RequestMapping(value = {"/{registerId}"}, method = RequestMethod.DELETE)
    @ResponseStatus(value = ACCEPTED)
    public void delete(@PathVariable("registerId") Long registerId) {
        registerService.delete(registerId);
    }

    @RequestMapping(value = {"/graph"}, method = RequestMethod.GET)
    public ResponseEntity<List<GraphTO>> getGraphInformation() {
        List<GraphTO> result = new ArrayList<>();
        for (RegisterType type : RegisterType.values())
          result.add(GraphTO.builder()
                      .label(type.name())
                      .value(registerService.getValueByType(type))
                      .build());

        return new ResponseEntity<List<GraphTO>>(result, OK);
    }
}

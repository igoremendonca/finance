package br.com.jiva.finance.service.impl;


import br.com.jiva.finance.commons.enuns.ExceptionCode;
import br.com.jiva.finance.commons.exception.FinanceException;
import br.com.jiva.finance.model.Register;
import br.com.jiva.finance.repository.RegisterRepository;
import br.com.jiva.finance.service.RegisterService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.jiva.finance.commons.enuns.ExceptionCode.NOT_FOUND_OBJECT;
import static br.com.jiva.finance.commons.validation.FinanceValidator.validate;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    private RegisterRepository registerRepository;

    @Autowired
    public RegisterServiceImpl(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    public Register create(Register register) throws FinanceException {
        log.debug("Creating register {}", register);
        verifying(register);
        return registerRepository.save(register);
    }

    public Register find(Long registerId) {
        return getRegister(registerId);
    }

    private Register getRegister(Long registerId) {
        log.debug("Finding register by id {}", registerId);
        Register register = registerRepository.findOne(registerId);
        if (register == null) {
            throw new FinanceException(NOT_FOUND_OBJECT, "Register Not Found");
        }
        return register;
    }

    public Register update(Long registerId, Register register) {
        verifying(register);
        log.debug("Updating register {}", register);
        getRegister(registerId);
        return registerRepository.save(register);
    }

    public void delete(Long registerId) {
        log.debug("Deleting register by id {}", registerId);
        getRegister(registerId);
        registerRepository.delete(registerId);
    }

    public List<Register> findAll() {
        log.debug("Finding all registers");
        return (List<Register>) registerRepository.findAll();
    }

    private void verifying(Register register) throws FinanceException {
        try {
            log.debug("Valid register {}", register);
            validate(register);
        } catch (IllegalArgumentException e) {
            throw new FinanceException(ExceptionCode.INVALID_OBJECT, "Invalid Register");
        }
    }
}

package br.com.jiva.finance.service;


import br.com.jiva.finance.model.Register;
import br.com.jiva.finance.model.enuns.RegisterType;

import java.util.List;

public interface RegisterService {

    Register create(Register register);
    Register find(Long registerId);
    Register update(Long registerId, Register register);
    void delete(Long registerId);
    List<Register> findAll();
    Double getValueByType(RegisterType type);
}

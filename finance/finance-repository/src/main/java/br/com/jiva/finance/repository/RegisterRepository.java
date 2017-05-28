package br.com.jiva.finance.repository;


import br.com.jiva.finance.model.Register;
import br.com.jiva.finance.model.enuns.RegisterType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisterRepository extends CrudRepository<Register, Long> {

    List<Register> findByType(RegisterType registerType);
}

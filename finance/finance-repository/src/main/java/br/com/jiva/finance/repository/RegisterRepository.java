package br.com.jiva.finance.repository;


import br.com.jiva.finance.model.Register;
import br.com.jiva.finance.model.enuns.RegisterType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisterRepository extends CrudRepository<Register, Long> {

    List<Register> findByType(RegisterType registerType);

    @Query("SELECT sum(r.value) From Register r Where r.type=:type")
    Double findSumValueByType(@Param("type") RegisterType type);
}

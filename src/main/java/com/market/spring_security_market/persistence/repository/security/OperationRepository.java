package com.market.spring_security_market.persistence.repository.security;

import com.market.spring_security_market.persistence.entity.security.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation,Long> {

    @Query("SELECT o FROM Operation o where o.permitAll = true")//obtenemos una lista de operaciones cuando el atributo permitAll sea true
    List<Operation> findByPublicAcces();
}

package com.market.spring_security_market.persistence.repository;

import com.market.spring_security_market.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}

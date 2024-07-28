package com.market.spring_security_market.persistence.repository;

import com.market.spring_security_market.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

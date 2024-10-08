package com.market.spring_security_market.persistence.repository.security;

import com.market.spring_security_market.persistence.entity.security.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
    Optional<JwtToken> findByToken(String jwt);
}

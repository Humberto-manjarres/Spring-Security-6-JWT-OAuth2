package com.market.spring_security_market.service;

import com.market.spring_security_market.dto.SaveUser;
import com.market.spring_security_market.persistence.entity.security.User;

import java.util.Optional;

public interface UserService {
    User registerOneCustomer(SaveUser newUser);
    Optional<User> findOneByUsername(String username);
}

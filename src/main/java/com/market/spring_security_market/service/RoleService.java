package com.market.spring_security_market.service;

import com.market.spring_security_market.persistence.entity.security.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findDefaultRole();
}

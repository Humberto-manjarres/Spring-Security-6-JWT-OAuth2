package com.market.spring_security_market.service.impl;

import com.market.spring_security_market.persistence.entity.security.Role;
import com.market.spring_security_market.persistence.repository.security.RoleRepository;
import com.market.spring_security_market.service.RoleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Value("${security.default.role}")
    private String defaultRole;

    @Override
    public Optional<Role> findDefaultRole() {
        return roleRepository.findByName(defaultRole);
    }
}

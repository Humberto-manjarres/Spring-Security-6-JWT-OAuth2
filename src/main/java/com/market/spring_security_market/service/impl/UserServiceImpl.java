package com.market.spring_security_market.service.impl;

import com.market.spring_security_market.dto.SaveUser;
import com.market.spring_security_market.exception.InvalidPasswordException;
import com.market.spring_security_market.exception.ObjectNotFoundException;
import com.market.spring_security_market.persistence.entity.security.Role;
import com.market.spring_security_market.persistence.entity.security.User;
import com.market.spring_security_market.persistence.repository.security.UserRepository;
import com.market.spring_security_market.service.RoleService;
import com.market.spring_security_market.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerOneCustomer(SaveUser newUser) {
        this.validatePassword(newUser);
        User user = new User();
        user.setName(newUser.getName());
        user.setUsername(newUser.getUsername());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        Role defaultRole = roleService.findDefaultRole().orElseThrow(() -> new ObjectNotFoundException("Role Not found. Default Role"));
        user.setRole(defaultRole);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private void validatePassword(SaveUser newUser) {

        //valida si el password no tienen texto
        if (!StringUtils.hasText(newUser.getPassword()) || !StringUtils.hasText(newUser.getRepeatedPassword())){
            throw new InvalidPasswordException("Password don't match");
        }

        if (!newUser.getPassword().equals(newUser.getRepeatedPassword())){
            throw new InvalidPasswordException("Password don't match");
        }
    }

}

package com.market.spring_security_market.controller;

import com.market.spring_security_market.dto.RegisteredUser;
import com.market.spring_security_market.dto.SaveUser;
import com.market.spring_security_market.persistence.entity.security.User;
import com.market.spring_security_market.service.auth.AuthenticateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    /***Controlador para registrar clientes*/

    private final AuthenticateService authenticateService;

    public CustomerController(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    //@PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<RegisteredUser> registerOne(@RequestBody @Valid SaveUser newUser){
        RegisteredUser registeredUser = authenticateService.registerOneCustomer(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    //@PreAuthorize("denyAll()")//nadie tiene acceso a este endpoint
    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(Arrays.asList());
    }
}

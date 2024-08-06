package com.market.spring_security_market.controller;

import com.market.spring_security_market.dto.auth.AuthenticationRequest;
import com.market.spring_security_market.dto.auth.AuthenticationResponse;
import com.market.spring_security_market.service.auth.AuthenticateService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticateService authenticateService;

    public AuthenticationController(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest){
        AuthenticationResponse rsp = authenticateService.login(authenticationRequest);
        return ResponseEntity.ok(rsp);
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt){
        boolean isTokenValid = authenticateService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }
}

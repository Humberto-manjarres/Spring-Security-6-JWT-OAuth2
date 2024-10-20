package com.market.spring_security_market.controller;

import com.market.spring_security_market.dto.LogoutResponse;
import com.market.spring_security_market.dto.auth.AuthenticationRequest;
import com.market.spring_security_market.dto.auth.AuthenticationResponse;
import com.market.spring_security_market.persistence.entity.security.User;
import com.market.spring_security_market.service.auth.AuthenticateService;
import jakarta.servlet.http.HttpServletRequest;
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

    //@PreAuthorize("permitAll()")
    //@CrossOrigin
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

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(HttpServletRequest request){
        authenticateService.logout(request);
        return ResponseEntity.ok(new LogoutResponse("Logout success"));
    }

    //@PreAuthorize("hasAnyRole('ADMINISTRATOR','ASSISTANT_ADMINISTRATOR','CUSTOMER')")
    @GetMapping("/profile")
    public ResponseEntity<User> findMyProfile(){
        User user = authenticateService.findLoggedInUser();
        return ResponseEntity.ok(user);
    }
}

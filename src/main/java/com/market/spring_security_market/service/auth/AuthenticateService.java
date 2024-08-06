package com.market.spring_security_market.service.auth;

import com.market.spring_security_market.dto.RegisteredUser;
import com.market.spring_security_market.dto.SaveUser;
import com.market.spring_security_market.dto.auth.AuthenticationRequest;
import com.market.spring_security_market.dto.auth.AuthenticationResponse;
import com.market.spring_security_market.exception.ObjectNotFoundException;
import com.market.spring_security_market.persistence.entity.User;
import com.market.spring_security_market.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticateService {

    private final UserService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticateService(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public RegisteredUser registerOneCustomer(SaveUser newUser) {

        User user = userService.registerOneCustomer(newUser);

        RegisteredUser userDto = new RegisteredUser();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(userDto.getUsername());
        userDto.setRole(user.getRole().name());

        String jwt = jwtService.generateToken(user,generateExtraClaims(user));
        userDto.setJwt(jwt);

        return userDto;
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name",user.getName());
        extraClaims.put("role",user.getRole().name());
        extraClaims.put("authorities",user.getAuthorities());
        return extraClaims;
    }

    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword());
        this.authenticationManager.authenticate(authentication);//verificar que el usuario sea quien dice ser con el username y password.
        UserDetails user = userService.findOneByUsername(authRequest.getUsername()).get();//buscamos en BD por username.
        String jwt = jwtService.generateToken(user, generateExtraClaims((User) user));//generación del token.
        AuthenticationResponse authRsp = new AuthenticationResponse();
        authRsp.setJwt(jwt);
        return authRsp;
    }

    public boolean validateToken(String jwt) {
        try {
            jwtService.extractUsername(jwt);
            return true;
        }catch (Exception e){
            System.out.println("error validando token = " + e.getMessage());
            return false;
        }
    }

    public User findLoggedInUser() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String username = (String) authentication.getPrincipal();
        return  userService.findOneByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User not fount. Username: "+username));

    }
}

package com.market.spring_security_market.service.auth;

import com.market.spring_security_market.dto.RegisteredUser;
import com.market.spring_security_market.dto.SaveUser;
import com.market.spring_security_market.dto.auth.AuthenticationRequest;
import com.market.spring_security_market.dto.auth.AuthenticationResponse;
import com.market.spring_security_market.exception.ObjectNotFoundException;
import com.market.spring_security_market.persistence.entity.security.JwtToken;
import com.market.spring_security_market.persistence.entity.security.User;
import com.market.spring_security_market.persistence.repository.security.JwtTokenRepository;
import com.market.spring_security_market.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticateService {

    private final UserService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenRepository jwtRepository;

    public AuthenticateService(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager, JwtTokenRepository jwtRepository) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.jwtRepository = jwtRepository;
    }


    public RegisteredUser registerOneCustomer(SaveUser newUser) {

        User user = userService.registerOneCustomer(newUser);

        String jwt = jwtService.generateToken(user,generateExtraClaims(user));

        this.saveUserToken(user,jwt);//guardar token en BD para cuando hagamos logout

        RegisteredUser userDto = new RegisteredUser();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(userDto.getUsername());
        userDto.setRole(user.getRole().getName());
        userDto.setJwt(jwt);

        return userDto;
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name",user.getName());
        extraClaims.put("role",user.getRole().getName());
        extraClaims.put("authorities",user.getAuthorities());
        return extraClaims;
    }

    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword());
        this.authenticationManager.authenticate(authentication);//verificar que el usuario sea quien dice ser con el username y password.
        UserDetails user = userService.findOneByUsername(authRequest.getUsername()).get();//buscamos en BD por username.
        String jwt = jwtService.generateToken(user, generateExtraClaims((User) user));//generación del token.
        this.saveUserToken((User) user,jwt);//guardar token en BD para cuando hagamos logout
        AuthenticationResponse authRsp = new AuthenticationResponse();
        authRsp.setJwt(jwt);
        return authRsp;
    }

    private void saveUserToken(User user, String jwt) {
        JwtToken token = new JwtToken();
        token.setToken(jwt);
        token.setUser(user);
        token.setExpitarion(jwtService.extractExpiration(jwt));
        token.setValid(true);
        jwtRepository.save(token);//guardar el token en BD
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

    public void logout(HttpServletRequest request) {
        String jwt = jwtService.extractJwtFromRequest(request);
        if (jwt == null || !StringUtils.hasText(jwt)){
            return;
        }

        Optional<JwtToken> token = jwtRepository.findByToken(jwt);
        if (token.isPresent() && token.get().isValid()){
            token.get().setValid(false);
            jwtRepository.save(token.get());//guardar el token con un estado inválido
        }
    }
}

package com.market.spring_security_market.config.security;

import com.market.spring_security_market.exception.ObjectNotFoundException;
import com.market.spring_security_market.persistence.repository.security.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityBeansInyector {

    private final UserRepository userRepository;

    public SecurityBeansInyector(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityBeansInyector(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }*/

    //inyección de dependencia por parámetro
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider();//DaoAuthenticationProvider es el proveedor de autenticación.
        authenticationStrategy.setPasswordEncoder(passwordEncoder());
        authenticationStrategy.setUserDetailsService(userDetailsService());//estrategia para extraer el usuario de la BD
        return authenticationStrategy;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return (username) -> userRepository.findByUsername(username).orElseThrow(()-> new ObjectNotFoundException("User not found whit username "+username));
    }

}

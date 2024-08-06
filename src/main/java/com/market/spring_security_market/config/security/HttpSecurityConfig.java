package com.market.spring_security_market.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity//ejecuta la creación de los componentes y configura por defecto
public class HttpSecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    public HttpSecurityConfig(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf( csrfConfig -> csrfConfig.disable())// deshabilitado ya que utilizaremos JWT
                .sessionManagement(sessMagConfig -> sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//configuración de sesión sin estado
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests( authReqConfig -> {
                        authReqConfig.requestMatchers(HttpMethod.POST,"/customers").permitAll();
                        authReqConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
                        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate").permitAll();
                        authReqConfig.anyRequest().authenticated();
                    }).build();
    }
}

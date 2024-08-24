package com.market.spring_security_market.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.market.spring_security_market.dto.ApiError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * este método se utiliza cuando la estrategia es por coincidencias de Requests Http.
     * este método se lanza cuando las credenciales no son válidas, es decir, cuando no se le genera un token*/
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ApiError apiError = new ApiError();
        apiError.setBackenMessage(authException.getLocalizedMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimeStamp(LocalDateTime.now());
        apiError.setMessage("No se encontraron credenciales de autenticación, por favor, inicie sesión para acceder a esta función.");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        /* convertimos un objeto (ApiError) a un String*/
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());//agregamos esto, ya que objectMapper no trabaja directamente con LocalDateTime
        String apiErrorAsJson = objectMapper.writeValueAsString(apiError);
        response.getWriter().write(apiErrorAsJson);
    }
}

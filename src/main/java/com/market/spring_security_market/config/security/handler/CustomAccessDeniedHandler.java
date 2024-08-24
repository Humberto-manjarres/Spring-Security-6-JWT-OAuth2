package com.market.spring_security_market.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.market.spring_security_market.dto.ApiError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * este método se utiliza cuando la estrategia es por coincidencias de Requests Http.
     * este método se lanza cuando no se tienen permisos para acceder a una función, es decir, cuando no se le genera un token*/
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ApiError apiError = new ApiError();
        apiError.setBackenMessage(accessDeniedException.getLocalizedMessage());
        apiError.setUrl(request.getRequestURL().toString());
        apiError.setMethod(request.getMethod());
        apiError.setTimeStamp(LocalDateTime.now());
        apiError.setMessage("Acceso denegado. No tienes los permisos necesarios para accederá esta función." +
                "por favor, contacta al administrador si crees que esto es un error.");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        /* convertimos un objeto (ApiError) a un String*/
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());//agregamos esto, ya que objectMapper no trabaja directamente con LocalDateTime
        String apiErrorAsJson = objectMapper.writeValueAsString(apiError);
        response.getWriter().write(apiErrorAsJson);

    }
}

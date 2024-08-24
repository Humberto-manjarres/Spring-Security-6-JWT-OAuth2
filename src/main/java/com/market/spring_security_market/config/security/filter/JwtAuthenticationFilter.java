package com.market.spring_security_market.config.security.filter;

import com.market.spring_security_market.exception.ObjectNotFoundException;
import com.market.spring_security_market.persistence.entity.security.User;
import com.market.spring_security_market.service.UserService;
import com.market.spring_security_market.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserService userService;

    public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //1. obtener encabezado http Authorization.

        String authorizationHeader = request.getHeader("Authorization");

        //si el authorizationHeader es nulo o si no empieza por el texto Bearer.
        if (!StringUtils.hasText(authorizationHeader)|| !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);//hasta aquí llega el proceso del doFilterInternal y que siga el proceso de los demás filtros.
            return;
        }

        //2. obtener el token desde el encabezado.
        String jwt = authorizationHeader.split(" ")[1];//se divide por un espacio y obtenemos la posición 1.

        //3. obtener el propietario del token (subject/username) desde el token,
        // y esta acción asu vez válida el formato del token, la firma y la fecha de expiración.
        String username = jwtService.extractUsername(jwt);

        //4. setear objeto authentication en el security contex holder.
        User user = userService.findOneByUsername(username).orElseThrow(()-> new ObjectNotFoundException("User not found Username: "+username));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            username,null, user.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        //5. ejecutar el registro de filtros
        filterChain.doFilter(request,response);

    }

}

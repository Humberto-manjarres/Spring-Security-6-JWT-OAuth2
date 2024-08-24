package com.market.spring_security_market.config.security.authorization;

import com.market.spring_security_market.exception.ObjectNotFoundException;
import com.market.spring_security_market.persistence.entity.security.Operation;
import com.market.spring_security_market.persistence.entity.security.User;
import com.market.spring_security_market.persistence.repository.security.OperationRepository;
import com.market.spring_security_market.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final OperationRepository operationRepository;

    private final UserService userService;

    public CustomAuthorizationManager(OperationRepository operationRepository, UserService userService) {
        this.operationRepository = operationRepository;
        this.userService = userService;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestContext) {
        HttpServletRequest request = requestContext.getRequest();
        String url = extracUrl(request);
        String httpMethod = request.getMethod();
        boolean isPublic = isPublic(url,httpMethod);

        //validamos si el endpoint es publico.
        if (isPublic){
            return new AuthorizationDecision(true);
        }
        //validamos si el endpoint es privado.
        boolean isGranted = isGranted(url, httpMethod, authentication.get());
        return new AuthorizationDecision(isGranted);
    }

    //método para saber si es permitido acceder a un endpoint
    private boolean isGranted(String url, String httpMethod, Authentication authentication) {
        if (authentication == null || !(authentication instanceof UsernamePasswordAuthenticationToken)){
            throw new AuthenticationCredentialsNotFoundException("User not logged in");
        }

        List<Operation> operations = obtainedOperations(authentication);

        //comparamos que la url+httpMethod es igual a la url+httpMethod que viene de BD
        boolean isGranted = operations.stream().anyMatch(getOperationPredicate(url, httpMethod));
        System.out.println("isGranted = " + isGranted);
        return isGranted;
    }

    //método para comparar si la url concatenada con el tipo de método son iguales al dato de la iteración
    private Predicate<Operation> getOperationPredicate(String url, String httpMethod) {
        return operation -> {
            String basePath = operation.getModule().getBasePath();
            Pattern pattern = Pattern.compile(basePath.concat(operation.getPath()));
            Matcher matcher = pattern.matcher(url);
            return matcher.matches() && operation.getHttpMethod().equals(httpMethod);
        };
    }

    //método para obtener las operaciones que tiene un cliente y sacar los paths y la urls
    private List<Operation> obtainedOperations(Authentication authentication) {
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
        String username = (String) authToken.getPrincipal();
        User user = userService.findOneByUsername(username).orElseThrow(()-> new ObjectNotFoundException("User not found. username: "+username));

        //como todas las tablas vienen enlazadas, extraemos las operaciones de la tabla operation.
        //esas operaciones solo son las del rol del cliente logueado.
        return user.getRole().getPermissions().stream()
                //por cada grantedPermission le sacamos la operación
                .map(grantedPermission -> grantedPermission.getOperation())
                .collect(Collectors.toList());
    }


    //en este método verificamos si algunas de las operaciones que vienen de BD coincide con la url a la que pretendemos ingresar
    private boolean isPublic(String url, String httpMethod) {
        List<Operation> publicAccessEndpoints = operationRepository.findByPublicAcces();
        boolean isPublic = publicAccessEndpoints.stream().anyMatch(getOperationPredicate(url, httpMethod));
        System.out.println("isPublic = " + isPublic);
        return isPublic;
    }


    private String extracUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String url = request.getRequestURI();
        url = url.replace(contextPath,"");
        System.out.println("url = " + url);
        return url;
    }
}

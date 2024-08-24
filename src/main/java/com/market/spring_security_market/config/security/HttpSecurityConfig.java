package com.market.spring_security_market.config.security;

import com.market.spring_security_market.config.security.filter.JwtAuthenticationFilter;
import com.market.spring_security_market.persistence.util.RoleEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity//habilitamos la seguridad web basada en peticiones Http y también ejecuta la creación de los componentes y configura por defecto
//@EnableMethodSecurity(prePostEnabled = true) //habilitamos la configuración de autorización a través de anotaciones en métodos
public class HttpSecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final AccessDeniedHandler accessDeniedHandler;

    private final AuthorizationManager<RequestAuthorizationContext> authorizationManager;

    public HttpSecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationEntryPoint authenticationEntryPoint, AccessDeniedHandler accessDeniedHandler, AuthorizationManager<RequestAuthorizationContext> authorizationManager) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authorizationManager = authorizationManager;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf( csrfConfig -> csrfConfig.disable())// deshabilitado ya que utilizaremos JWT
                .sessionManagement(sessMagConfig -> sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//configuración de sesión sin estado
                .authenticationProvider(authenticationProvider)
                //con el addFilterBefore() se agrega filtro jwtAuthenticationFilter antes de que se ejecute el filtro UsernamePasswordAuthenticationFilter.class
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests( authReqConfig -> {
                    //this.buildRequestMatchers(authReqConfig);
                    authReqConfig.anyRequest().access(authorizationManager);//cualquier petición será autorizada mediante el authorizationManager
                })
                .exceptionHandling(exceptionConfig -> {
                    exceptionConfig.authenticationEntryPoint(authenticationEntryPoint);//esto se agrega para el manejo de excepción cuando no tiene token.
                    exceptionConfig.accessDeniedHandler(accessDeniedHandler);//esto se agrega para el manejo de excepción cuando no tiene permisos a una función.
                })
                .build();
    }

    private static void buildRequestMatchers(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        //AUTORIZACIÓN BASADA EN COINCIDENCIAS REQUEST HTTP
        /*
        * Autorización de endpoints de productos.
        * */
        authReqConfig.requestMatchers(HttpMethod.GET,"/products")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.GET,"/products/{productId}").hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.POST,"/products").hasRole(RoleEnum.ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.PUT,"/products/{productId}").hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.PUT,"/products/{productId}/disabled").hasRole(RoleEnum.ADMINISTRATOR.name());


        /*
        * Autorización de endpoints de categorías
        * */
        authReqConfig.requestMatchers(HttpMethod.GET,"/categories").hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.GET,"/categories/{categoryId}").hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.POST,"/categories").hasRole(RoleEnum.ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.PUT,"/categories/{categoryId}").hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.PUT,"/categories/{categoryId}/disabled").hasRole(RoleEnum.ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/profile")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name(), RoleEnum.CUSTOMER.name());

        /*
        * Autorización de endpoints públicos
        * */
        authReqConfig.requestMatchers(HttpMethod.POST,"/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate").permitAll();

        authReqConfig.anyRequest().authenticated();
    }

    private static void buildRequestMatchersV2(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        //AUTORIZACIÓN BASADA EN ANOTACIONES EN MÉTODOS
        /*
         * Autorización de endpoints públicos
         * */
        authReqConfig.requestMatchers(HttpMethod.POST,"/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate").permitAll();

        /*
        * los demás endpoints serán privados y tendrán que autenticarse*/
        authReqConfig.anyRequest().authenticated();
    }
}

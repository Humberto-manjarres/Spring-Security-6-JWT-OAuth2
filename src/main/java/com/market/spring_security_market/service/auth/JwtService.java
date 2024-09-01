package com.market.spring_security_market.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(UserDetails user,Map<String,Object> extraClaims) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date((EXPIRATION_IN_MINUTES * 60 * 1000)+ issuedAt.getTime());

        String jwt = Jwts.builder()
                .claims(extraClaims)//los claims son las propiedades del payload del JWT.
                .subject(user.getUsername())
                .issuedAt(issuedAt).expiration(expiration)//fecha de expiración del token.
                .header()//header
                .type("JWT").and()
                .signWith(generateKey(), Jwts.SIG.HS256)//firmar
                .compact();
        return jwt;
    }

    private SecretKey generateKey() {
        byte[] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);//decodificando un BASE64
        System.out.println("passwordDecoded => " + new String(passwordDecoded));
        return Keys.hmacShaKeyFor(passwordDecoded);
    }

    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(jwt).getPayload();
    }

    public String extractJwtFromRequest(HttpServletRequest request) {
        //1. obtener encabezado http Authorization.

        String authorizationHeader = request.getHeader("Authorization");

        //si el authorizationHeader es nulo o si no empieza por el texto Bearer.
        if (!StringUtils.hasText(authorizationHeader)|| !authorizationHeader.startsWith("Bearer ")){
            return null;
        }

        return authorizationHeader.split(" ")[1];//se divide por un espacio y obtenemos la posición 1.

    }

    public Date extractExpiration(String jwt) {
        return extractAllClaims(jwt).getExpiration();
    }
}

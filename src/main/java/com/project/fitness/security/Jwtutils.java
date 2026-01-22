package com.project.fitness.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class Jwtutils {
    private String jwtSecret = "bXktdmVyeS1sb25nLXNlY3VyZS1qd3Qtc2VjcmV0LWtleS0zMi1ieXRlcw==\n" ;
    private int jwtExpiration = 17280000 ;

    public String getJwtFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(bearerToken != null && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7) ;

        return null ;
    }

    public String generateToken(String userId, String role){


        return Jwts.builder()
                .subject(userId)
                .claim("roles", List.of(new SimpleGrantedAuthority(role)))
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpiration))
                .signWith(key())
                .compact();
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(jwtToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    public String getUserIdFromToken(String jwt) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject();
    }

    public List<String> getRolesNameFromToken(String jwt) {

        return Collections.singletonList(Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject());
    }


    public Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}

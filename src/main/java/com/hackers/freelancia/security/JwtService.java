package com.hackers.freelancia.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    public  String SECRET_KEY ;
    @Value("${jwt.expiration}")
    public  long EXPIRATION_TIME; 

    

    /**
     * Génère un token JWT pour l'utilisateur donné.
     *
     * @param userDetails les détails de l'utilisateur pour lequel générer le token
     * @return le token JWT généré
     */
    public  String generateToken(UserDetails userDetails){

        Map<String, Object> claims = new HashMap<>();

        claims.put("roles", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    /**
     * Extrait le nom d'utilisateur du token JWT.
     *
     * @param token le token JWT à partir duquel extraire le nom d'utilisateur
     * @return le nom d'utilisateur extrait du token
     */
    public String extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getSecret() {
        return SECRET_KEY;
    }

}

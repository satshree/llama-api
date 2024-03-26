package com.llama.api.authentication.jwt;

import com.llama.api.users.models.Users;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${llama.app.jwtSecret}")
    String jwtSecret;

    @Value("${llama.app.jwtExpirationMs}")
    Integer jwtExpirationMs;

    @Value("${llama.app.jwtRefreshExpirationMs}")
    Integer jwtRefreshExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        Users userPrincipal = (Users) authentication.getPrincipal();

        Date issuedAt = new Date();
        Date expiration = Date.from(new Date().toInstant().plusMillis(jwtExpirationMs));


        String token = Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, key())
                .compact();

        return token;
    }

    public String generateTokenFromUsername(String username) {
        Date issuedAt = new Date();
        Date expiration = Date.from(new Date().toInstant().plusMillis(jwtExpirationMs));

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, key())
                .compact();
    }

    public String generateRefreshTokenFromUsername(String username) {
        Date issuedAt = new Date();
        Date expiration = Date.from(new Date().toInstant().plusMillis(jwtRefreshExpirationMs));

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, key())
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(key())
                    .build()
                    .parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
//            throw new MalformedJwtException("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
//            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
//            throw new UnsupportedJwtException("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
//            throw new IllegalArgumentException("JWT claims string is empty: " + e.getMessage());
        }

        return false;
    }
}

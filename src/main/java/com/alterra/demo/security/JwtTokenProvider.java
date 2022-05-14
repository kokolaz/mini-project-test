package com.alterra.demo.security;

import com.alterra.demo.domain.model.Admin;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class JwtTokenProvider {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("600000")
    private Long expiration;

    public String generationToken(Authentication authentication){
        final Admin admin = (Admin) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() * expiration);

        Map<String,Object> claims = new HashMap<>();
        claims.put("username", admin.getUsername());

        return Jwts.builder()
                .setId(admin.getId().toString())
                .setSubject(admin.getUsername())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException ex){
            log.error("Invalid Jwt Signature : {}", ex.getMessage());
        } catch (MalformedJwtException ex){
            log.error("Invalid Jwt Token : {}", ex.getMessage());
        } catch (ExpiredJwtException ex){
            log.error("Invalid Jwt Token : {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported Jwt Token : {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("Unsupported Jwt Token : {}", ex.getMessage());
        }
        return false;
    }

    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }
}

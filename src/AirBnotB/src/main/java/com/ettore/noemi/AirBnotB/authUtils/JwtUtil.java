package com.ettore.noemi.AirBnotB.authUtils;

import com.ettore.noemi.AirBnotB.DTOs.userDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.security.Key;
import org.springframework.http.HttpRequest;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}") 
    private String SECRET_KEY;

    private long DURATIONms = 1000 * 60 * 60 * 12; 

    //crea il token (nota: sotto i 32B di chiave sulle properties crasha tutto)
    public String generateToken(String username) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.builder()
                .setIssuer("AirBnotB")
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + DURATIONms))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // autoesplicativo, i claim sono estratti, questi sono solamente dei wrapper
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // estrazione dei diritti tramite i generici (ma va una volta ogni mai)
    public <T> T extractClaim(String token, ClaimsResolver<T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.resolve(claims);
    }

    private Claims extractAllClaims(String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.parserBuilder()
                .setSigningKey(key)  
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    @FunctionalInterface
    public interface ClaimsResolver<T> {
        T resolve(Claims claims);
    }
    
    
}

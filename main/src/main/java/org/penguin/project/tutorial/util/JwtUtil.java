package org.penguin.project.tutorial.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.penguin.project.tutorial.security.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil implements Serializable {

    private static final long serialVersionUID = 5449337707724894388L;

    @Value("${tutorial.app.jwtSecret}")
    private String jwtSecret;

    @Value("${tutorial.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        SecurityUserDetails userPrincipal = (SecurityUserDetails) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities().stream().map(GrantedAuthority:: getAuthority).collect(Collectors.toList());
        log.info("登录用户信息：{},{},{}", userPrincipal.getUsername(), userPrincipal.getEmail(), roles);
        return Jwts.builder()
                .setIssuer("Tutorial By Terry")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .setSubject((userPrincipal.getUsername()))
                .claim("Roles", roles)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        } catch (SignatureException e){
            log.error("Invalid JWT token: {}", e.getMessage());
        }

        return false;
    }
}
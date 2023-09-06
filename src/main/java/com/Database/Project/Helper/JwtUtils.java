package com.Database.Project.Helper;


import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

//    @Value("${beholder.app.jwtSecret}")
    private final String jwtSecret = "mySecretKey";

//    @Value("${beholder.app.jwtExpirationMs}")
    private final int jwtExpirationMs = 3600000;

//    @Value("${beholder.app.jwtRefreshExpirationMs}")
    private final int jwtRefreshExpirationMs = 259200000;

    public String generateJwtToken(Authentication authentication) {

        String secureUser = (String) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(secureUser)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateRefreshJwtToken(Authentication authentication) {

        String secureUser = (String) authentication.getPrincipal();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        Map<String, Object> roles = new HashMap<>();
        roles.put("scope", "admin");
        grantedAuthorities.add(new SimpleGrantedAuthority("admin"));
        return Jwts.builder()
                .setClaims(roles)
                .setSubject(secureUser)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs + jwtRefreshExpirationMs))
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
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}
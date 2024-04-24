package com.revature.shop.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import com.revature.shop.dtos.responses.Principal;
import com.revature.shop.models.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@SuppressWarnings("deprecation")
public class TokenService {
    private String secretKey;

    public TokenService() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
        Properties props = new Properties();
        props.load(is);
        secretKey = props.getProperty("secret");
    }

    public String generateToken(Principal principal) {
        return Jwts.builder()
                .setId(principal.getId())
                .setIssuer("shop")
                .setSubject(principal.getEmail())
                .claim("roleId", principal.getRole().getId())
                .claim("roleName", principal.getRole().getName())
                .setExpiration(new Date(System.currentTimeMillis() + 36000000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Principal parseToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return new Principal(
                claims.getId(),
                claims.getSubject(),
                new Role(
                        claims.get("roleId", String.class),
                        claims.get("roleName", String.class)));
    }
}

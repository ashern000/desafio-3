package com.compass.infraestructure.adapters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.compass.application.adapters.TokenAdapter;
import com.compass.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class TokenService implements TokenAdapter {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String generateToken(User user, Instant issuedAt, Instant expiresAt) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("ecommerce-compass")
                .withSubject(user.getEmail().getValue())
                .withClaim("role", user.getRole().getRole())
                .withIssuedAt(Date.from(issuedAt))
                .withExpiresAt(Date.from(expiresAt))
                .sign(algorithm);
    }

    @Override
    public boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWT.require(algorithm)
                    .withIssuer("ecommerce-compass")
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("ecommerce-compass")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e){
            return "";
        }
    }
}

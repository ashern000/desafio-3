package com.compass.application.adapters;

import com.compass.domain.user.User;

import java.time.Instant;

public interface TokenAdapter {

    String generateToken(User user, Instant issuedAt, Instant expiresAt);

    boolean verifyToken(String token);
}

package com.compass.application.user.login;

import com.compass.application.adapters.CryptoAdapter;
import com.compass.application.adapters.TokenAdapter;
import com.compass.domain.exceptions.DomainException;
import com.compass.domain.user.Email;
import com.compass.domain.user.User;
import com.compass.domain.user.UserGateway;
import com.compass.domain.validation.Error;

import java.time.Instant;
import java.util.Objects;

public class DefaultLoginUserUseCase extends LoginUserUseCase {
    private UserGateway userGateway;
    private CryptoAdapter crypto;
    private TokenAdapter tokenService;

    public DefaultLoginUserUseCase(final UserGateway userGateway, final CryptoAdapter crypto, final TokenAdapter tokenService) {
        this.userGateway = Objects.requireNonNull(userGateway);
        this.crypto = Objects.requireNonNull(crypto);
        this.tokenService = Objects.requireNonNull(tokenService);
    }

    @Override
    public LoginUserOutput execute(LoginUserCommand aCommand) {
        final var anEmail = Email.newEmail(aCommand.email());
        final var aPassword = aCommand.password();

        if (!this.userGateway.findByEmail(anEmail).isPresent()) {
            throw DomainException.with(new Error("Usuário ou senha inválidos"));
        }

        User userPersistence = this.userGateway.findByEmail(anEmail).orElseThrow();

        if (!crypto.matches(aPassword, userPersistence.getPassword().getValue())) {
            throw DomainException.with(new Error("Usuário ou senha inválidos"));
        }

// Gere o token
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plusSeconds(600);  // Expira em 10 minutos
        String token = this.tokenService.generateToken(userPersistence, issuedAt, expiresAt);

        return new LoginUserOutput(userPersistence.getId().getValue(),userPersistence.getId().getValue(), userPersistence.getEmail().getValue(), token);

    }
}

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


/**
 * DefaultLoginUserUseCase class represents the use case for logging in a user.
 * It extends LoginUserUseCase.
 */
public class DefaultLoginUserUseCase extends LoginUserUseCase {
    // Fields representing the user gateway, crypto adapter, and token service
    private UserGateway userGateway;
    private CryptoAdapter crypto;
    private TokenAdapter tokenService;

    /**
     * Constructor for the DefaultLoginUserUseCase class.
     *
     * @param userGateway The user gateway.
     * @param crypto The crypto adapter.
     * @param tokenService The token service.
     */
    public DefaultLoginUserUseCase(final UserGateway userGateway, final CryptoAdapter crypto, final TokenAdapter tokenService) {
        this.userGateway = Objects.requireNonNull(userGateway);
        this.crypto = Objects.requireNonNull(crypto);
        this.tokenService = Objects.requireNonNull(tokenService);
    }

    /**
     * Method to execute the use case. It logs in a user based on the provided command,
     * validates the user's email and password, generates a token, and returns the login output.
     *
     * @param aCommand The command to log in a user.
     * @return The output of the user login.
     */
    @Override
    public LoginUserOutput execute(LoginUserCommand aCommand) {
        final var anEmail = Email.newEmail(aCommand.email());
        final var aPassword = aCommand.password();

        if (!this.userGateway.findByEmail(anEmail).isPresent()) {
            throw DomainException.with(new Error("Usuário ou senha inválidos email"));
        }

        User userPersistence = this.userGateway.findByEmail(anEmail).orElseThrow();

        if (!crypto.matches(aPassword, userPersistence.getPassword().getValue())) {
            throw DomainException.with(new Error("Usuário ou senha inválidos"));
        }

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plusSeconds(1200);
        String token = this.tokenService.generateToken(userPersistence, issuedAt, expiresAt);

        return new LoginUserOutput(userPersistence.getId().getValue(), userPersistence.getEmail().getValue(), token);
    }
}

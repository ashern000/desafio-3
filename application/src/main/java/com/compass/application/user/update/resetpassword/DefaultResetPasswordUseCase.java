package com.compass.application.user.update.resetpassword;

import com.compass.application.adapters.CryptoAdapter;
import com.compass.application.adapters.EmailAdapter;
import com.compass.application.adapters.TokenAdapter;
import com.compass.domain.exceptions.DomainException;
import com.compass.domain.user.Email;
import com.compass.domain.user.Password;
import com.compass.domain.user.User;
import com.compass.domain.user.UserGateway;
import com.compass.domain.validation.Error;
import com.compass.domain.validation.handler.Notification;
import io.vavr.control.Either;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

import java.time.Instant;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * DefaultResetPasswordUseCase class represents the use case for resetting a user's password.
 * It extends ResetPasswordUseCase.
 */
public class DefaultResetPasswordUseCase extends ResetPasswordUseCase{

    // Fields representing the email adapter, user gateway, token adapter, and crypto adapter
    private final EmailAdapter email;
    private final UserGateway userGateway;
    private final TokenAdapter tokenAdapter;
    private final CryptoAdapter crypto;

    /**
     * Constructor for the DefaultResetPasswordUseCase class.
     *
     * @param emailAdapter The email adapter.
     * @param userGateway The user gateway.
     * @param tokenAdapter The token adapter.
     * @param crypto The crypto adapter.
     */
    public DefaultResetPasswordUseCase(final EmailAdapter emailAdapter, final UserGateway userGateway, final TokenAdapter tokenAdapter, final CryptoAdapter crypto) {
        this.email = Objects.requireNonNull(emailAdapter);
        this.userGateway = Objects.requireNonNull(userGateway);
        this.tokenAdapter = Objects.requireNonNull(tokenAdapter);
        this.crypto = Objects.requireNonNull(crypto);
    }

    /**
     * Method to execute the use case. It resets a user's password based on the provided command,
     * validates the new password, generates a token, sends an email with the token, and returns the reset password output.
     *
     * @param aCommand The command to reset a user's password.
     * @return Either a notification of validation errors or the output of the password reset.
     */
    @Override
    public Either<Notification, ResetPasswordOutput> execute(ResetPasswordCommand aCommand) {
        final var notification = Notification.create();
        final var anEmail = Email.newEmail(aCommand.email());
        final var anPassword = Password.newPassword(aCommand.password());
        anPassword.validate(notification);
        final var anPasswordHashed = Password.with(crypto.encode(anPassword.getValue()));

        final var actualUser = this.userGateway.findByEmail(anEmail).orElseThrow(notFound(anEmail));
        actualUser.hashedPasswordDefine(anPasswordHashed);

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plusSeconds(1200);
        final var token = tokenAdapter.generateToken(actualUser, issuedAt, expiresAt);

        if(!notification.hasErrors()) {
            email.send(actualUser.getEmail(),  """
                    Dear User,
                                        
                    We hope this message finds you well.
                                        
                    We're writing to let you know that your password reset request was successful. Your account security is our top priority!
                                        
                    Here is your temporary token which allows you to log in to your account:
                                        
                    **Your temporary login token: TOKEN**
                                        
                    Please use this token to log in to your Compass E-commerce account. Once you're logged in, we strongly recommend that you update your password to something only you would know.
                                        
                    Remember, Compass E-commerce staff will never ask you for your password, so please keep it safe!
                                        
                    If you didn't request this password reset, please let us know immediately.
                                        
                    Thank you for being a valued member of our community!
                    
                    """, token);
        }

        return notification.hasErrors() ? Left(notification) : reset(actualUser);
    }

    /**
     * Private method to reset a user's password. It tries to update the user through the user gateway,
     * and returns either a notification of errors or the output of the password reset.
     *
     * @param user The user whose password is to be reset.
     * @return Either a notification of errors or the output of the password reset.
     */
    private Either<Notification, ResetPasswordOutput> reset(final User user) {
        return Try(() -> this.userGateway.update(user))
                .toEither()
                .bimap(Notification::create, ResetPasswordOutput::from);
    }

    /**
     * Private method to handle not found exception.
     *
     * @param anEmail The email of the user.
     * @return A supplier of DomainException.
     */
    private Supplier<DomainException> notFound(final Email anEmail) {
        return () -> DomainException.with(new Error("Email with %s was not found".formatted(anEmail.getValue())));
    }
}

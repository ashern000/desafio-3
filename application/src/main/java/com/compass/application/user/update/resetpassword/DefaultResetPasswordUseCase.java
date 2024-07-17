package com.compass.application.user.update.resetpassword;

import com.compass.application.adapters.CryptoAdapter;
import com.compass.application.adapters.EmailAdapter;
import com.compass.application.adapters.TokenAdapter;
import com.compass.domain.exceptions.DomainException;
import com.compass.domain.product.ProductID;
import com.compass.domain.user.Email;
import com.compass.domain.user.Password;
import com.compass.domain.user.UserGateway;
import com.compass.domain.validation.Error;
import com.compass.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultResetPasswordUseCase extends ResetPasswordUseCase{

    private final EmailAdapter email;
    private final UserGateway userGateway;
    private final TokenAdapter tokenAdapter;
    private final CryptoAdapter crypto;

    public DefaultResetPasswordUseCase(final EmailAdapter emailAdapter, final UserGateway userGateway, final TokenAdapter tokenAdapter, final CryptoAdapter crypto) {
        this.email = Objects.requireNonNull(emailAdapter);
        this.userGateway = Objects.requireNonNull(userGateway);
        this.tokenAdapter = Objects.requireNonNull(tokenAdapter);
        this.crypto = Objects.requireNonNull(crypto);
    }
    @Override
    public Either<Notification, ResetPasswordOutput> execute(ResetPasswordCommand aCommand) {
        final var anEmail = Email.newEmail(aCommand.email());
        final var anPassword = Password.with(aCommand.password());

        final var notification = Notification.create();
        final var actualUser = this.userGateway.findByEmail(anEmail).orElseThrow(notFound(anEmail));


    }

    private Supplier<DomainException> notFound(final Email anEmail) {
        return () -> DomainException.with(new Error("Product with ID %s was not found".formatted(anEmail.getValue())));
    }
}

package com.compass.application.user.create;

import com.compass.application.adapters.CryptoAdapter;
import com.compass.domain.user.Password;
import com.compass.domain.user.User;
import com.compass.domain.user.UserGateway;
import com.compass.domain.validation.handler.Notification;
import io.vavr.control.Either;
import static io.vavr.API.Left;
import static io.vavr.API.Try;

import java.util.Objects;

public class DefaultCreateUserUseCase extends CreateUserUseCase{
    private final UserGateway userGateway;
    private final CryptoAdapter crypto;

    public DefaultCreateUserUseCase(final UserGateway gateway, final CryptoAdapter crypto) {
        this.userGateway = Objects.requireNonNull(gateway);
        this.crypto = Objects.requireNonNull(crypto);
    }

    @Override
    public Either<Notification, CreateUserOutput> execute(CreateUserCommand aCommand) {
        final var aName = aCommand.name();
        final var anEmail = aCommand.email();
        final var aPassword = aCommand.password();
        final var anActive = aCommand.active();
        final var aRole = aCommand.role();

        final var notification = Notification.create();
        final var actualUser = User.newUser(aName, anEmail,aPassword,anActive,aRole);
        actualUser.validate(notification);
        final var hashedPassword = crypto.encode(aPassword);
        actualUser.hashedPasswordDefine(Password.with(hashedPassword));

        return notification.hasErrors() ? Left(notification) : create(actualUser);

    }

    private Either<Notification, CreateUserOutput> create(User actualUser) {
        return Try(() -> this.userGateway.create(actualUser))
                .toEither()
                .mapLeft(Notification::create).map(CreateUserOutput::from);
    }
}

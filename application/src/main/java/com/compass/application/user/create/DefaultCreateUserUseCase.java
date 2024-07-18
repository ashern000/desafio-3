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

/**
 * DefaultCreateUserUseCase class represents the use case for creating a new user.
 * It extends CreateUserUseCase.
 */
public class DefaultCreateUserUseCase extends CreateUserUseCase{
    // Fields representing the user gateway and crypto adapter
    private final UserGateway userGateway;
    private final CryptoAdapter crypto;

    /**
     * Constructor for the DefaultCreateUserUseCase class.
     *
     * @param gateway The user gateway.
     * @param crypto The crypto adapter.
     */
    public DefaultCreateUserUseCase(final UserGateway gateway, final CryptoAdapter crypto) {
        this.userGateway = Objects.requireNonNull(gateway);
        this.crypto = Objects.requireNonNull(crypto);
    }

    /**
     * Method to execute the use case. It creates a new user based on the provided command,
     * validates the user, encodes the password, and creates the user if there are no validation errors.
     *
     * @param aCommand The command to create a new user.
     * @return Either a notification of validation errors or the output of the user creation.
     */
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

    /**
     * Private method to create a user. It tries to create the user through the user gateway,
     * and returns either a notification of errors or the output of the user creation.
     *
     * @param actualUser The user to be created.
     * @return Either a notification of errors or the output of the user creation.
     */
    private Either<Notification, CreateUserOutput> create(User actualUser) {
        return Try(() -> this.userGateway.create(actualUser))
                .toEither().bimap(Notification::create, CreateUserOutput::from);
    }
}

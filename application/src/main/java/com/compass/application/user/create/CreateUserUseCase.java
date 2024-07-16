package com.compass.application.user.create;

import com.compass.application.UseCase;
import com.compass.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateUserUseCase extends UseCase<CreateUserCommand, Either<Notification, CreateUserOutput>> {
}

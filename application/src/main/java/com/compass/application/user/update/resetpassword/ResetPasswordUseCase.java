package com.compass.application.user.update.resetpassword;

import com.compass.application.UseCase;
import com.compass.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class ResetPasswordUseCase extends UseCase<ResetPasswordCommand, Either<Notification, ResetPasswordOutput>> {
}

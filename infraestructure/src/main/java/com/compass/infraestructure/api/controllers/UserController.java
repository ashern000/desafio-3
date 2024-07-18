package com.compass.infraestructure.api.controllers;

import com.compass.application.product.create.CreateProductOutput;
import com.compass.application.user.create.CreateUserCommand;
import com.compass.application.user.create.CreateUserOutput;
import com.compass.application.user.create.CreateUserUseCase;
import com.compass.application.user.login.LoginUserCommand;
import com.compass.application.user.login.LoginUserOutput;
import com.compass.application.user.login.LoginUserUseCase;
import com.compass.application.user.update.resetpassword.ResetPasswordCommand;
import com.compass.application.user.update.resetpassword.ResetPasswordUseCase;
import com.compass.domain.exceptions.DomainException;
import com.compass.domain.validation.handler.Notification;
import com.compass.infraestructure.api.UserAPI;
import com.compass.infraestructure.user.models.CreateUserApiInput;
import com.compass.infraestructure.user.models.LoginUserApiInput;
import com.compass.infraestructure.user.models.ResetPasswordApiInput;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class UserController implements UserAPI {

    private final CreateUserUseCase createUserUseCase;

    private final LoginUserUseCase loginUserUseCase;

    private final ResetPasswordUseCase resetPasswordUseCase;


    public UserController(final CreateUserUseCase createUserUseCase, final LoginUserUseCase loginUserUseCase, final ResetPasswordUseCase resetPasswordUseCase) {
        this.createUserUseCase = Objects.requireNonNull(createUserUseCase);
        this.loginUserUseCase = Objects.requireNonNull(loginUserUseCase);
        this.resetPasswordUseCase = Objects.requireNonNull(resetPasswordUseCase);

    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public ResponseEntity<?> createUser(CreateUserApiInput input) {
        final var aName = input.name();
        final var anEmail = input.email();
        final var aPassword = input.password();
        final var active = input.active() != null ? input.active() : true;
        final var aRole = input.role();

        final var aCommand = CreateUserCommand.with(aName, anEmail, aPassword, active, aRole);

        final Function<Notification, ResponseEntity<?>> onError = notification -> {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("code", HttpStatus.UNPROCESSABLE_ENTITY.value());
            responseBody.put("errors", notification.getErrors());
            responseBody.put("status", HttpStatus.UNPROCESSABLE_ENTITY.name());
            return ResponseEntity.unprocessableEntity().body(responseBody);
        };

        final Function<CreateUserOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;


        return this.createUserUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    @Cacheable(value = "users")
    public ResponseEntity<?> loginUser(LoginUserApiInput input) {
        final var anEmail = input.email();
        final var aPassword = input.password();

        final var aCommand = LoginUserCommand.with(anEmail, aPassword);

        try {
            final LoginUserOutput output = this.loginUserUseCase.execute(aCommand);
            return ResponseEntity.ok(output);
        } catch (DomainException e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("code", HttpStatus.UNPROCESSABLE_ENTITY.value());
            responseBody.put("errors", e.getErrors());
            responseBody.put("status", HttpStatus.UNPROCESSABLE_ENTITY.name());
            return ResponseEntity.unprocessableEntity().body(responseBody);
        }
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public ResponseEntity<?> resetPassword(ResetPasswordApiInput input) {
        final var anEmail = input.email();
        final var aPassword = input.password();

        final var aCommand = ResetPasswordCommand.with(anEmail, aPassword);
        System.out.println("reset password");
        try {
            final var output = ResponseEntity.ok(this.resetPasswordUseCase.execute(aCommand));
            System.out.println(output);
            return output;
        } catch (DomainException e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("code", HttpStatus.UNPROCESSABLE_ENTITY.value());
            responseBody.put("errors", e.getErrors());
            responseBody.put("status", HttpStatus.UNPROCESSABLE_ENTITY.name());
            return ResponseEntity.unprocessableEntity().body(responseBody);
        }
    }
}

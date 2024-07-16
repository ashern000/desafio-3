package com.compass.domain.user;

import com.compass.domain.exceptions.DomainException;
import com.compass.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class UserTest {

    @Test
    public void givenAValidPassword_whenCallNewPassword() {
        final String expectedPassword = "Password1@";
        final Password password = Password.newPassword(expectedPassword, new ThrowsValidationHandler());
        Assertions.assertNotNull(password);
        Assertions.assertEquals(expectedPassword, password.getPassword());
    }

    @Test
    public void givenAnInvalidPassword_whenCallNewPassword_thenShouldReceiveError() {
        final String expectedPassword = "password";
        final var expectedErrorMessage = "Senha inválida";
        final var actualException = Assertions.assertThrows(DomainException.class, () -> Password.newPassword(expectedPassword, new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAValidEmail_whenCallNewEmail() {
        final String expectedEmail = "test@example.com";
        final Email email = Email.newEmail(expectedEmail, new ThrowsValidationHandler());
        Assertions.assertNotNull(email);
        Assertions.assertEquals(expectedEmail, email.getEmail());
    }

    @Test
    public void givenAnInvalidEmail_whenCallNewEmail_thenShouldReceiveError() {
        final String expectedEmail = "test";
        final var expectedErrorMessage = "Email inválido";
        final var actualException = Assertions.assertThrows(DomainException.class, () -> Email.newEmail(expectedEmail, new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAValidUserRole_whenCallOf() {
        final String expectedRole = "USER";
        final Optional<UserRole> role = UserRole.of(expectedRole);
        Assertions.assertTrue(role.isPresent());
        Assertions.assertEquals(expectedRole, role.get().getRole());
    }

    @Test
    public void givenAnInvalidUserRole_whenCallOf_thenShouldReceiveEmpty() {
        final String expectedRole = "INVALID_ROLE";
        final Optional<UserRole> role = UserRole.of(expectedRole);
        Assertions.assertTrue(role.isEmpty());
    }

    @Test
    public void givenValidUserDetails_whenCallNewUser_thenShouldCreateUser() {
        final String expectedName = "Test User";
        final var expectedEmail = "test@example.com";
        final var expectedPassword = "Password@1";
        final boolean isActive = true;
        final UserRole expectedRole = UserRole.USER_ROLE;

        final User user = User.newUser(expectedName, expectedEmail, expectedPassword, isActive, expectedRole.getRole());

        Assertions.assertNotNull(user);
        Assertions.assertEquals(expectedName, user.getName());
        Assertions.assertEquals(expectedEmail, user.getEmail().getEmail());
        Assertions.assertEquals(expectedPassword, user.getPassword().getPassword());
        Assertions.assertEquals(isActive, user.isActive());
        Assertions.assertEquals(expectedRole, user.getRole());
        Assertions.assertNotNull(user.getCreatedAt());
        Assertions.assertNotNull(user.getUpdatedAt());
    }

    @Test
    public void givenInvalidEmail_whenCallNewUser_thenShouldThrowError() {
        final String expectedName = "Test User";
        final var expectedEmail = "test"; // fornecer um e-mail inválido
        final var expectedPassword = "Password@1";
        final boolean isActive = true;
        final UserRole expectedRole = UserRole.USER_ROLE;

        final var expectedErrorMessage = "Email invalido";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> {
            User.newUser(expectedName, expectedEmail, expectedPassword, isActive, expectedRole.getRole());
        });
        Assertions.assertTrue(actualException.getErrors().get(0).message().contains(expectedErrorMessage));
    }

    @Test
    public void givenInvalidPassword_whenCallNewUser_thenShouldThrowError() {
        final String expectedName = "Test User";
        final var expectedEmail = "test@test.com";
        final var expectedPassword = "password";
        final boolean isActive = true;
        final UserRole expectedRole = UserRole.USER_ROLE;

        final var expectedErrorMessage = "Senha inválida";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> {
            User.newUser(expectedName, expectedEmail, expectedPassword, isActive, expectedRole.getRole());
        });
        Assertions.assertTrue(actualException.getErrors().get(0).message().contains(expectedErrorMessage));
    }

    @Test
    public void givenInvalidEmailAndPassword_whenCallNewUser_thenShouldThrowError() {
        final String expectedName = "Test User";
        final var expectedEmail = "test"; // fornecer um e-mail inválido
        final var expectedPassword = "password"; // fornecer uma senha inválida
        final boolean isActive = true;
        final UserRole expectedRole = UserRole.USER_ROLE;

        final var expectedEmailErrorMessage = "Email invalido";
        final var expectedPasswordErrorMessage = "Senha inválida";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> {
            User.newUser(expectedName, expectedEmail, expectedPassword, isActive, expectedRole.getRole());
        });

        Assertions.assertTrue(actualException.getErrors().get(0).message().contains(expectedEmailErrorMessage));
        Assertions.assertTrue(actualException.getErrors().get(1).message().contains(expectedPasswordErrorMessage));

    }

}

package com.compass.domain.user;

import com.compass.domain.AggregateRoot;
import com.compass.domain.exceptions.DomainException;
import com.compass.domain.product.Product;
import com.compass.domain.validation.Error;
import com.compass.domain.validation.ValidationHandler;
import com.compass.domain.validation.handler.Notification;

import java.time.Instant;
import java.util.Objects;

/**
 * User class represents a user entity in the domain.
 * It extends AggregateRoot and implements Cloneable.
 */
public class User extends AggregateRoot<UserID> implements Cloneable {

    // Fields representing user properties
    private String name;
    private Email email;
    private Password password;
    private boolean active;
    private UserRole role;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    /**
     * Private constructor for the User class.
     *
     * @param userID The unique identifier for the user.
     * @param name The name of the user.
     * @param email The email of the user.
     * @param password The password of the user.
     * @param anActive The active status of the user.
     * @param userRole The role of the user.
     * @param aCreated The timestamp when the user was created.
     * @param anUpdated The timestamp when the user was last updated.
     * @param aDeletedAt The timestamp when the user was deleted.
     */
    private User(UserID userID, final String name, final Email email, final Password password, final boolean anActive,final UserRole userRole, final Instant aCreated, final Instant anUpdated, final Instant aDeletedAt) {
        super(userID);
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = anActive;
        this.role = userRole;
        this.createdAt = Objects.requireNonNull(aCreated);
        this.updatedAt = Objects.requireNonNull(anUpdated);
        this.deletedAt = aDeletedAt;
    }

    /**
     * Validates the user using the provided validation handler.
     *
     * @param handler The validation handler.
     */
    public void validate(ValidationHandler handler) {
        if (this.name == null || this.name.trim().isEmpty()) {
            handler.append(new Error("'name' should not be empty"));
        }
        this.email.validate(handler);
        this.password.validate(handler);
    }

    /**
     * Static factory method to create a new user.
     *
     * @param aName The name of the user.
     * @param anEmail The email of the user.
     * @param aPassword The password of the user.
     * @param isActive The active status of the user.
     * @param roleUser The role of the user.
     * @return A new User instance.
     */
    public static User newUser(final String aName, final String anEmail, final String aPassword, final boolean isActive, final String roleUser) {
        final UserID anId = UserID.unique();
        final Instant now = Instant.now();
        final Instant deletedAt = isActive ? null : now;
        final UserRole role = UserRole.of(roleUser).orElseThrow(() -> new IllegalArgumentException("Role inválido"));

        final Email email = Email.newEmail(anEmail);
        final Password password = Password.newPassword(aPassword);

        final User user = new User(anId, aName, email, password, isActive, role, now, now, deletedAt);

        return user;
    }

    /**
     * Static factory method to create a user with specific attributes.
     *
     * @param userID The unique identifier for the user.
     * @param aName The name of the user.
     * @param anEmail The email of the user.
     * @param aPassword The password of the user.
     * @param isActive The active status of the user.
     * @param role The role of the user.
     * @param createdAt The timestamp when the user was created.
     * @param updatedAt The timestamp when the user was last updated.
     * @param deletedAt The timestamp when the user was deleted.
     * @return A User instance.
     */
    public static User with(final UserID userID, final String aName, final String anEmail, final String aPassword, final boolean isActive, final UserRole role, final Instant createdAt, final Instant updatedAt, final Instant deletedAt ) {
        return new User(userID, aName, Email.newEmail(anEmail),Password.with(aPassword),isActive, role, createdAt, updatedAt, deletedAt);
    }

    /**
     * Method to activate a user. It sets the 'deletedAt' field to null,
     * 'active' field to true, and updates the 'updatedAt' field.
     *
     * @return The current User instance.
     */
    public User active() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();
        return this;
    }

    /**
     * Method to deactivate a user. It sets the 'deletedAt' field to the current time,
     * 'active' field to false, and updates the 'updatedAt' field.
     *
     * @return The current User instance.
     */
    public User deactive() {
        if (getDeletedAt() == null) {
            this.deletedAt = Instant.now();
        }
        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

    /**
     * Updates the user properties.
     *
     * @param aName The new name of the user.
     * @param anEmail The new email of the user.
     * @param aPassword The new password of the user.
     * @param isActive The new active status of the user.
     * @param aRole The new role of the user.
     * @return The current User instance.
     */
    public User update(final String aName, final Email anEmail, final Password aPassword, final boolean isActive, final UserRole aRole) {
        if (isActive) {
            active();
        } else {
            deactive();
        }
        this.name = aName;
        this.email = anEmail;
        this.password = aPassword;
        this.updatedAt = Instant.now();
        return this;
    }

    /**
     * Creates and returns a copy of this user.
     *
     * @return A clone of this user.
     */
    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    // Getters for the user properties

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public void hashedPasswordDefine(final Password password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public UserRole getRole() {
        return role;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}

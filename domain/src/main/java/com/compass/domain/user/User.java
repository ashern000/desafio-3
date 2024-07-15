package com.compass.domain.user;

import com.compass.domain.AggregateRoot;
import com.compass.domain.product.Product;

import java.time.Instant;
import java.util.Objects;

public class User extends AggregateRoot<UserID> implements Cloneable {

    private String name;
    private Email email;
    private Password password;
    private boolean active;
    private UserRole role;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    /**
     * Constructs a new AggregateRoot with the specified unique identifier.
     *
     * @param userID The unique identifier for the aggregate root.
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
        this.deletedAt = Objects.requireNonNull(aDeletedAt);

    }

    public static User newUser(final String aName, final Email anEmail, final Password aPassword, final boolean isActive, final UserRole role) {
        final UserID anId = UserID.unique();
        final Instant now = Instant.now();
        final Instant deletedAt = isActive ? null : now;
        return new User(anId,aName, anEmail, aPassword, isActive,role, now, now, deletedAt);
    }

    public User active() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();
        return this;
    }

    public User deactive() {
        if (getDeletedAt() == null) {
            this.deletedAt = Instant.now();
        }
        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;
        return getName().equals(user.getName()) && getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getEmail().hashCode();
        return result;
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
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

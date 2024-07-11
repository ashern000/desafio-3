package com.compass.domain;

import java.util.Objects;

/**
 * An abstract Entity class that represents a domain entity.
 * This class provides a structure for entities with a unique identifier.
 *
 * @param <ID> The type of the unique identifier for the entity.
 */
public abstract class Entity<ID extends Identifier> {

    // The unique identifier for the entity
    protected ID id;

    /**
     * Constructs a new Entity with the specified unique identifier.
     *
     * @param id The unique identifier for the entity.
     */
    protected Entity(final ID id) {
        this.id = Objects.requireNonNull(id, "'id' should be not null");
    }

    /**
     * Returns the unique identifier for the entity.
     *
     * @return The unique identifier for the entity.
     */
    public ID getId() { return id; }

    /**
     * Checks if this entity is equal to another object.
     * The two objects are equal if they are of the same class and have the same unique identifier.
     *
     * @param o The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Entity<?> entity = (Entity<?>) o;
        return Objects.equals(getId(), entity.getId());
    }

    /**
     * Returns the hash code for the entity.
     * The hash code is based on the unique identifier.
     *
     * @return The hash code for the entity.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}

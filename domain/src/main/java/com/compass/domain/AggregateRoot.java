package com.compass.domain;

/**
 * An abstract AggregateRoot class that extends the Entity class.
 * This class provides a structure for aggregate roots in the domain.
 * An aggregate root is an entity in the domain model that is responsible for maintaining the consistency of changes to entities within its aggregate.
 *
 * @param <ID> The type of the unique identifier for the aggregate root.
 */
public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID>{

    /**
     * Constructs a new AggregateRoot with the specified unique identifier.
     *
     * @param id The unique identifier for the aggregate root.
     */
    protected AggregateRoot(final ID id) {
        super(id);
    }
}

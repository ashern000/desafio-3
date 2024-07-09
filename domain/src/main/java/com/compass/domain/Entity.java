package com.compass.domain;

import java.util.Objects;

public abstract class Entity<ID extends Identifier> {

    protected ID id;

    protected Entity(final ID id) {
        this.id = Objects.requireNonNull(id, "'id' should be not null");
    }

    public ID getId() { return id; }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Entity<?> entity = (Entity<?>) o;
        return Objects.equals(getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}

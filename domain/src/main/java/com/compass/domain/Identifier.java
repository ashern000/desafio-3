package com.compass.domain;

/**
 * An abstract Identifier class that extends the ValueObject class.
 * This class provides a structure for identifiers with a value.
 */
public abstract class Identifier extends ValueObject{

    /**
     * Returns the value of the identifier.
     * This method is abstract and should be implemented by subclasses.
     *
     * @return The value of the identifier.
     */
    public abstract String getValue();
}

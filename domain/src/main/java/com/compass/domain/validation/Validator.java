package com.compass.domain.validation;

/**
 * A Validator is an abstract class that provides a structure for implementing validation logic.
 * It uses a ValidationHandler to handle validation errors.
 */
public abstract class Validator {

    // The ValidationHandler used to handle validation errors
    private final ValidationHandler handler;

    /**
     * Constructs a new Validator with the specified ValidationHandler.
     *
     * @param aHandler The ValidationHandler used to handle validation errors.
     */
    protected Validator(final ValidationHandler aHandler) {
        this.handler = aHandler;
    }

    /**
     * Validates the object. This method is abstract and should be implemented by subclasses.
     */
    public abstract void validate();

    /**
     * Returns the ValidationHandler used by this Validator.
     *
     * @return The ValidationHandler used by this Validator.
     */
    protected ValidationHandler validationHandler() {
        return this.handler;
    }
}


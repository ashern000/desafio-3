package com.compass.domain.validation.handler;

import com.compass.domain.exceptions.DomainException;
import com.compass.domain.validation.Error;
import com.compass.domain.validation.ValidationHandler;

import java.util.List;

/**
 * A ThrowsValidationHandler class that implements the ValidationHandler interface.
 * This class throws a DomainException when an error is appended or when a validation fails.
 */
public class ThrowsValidationHandler implements ValidationHandler {

    /**
     * Appends an error and throws a DomainException with the error.
     *
     * @param anError The error to append.
     * @return Does not return a value because a DomainException is thrown.
     */
    @Override
    public ValidationHandler append(Error anError) {
        throw DomainException.with(anError);
    }

    /**
     * Appends all errors from another ValidationHandler and throws a DomainException with the errors.
     *
     * @param aHandler The ValidationHandler whose errors to append.
     * @return Does not return a value because a DomainException is thrown.
     */
    @Override
    public ValidationHandler append(ValidationHandler aHandler) {
        throw DomainException.with(aHandler.getErrors());
    }

    /**
     * Validates a Validation object and returns the result.
     * If the validation throws an exception, a DomainException is thrown with the error message.
     *
     * @param aValidation The Validation object to validate.
     * @return The result of the validation.
     */
    @Override
    public <T> T validate(Validation<T> aValidation) {
        try {
            return aValidation.validate();
        } catch (final Exception ex) {
            throw DomainException.with(new Error(ex.getMessage()));
        }
    }

    /**
     * Returns an empty list of errors.
     *
     * @return An empty list of errors.
     */
    @Override
    public List<Error> getErrors() {
        return List.of();
    }
}


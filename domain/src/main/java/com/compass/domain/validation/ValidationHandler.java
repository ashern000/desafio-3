package com.compass.domain.validation;

import java.util.List;

/**
 * A ValidationHandler interface that provides methods for handling validation errors.
 */
public interface ValidationHandler {

    /**
     * Appends an error to the list of errors.
     *
     * @param anError The error to append.
     * @return The current ValidationHandler instance.
     */
    ValidationHandler append(Error anError);

    /**
     * Appends all errors from another ValidationHandler.
     *
     * @param aHandler The ValidationHandler whose errors to append.
     * @return The current ValidationHandler instance.
     */
    ValidationHandler append(ValidationHandler aHandler);

    /**
     * Validates a Validation object and returns the result.
     *
     * @param aValidation The Validation object to validate.
     * @return The result of the validation.
     */
    <T> T validate(Validation<T> aValidation);

    /**
     * Returns the list of errors.
     *
     * @return The list of errors.
     */
    List<Error> getErrors();

    /**
     * Checks if there are any errors.
     *
     * @return true if there are errors, false otherwise.
     */
    default boolean hasErrors() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    /**
     * Returns the first error in the list of errors.
     *
     * @return The first error if there are errors, null otherwise.
     */
    default Error firstError() {
        if (hasErrors()) {
            return getErrors().get(0);
        } else {
            return null;
        }
    }

    /**
     * A Validation interface that provides a method for validation.
     */
    interface Validation<T> {
        /**
         * Validates and returns the result.
         *
         * @return The result of the validation.
         */
        T validate();
    }

}

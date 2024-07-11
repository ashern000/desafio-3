package com.compass.domain.validation.handler;

import com.compass.domain.exceptions.DomainException;
import com.compass.domain.validation.Error;
import com.compass.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * A Notification class that implements the ValidationHandler interface.
 * This class collects validation errors in a list and provides methods to create a Notification instance.
 */
public class Notification implements ValidationHandler {

    // The list of validation errors
    private final List<Error> errors;

    /**
     * Private constructor for the Notification class.
     *
     * @param errors The list of validation errors.
     */
    private Notification(List<Error> errors) {
        this.errors = errors;
    }

    /**
     * Static factory method to create a new Notification instance with an empty list of errors.
     *
     * @return A new Notification instance.
     */
    public static Notification create() { return new Notification(new ArrayList<>());};

    /**
     * Static factory method to create a new Notification instance with a single error from a Throwable.
     *
     * @param t The Throwable from which to create an error.
     * @return A new Notification instance.
     */
    public static Notification create(final Throwable t) {
        return create(new Error(t.getMessage()));
    }

    /**
     * Static factory method to create a new Notification instance with a single error.
     *
     * @param anError The error to add to the Notification.
     * @return A new Notification instance.
     */
    public static Notification create(final Error anError) {
        return new Notification(new ArrayList<>()).append(anError);
    }

    /**
     * Appends an error to the list of errors.
     *
     * @param anError The error to append.
     * @return The current Notification instance.
     */
    @Override
    public Notification append(Error anError) {
        this.errors.add(anError);
        return this;
    }

    /**
     * Appends all errors from another ValidationHandler to the list of errors.
     *
     * @param aHandler The ValidationHandler whose errors to append.
     * @return The current Notification instance.
     */
    @Override
    public Notification append(ValidationHandler aHandler) {
        this.errors.addAll(aHandler.getErrors());
        return this;
    }

    /**
     * Validates a Validation object and returns the result.
     * If the validation throws a DomainException, the errors are added to the list of errors.
     * If the validation throws any other Throwable, an error with the Throwable's message is added to the list of errors.
     *
     * @param aValidation The Validation object to validate.
     * @return The result of the validation, or null if the validation threw an exception.
     */
    @Override
    public <T> T validate(Validation<T> aValidation) {
        try {
            return aValidation.validate();
        } catch (DomainException ex) {
            this.errors.addAll(ex.getErrors());
        } catch (Throwable t) {
            this.errors.add(new Error(t.getMessage()));
        }
        return null;
    }

    /**
     * Returns the list of errors.
     *
     * @return The list of errors.
     */
    @Override
    public List<Error> getErrors() {
        return this.errors;
    }
}

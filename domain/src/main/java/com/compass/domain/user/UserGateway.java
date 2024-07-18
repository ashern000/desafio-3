package com.compass.domain.user;

import java.util.Optional;

/**
 * UserGateway interface provides the contract for user data access and manipulation.
 */
public interface UserGateway {

    /**
     * Method to create a new user.
     *
     * @param user The user to be created.
     * @return The created user.
     */
    User create(User user);

    /**
     * Method to delete a user.
     *
     * @param anId The unique identifier of the user to be deleted.
     */
    void delete(UserID anId);

    /**
     * Method to find a user by their email.
     *
     * @param email The email of the user to be found.
     * @return An Optional that can contain the User if found.
     */
    Optional<User> findByEmail(Email email);

    /**
     * Method to update a user.
     *
     * @param user The user with updated information.
     * @return The updated user.
     */
    User update(User user);
}


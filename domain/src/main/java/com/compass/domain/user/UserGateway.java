package com.compass.domain.user;

import java.util.Optional;

public interface UserGateway {
    User create(User user);
    void delete(UserID anId);
    Optional<User> findByEmail(Email email);
    void update(User user);
}

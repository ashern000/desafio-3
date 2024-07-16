package com.compass.domain.user;

public interface UserGateway {
    User create(User user);
    void delete(UserID anId);
    User findByEmail(Email email);
    void update(User user);
}

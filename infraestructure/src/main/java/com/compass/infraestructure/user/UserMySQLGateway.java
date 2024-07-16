package com.compass.infraestructure.user;

import com.compass.domain.user.Email;
import com.compass.domain.user.User;
import com.compass.domain.user.UserGateway;
import com.compass.domain.user.UserID;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserMySQLGateway implements UserGateway {
    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public void delete(UserID anId) {

    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return Optional.empty();
    }

    @Override
    public void update(User user) {

    }
}

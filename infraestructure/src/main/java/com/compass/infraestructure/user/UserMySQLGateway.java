package com.compass.infraestructure.user;

import com.compass.domain.user.Email;
import com.compass.domain.user.User;
import com.compass.domain.user.UserGateway;
import com.compass.domain.user.UserID;
import com.compass.infraestructure.user.persistence.UserJpaEntity;
import com.compass.infraestructure.user.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserMySQLGateway implements UserGateway {
    private final UserRepository userRepository;

    public UserMySQLGateway(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        return this.userRepository.save(UserJpaEntity.from(user)).toDomain();
    }

    @Override
    public void delete(UserID anId) {

    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return this.userRepository.findByEmail(email.getValue()).map(UserJpaEntity::toDomain);
    }

    @Override
    public void update(User user) {

    }
}

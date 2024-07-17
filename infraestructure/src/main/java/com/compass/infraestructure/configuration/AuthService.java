package com.compass.infraestructure.configuration;

import com.compass.domain.user.Email;
import com.compass.domain.user.UserGateway;
import com.compass.infraestructure.user.persistence.UserJpaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserGateway userGateway;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userGateway.findByEmail(Email.newEmail(email)).map(user -> (UserDetails) UserJpaEntity.from(user)).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
    }
}

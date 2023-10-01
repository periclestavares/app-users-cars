package com.veiculos.api.infra.authentication;

import com.veiculos.api.model.User;
import com.veiculos.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service for the authentication login using spring security
 * @author Pericles Tavares
 */
@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }

    public void updateLastLogged(User user) throws UsernameNotFoundException {
        user.setLastLogged(LocalDateTime.now());
        repository.save(user);
    }
}

package com.veiculos.api.service;

import com.veiculos.api.infra.authentication.AuthenticationService;
import com.veiculos.api.model.User;
import com.veiculos.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for the AuthenticationService
 * @author Pericles Tavares
 *
 */
@SpringBootTest
public class AuthenticationServiceTest {
    @Autowired
    private AuthenticationService service;

    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    public void insertUser() {
        user = userRepository.save(new User(null, "First Name", "Last Name", "email@email.com", LocalDate.now(), "login", "senha", "1234566", null, null));
    }

    @Test
    public void testLoadByUserName() {
        UserDetails userDetails = service.loadUserByUsername("login");
        assertThat(userDetails.getUsername()).isEqualTo("login");
        assertThat(userDetails.getPassword()).isEqualTo("senha");
    }

    @Test
    public void testUpdateLastLogged() {
        service.updateLastLogged(user);
        User userDb = userRepository.findById(user.getId()).get();
        assertThat(userDb.getLastLogged()).isNotNull();
    }
}

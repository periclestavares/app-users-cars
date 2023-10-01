package com.veiculos.api.service;

import com.veiculos.api.infra.authentication.service.JWTAuthenticationService;
import com.veiculos.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for the JWTAuthenticationService
 * @author Pericles Tavares
 */
@SpringBootTest
public class JWTAuthenticationServiceTest {
    @Autowired
    private JWTAuthenticationService service;

    @Test
    public void testVerifyToken() {
        User user = new User();
        user.setLogin("login");
        user.setId(1L);
        String token = service.generateToken(user);
        assertThat(token).isNotNull();
        String id = service.verifyToken(token);
        assertThat(id).isEqualTo("login");
    }
}

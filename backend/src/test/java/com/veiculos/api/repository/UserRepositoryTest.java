package com.veiculos.api.repository;

import com.veiculos.api.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for the User Repository using Data JPA Test
 *
 * @author Péricles Tavares
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Autowired
    TestEntityManager em;

    @BeforeEach
    public void insertUser() {
        em.persist(new User(null, "First Name", "Last Name", "email@email.com", LocalDate.now(), "login", "senha", "1234566", null, null));
        em.persist(new User(null, "Second User", "Last Name", "secondEmail@email.com", LocalDate.now(), "secondLogin", "senha", "1234566", null, null));
    }

    @Test
    public void testFindByLoginReturningUser() {
        assertThat(repository.findByLogin("login")).isNotNull();
    }

    @Test
    public void testFindByLoginReturningNull() {
        assertThat(repository.findByLogin("login2")).isNull();
    }

    @Test
    public void testExistsByLoginReturningTrue() {
        assertThat(repository.existsByLogin("login")).isTrue();
    }

    @Test
    public void testExistsByLoginReturningFalse() {
        assertThat(repository.existsByLogin("login2")).isFalse();
    }

    @Test
    public void testExistsByEmailReturningTrue() {
        assertThat(repository.existsByEmail("email@email.com")).isTrue();
    }

    @Test
    public void testExistsByEmailReturningFalse() {
        assertThat(repository.existsByEmail("email@email.com.br")).isFalse();
    }

    @Test
    public void testExistsByLoginAndIdNotReturningTrue() {
        User user1 = repository.findByLogin("login");
        User user2 = repository.findByLogin("secondLogin");
        assertThat(repository.existsByLoginAndIdNot("login", user2.getId())).isTrue();
        assertThat(repository.existsByLoginAndIdNot("secondLogin", user1.getId())).isTrue();
    }

    @Test
    public void testExistsByLoginAndIdNotReturningFalse() {
        User user1 = repository.findByLogin("login");
        User user2 = repository.findByLogin("secondLogin");
        assertThat(repository.existsByLoginAndIdNot("", user1.getId())).isFalse();
        assertThat(repository.existsByLoginAndIdNot("secondLogin", user2.getId())).isFalse();
        assertThat(repository.existsByLoginAndIdNot("anotherLogin", user2.getId())).isFalse();
    }

    @Test
    public void testExistsByEmailAndIdNotReturningTrue() {
        User user1 = repository.findByLogin("login");
        User user2 = repository.findByLogin("secondLogin");
        assertThat(repository.existsByEmailAndIdNot("email@email.com", user2.getId())).isTrue();
        assertThat(repository.existsByEmailAndIdNot("secondEmail@email.com", user1.getId())).isTrue();
    }

    @Test
    public void testExistsByEmailAndIdNotReturningFalse() {
        User user1 = repository.findByLogin("login");
        User user2 = repository.findByLogin("secondLogin");
        assertThat(repository.existsByEmailAndIdNot("email@email.com", user1.getId())).isFalse();
        assertThat(repository.existsByEmailAndIdNot("secondEmail@email.com", user2.getId())).isFalse();
        assertThat(repository.existsByEmailAndIdNot("anotherEmail@email.com", user2.getId())).isFalse();
    }

}

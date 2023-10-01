package com.veiculos.api.repository;

import com.veiculos.api.model.Car;
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
 * Test for the Car Repository using Data JPA Test
 *
 * @author Péricles Tavares
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CarRepositoryTest {

    @Autowired
    CarRepository repository;

    @Autowired
    TestEntityManager em;
    private User user1;
    private User user2;
    private User user3;

    private Car car1;
    private Car car2;

    @BeforeEach
    public void insertCar() {
        this.user1 = em.persist(new User(null, "First Name", "Last Name", "email@email.com", LocalDate.now(), "login", "senha", "1234566", null, null));
        this.user2 = em.persist(new User(null, "Second User", "Last Name", "secondEmail@email.com", LocalDate.now(), "secondLogin", "senha", "1234566", null, null));
        this.user3 = em.persist(new User(null, "Third User", "Last Name", "thirdEmail@email.com", LocalDate.now(), "thirdLogin", "senha", "1234566", null, null));
        this.car1 = em.persist(new Car(null, 2023, "123456", "model", "color", user1));
        this.car2 = em.persist(new Car(null, 2022, "654321", "model", "color", user2));
    }

    @Test
    public void testFindAllByUserIdReturningCar() {
        assertThat(repository.findAllByUserId(user1.getId()).size()).isEqualTo(1);
    }

    @Test
    public void testFindAllByUserIdReturningNull() {
        assertThat(repository.findAllByUserId(user3.getId()).size()).isZero();
    }

    @Test
    public void testFindByIdByUserIdReturningCar() {
        assertThat(repository.findByIdAndUserId(car1.getId(), user1.getId()).isPresent()).isTrue();
    }

    @Test
    public void testFindByIdByUserIdReturningNull() {
        assertThat(repository.findByIdAndUserId(car1.getId(), user2.getId()).isPresent()).isFalse();
    }

    @Test
    public void testExistsByLicensePlateReturningTrue() {
        assertThat(repository.existsByLicensePlate("123456")).isTrue();
    }

    @Test
    public void testExistsByLicensePlateReturningFalse() {
        assertThat(repository.existsByLicensePlate("12345657755")).isFalse();
    }

    @Test
    public void testExistsByLicensePlateAndIdNotReturningTrue() {
        assertThat(repository.existsByLicensePlateAndIdNot("123456", car2.getId())).isTrue();
        assertThat(repository.existsByLicensePlateAndIdNot("654321", car1.getId())).isTrue();
    }

    @Test
    public void testExistsByLicensePlateAndIdNotReturningFalse() {
        assertThat(repository.existsByLicensePlateAndIdNot("123456", car1.getId())).isFalse();
        assertThat(repository.existsByLicensePlateAndIdNot("654321", car2.getId())).isFalse();
        assertThat(repository.existsByLicensePlateAndIdNot("6543210", car2.getId())).isFalse();
    }
}

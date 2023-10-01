package com.veiculos.api.repository;

import com.veiculos.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the User entity
 * @author Péricles Tavares
 */
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    boolean existsByLoginAndIdNot(String login, Long id);

    boolean existsByEmailAndIdNot(String email, Long login);

    User findByLogin(String login);
}

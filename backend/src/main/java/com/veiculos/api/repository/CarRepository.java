package com.veiculos.api.repository;

import com.veiculos.api.model.Car;
import com.veiculos.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for the Car entity
 * @author Péricles Tavares
 */
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAllByUserId(Long userId);

    Optional<Car> findByIdAndUserId(Long id, Long userId);

    boolean existsByLicensePlate(String licensePlate);

    boolean existsByLicensePlateAndIdNot(String licensePlate, Long id);
}

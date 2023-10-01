package com.veiculos.api.service;

import com.veiculos.api.dto.CarDTO;
import com.veiculos.api.infra.exception.FieldExistsException;
import com.veiculos.api.model.Car;
import com.veiculos.api.model.User;
import com.veiculos.api.repository.CarRepository;
import com.veiculos.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service with business logic for the Car Entity
 *
 * @author Péricles Tavares
 */
@Service
public class CarService {
    @Autowired
    private CarRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    public List<CarDTO> getAll() {
        return repository
                .findAllByUserId(getLoggedUser().getId())
                .stream()
                .map(u -> modelMapper.map(u, CarDTO.class))
                .collect(Collectors.toList());
    }

    public CarDTO getById(Long id) {
        Car car = repository.findByIdAndUserId(id, getLoggedUser().getId())
                .orElseThrow(() -> new EntityNotFoundException());

        return modelMapper.map(car, CarDTO.class);
    }

    public CarDTO add(CarDTO dto) throws FieldExistsException {
        Car car = modelMapper.map(dto, Car.class);

        if (repository.existsByLicensePlate(car.getLicensePlate())) {
            throw new FieldExistsException(Map.of(4L,"License Plate"));
        }
        car.setUser(getLoggedUser());
        repository.save(car);

        return modelMapper.map(car, CarDTO.class);
    }

    public CarDTO update(Long id, CarDTO dto) throws FieldExistsException {
        Car car = modelMapper.map(dto, Car.class);
        car.setId(id);
        if (repository.existsByLicensePlateAndIdNot(car.getLicensePlate(), id)) {
            throw new FieldExistsException(Map.of(4L,"License Plate"));
        }
        car.setUser(getLoggedUser());
        car = repository.save(car);
        return modelMapper.map(car, CarDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private User getLoggedUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

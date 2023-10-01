package com.veiculos.api.controller;

import com.veiculos.api.dto.CarDTO;
import com.veiculos.api.infra.exception.FieldExistsException;
import com.veiculos.api.service.CarService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controller for the Car Entity
 * @author Pericles Tavares
 */
@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService service;

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAll() {

        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getById(@PathVariable @NotNull Long id) {
        CarDTO dto = service.getById(id);

        return ResponseEntity.ok(dto);
    }


    @PostMapping
    public ResponseEntity<CarDTO> add(@RequestBody @Valid CarDTO dto, UriComponentsBuilder uriBuilder) throws FieldExistsException {
        CarDTO car = service.add(dto);
        URI endereco = uriBuilder.path("/cars/{id}").buildAndExpand(car.getId()).toUri();

        return ResponseEntity.created(endereco).body(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> update(@PathVariable @NotNull Long id, @RequestBody @Valid CarDTO dto) throws FieldExistsException {
        CarDTO car = service.update(id, dto);
        return ResponseEntity.ok(car);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CarDTO> remover(@PathVariable @NotNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

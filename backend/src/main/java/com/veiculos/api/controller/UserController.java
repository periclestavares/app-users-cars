package com.veiculos.api.controller;

import com.veiculos.api.dto.UserDTO;
import com.veiculos.api.infra.exception.FieldExistsException;
import com.veiculos.api.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controller for the User Entity
 * @author Pericles Tavares
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable @NotNull Long id) {
        UserDTO dto = service.getById(id);

        return ResponseEntity.ok(dto);
    }


    @PostMapping
    public ResponseEntity<UserDTO> add(@RequestBody @Valid UserDTO dto, UriComponentsBuilder uriBuilder) throws FieldExistsException {
        UserDTO user = service.add(dto);
        URI url = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(url).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable @NotNull Long id, @RequestBody @Valid UserDTO dto) throws FieldExistsException {
        UserDTO user = service.update(id, dto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> remover(@PathVariable @NotNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

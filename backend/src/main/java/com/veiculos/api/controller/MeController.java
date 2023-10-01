package com.veiculos.api.controller;

import com.veiculos.api.dto.UserDTO;
import com.veiculos.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the me route returning the current logged user.
 * @author Pericles Tavares
 */
@RestController
@RequestMapping("/api/me")
public class MeController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<UserDTO> getLoggedUser() {
        return ResponseEntity.ok(service.getLoggedUser());
    }
}

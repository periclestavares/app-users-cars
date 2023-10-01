package com.veiculos.api.controller;

import com.veiculos.api.dto.SignInDTO;
import com.veiculos.api.infra.authentication.service.AuthenticationService;
import com.veiculos.api.infra.authentication.service.JWTAuthenticationService;
import com.veiculos.api.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller for the SignIn route
 * @author Pericles Tavares
 */
@RestController
@RequestMapping("/api/signin")
public class SignInController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JWTAuthenticationService tokenService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity signIn(@RequestBody @Valid SignInDTO signInDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(signInDTO.getLogin(), signInDTO.getPassword());
        var authentication = manager.authenticate(authenticationToken);

        User user = (User) authentication.getPrincipal();
        authenticationService.updateLastLogged(user);

        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(Map.of("token", tokenJWT));
    }

}

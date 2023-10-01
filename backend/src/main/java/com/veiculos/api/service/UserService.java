package com.veiculos.api.service;

import com.veiculos.api.dto.UserDTO;
import com.veiculos.api.infra.exception.FieldExistsException;
import com.veiculos.api.model.User;
import com.veiculos.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Service with business logic for the User Entity
 *
 * @author Péricles Tavares
 */
@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder encoder;


    public List<UserDTO> getAll() {
        return repository
                .findAll()
                .stream()
                .map(u -> modelMapper.map(u, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO getById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO add(UserDTO dto) throws FieldExistsException {
        User user = modelMapper.map(dto, User.class);

        Map<Long, String> fieldsExists = new HashMap<>();
        if (repository.existsByEmail(user.getEmail())) {
            fieldsExists.put(2L, "Email");
        }
        if (repository.existsByLogin(user.getLogin())) {
            fieldsExists.put(3L, "Login");
        }
        if (!fieldsExists.isEmpty()) {
            throw new FieldExistsException(fieldsExists);
        }
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);

        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO update(Long id, UserDTO dto) throws FieldExistsException {
        User user = modelMapper.map(dto, User.class);

        Map<Long, String> fieldsExists = new HashMap<>();
        if (repository.existsByEmailAndIdNot(user.getEmail(), id)) {
            fieldsExists.put(2L, "Email");
        }
        if (repository.existsByLoginAndIdNot(user.getLogin(), id)) {
            fieldsExists.put(3L, "Login");
        }
        if (!fieldsExists.isEmpty()) {
            throw new FieldExistsException(fieldsExists);
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        repository.save(user);

        return modelMapper.map(user, UserDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public UserDTO getLoggedUser() {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User user = repository.findById(userId).get();
        return modelMapper.map(user, UserDTO.class);
    }
}

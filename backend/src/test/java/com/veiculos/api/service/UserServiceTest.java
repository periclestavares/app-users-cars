package com.veiculos.api.service;

import com.veiculos.api.dto.UserDTO;
import com.veiculos.api.infra.exception.FieldExistsException;
import com.veiculos.api.model.User;
import com.veiculos.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService service;

    @MockBean
    private UserRepository userRepository;

    private static User generateUser() {
        return new User(null, "First Name", "Last Name", "email@email.com", LocalDate.now(), "login", "password", "1234566", LocalDateTime.now(), null);
    }
    private static void validateUserDTO(UserDTO userDTO, User user) {
        assertThat(userDTO.getLogin()).isEqualTo(user.getLogin());
        assertThat(userDTO.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userDTO.getLastName()).isEqualTo(user.getLastName());
        assertThat(userDTO.getEmail()).isEqualTo(user.getEmail());
        assertThat(userDTO.getBirthday()).isEqualTo(user.getBirthday());
        assertThat(userDTO.getLogin()).isEqualTo(user.getLogin());
        assertThat(userDTO.getPassword()).isEqualTo(user.getPassword());
        assertThat(userDTO.getPhone()).isEqualTo(user.getPhone());
        assertThat(userDTO.getCreatedAt()).isEqualTo(user.getCreatedAt());
        assertThat(userDTO.getLastLogged()).isEqualTo(user.getLastLogged());
    }

    @Test
    public void testGetAll() {
        User user = generateUser();
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        List<UserDTO> page = service.getAll();
        assertThat(page.size()).isEqualTo(1);
        page.forEach(userDTO -> {
            validateUserDTO(userDTO, user);
        });
    }


    @Test
    public void testGetById() {
        User user = generateUser();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDTO userDTO = service.getById(1L);
        validateUserDTO(userDTO, user);
    }

    @Test
    public void testAdd() throws FieldExistsException {
        User user = generateUser();
        UserDTO userDTO = new UserDTO(null, "First Name", "Last Name", "email@email.com", LocalDate.now(), "login", "password", "1234566", LocalDateTime.now(), null);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDTO addedUserDTO = service.add(userDTO);
        validateUserDTO(addedUserDTO, user);
    }

    @Test
    public void testAddWithExistingEmail() {
        UserDTO userDTO = new UserDTO(null, "First Name", "Last Name", "email@email.com", LocalDate.now(), "login", "password", "1234566", LocalDateTime.now(), null);
        Mockito.when(userRepository.existsByEmail(Mockito.any())).thenReturn(true);
        FieldExistsException e = catchThrowableOfType(() -> service.add(userDTO), FieldExistsException.class);
        assertThat(e.getFields().get(2L)).isEqualTo("Email");
    }

    @Test
    public void testAddWithExistingLogin() {
        UserDTO userDTO = new UserDTO(null, "First Name", "Last Name", "email@email.com", LocalDate.now(), "login", "password", "1234566", LocalDateTime.now(), null);
        Mockito.when(userRepository.existsByLogin(Mockito.any())).thenReturn(true);
        FieldExistsException e = catchThrowableOfType(() -> service.add(userDTO), FieldExistsException.class);
        assertThat(e.getFields().get(3L)).isEqualTo("Login");
    }

    @Test
    public void testUpdate() throws FieldExistsException {
        User user = generateUser();
        user.setId(1L);
        UserDTO userDTO = new UserDTO(null, "First Name", "Last Name", "email@email.com", LocalDate.now(), "login", "password", "1234566", LocalDateTime.now(), null);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDTO updatedUserDTO = service.update(1L, userDTO);
        validateUserDTO(updatedUserDTO, user);
    }

    @Test
    public void testUpdateWithExistingEmail() {
        UserDTO userDTO = new UserDTO(null, "First Name", "Last Name", "email@email.com", LocalDate.now(), "login", "password", "1234566", LocalDateTime.now(), null);
        Mockito.when(userRepository.existsByEmailAndIdNot(Mockito.any(), Mockito.any())).thenReturn(true);
        FieldExistsException e = catchThrowableOfType(() -> service.update(1L, userDTO), FieldExistsException.class);
        assertThat(e.getFields().get(2L)).isEqualTo("Email");
    }

    @Test
    public void testUpdateWithExistingLogin() {
        UserDTO userDTO = new UserDTO(null, "First Name", "Last Name", "email@email.com", LocalDate.now(), "login", "password", "1234566", LocalDateTime.now(), null);
        Mockito.when(userRepository.existsByLoginAndIdNot(Mockito.any(), Mockito.any())).thenReturn(true);
        FieldExistsException e = catchThrowableOfType(() -> service.update(1L, userDTO), FieldExistsException.class);
        assertThat(e.getFields().get(3L)).isEqualTo("Login");
    }

    @Test
    public void testDelete()  {
        service.delete(1L);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Mockito.any());
    }
}

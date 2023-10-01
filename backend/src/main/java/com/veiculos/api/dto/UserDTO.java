package com.veiculos.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for the user entity.
 * @author Péricles Tavares
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDTO {

    private Long id;

    @NotBlank(message = "{missing.field}")
    @Size(max = 250, message = "{invalid.field}")
    private String firstName;

    @NotBlank(message = "{missing.field}")
    @Size(max = 250, message = "{invalid.field}")
    private String lastName;

    @NotNull(message = "{missing.field}")
    @Email(message = "{invalid.field}")
    @Size(max = 250, message = "{invalid.field}")
    private String email;

    @NotNull(message = "{missing.field}")
    private LocalDate birthday;

    @NotBlank(message = "{missing.field}")
    @Size(max = 250, message = "{invalid.field}")
    private String login;

    @NotBlank(message = "{missing.field}")
    @Size(max = 250, message = "{invalid.field}")
    private String password;

    @NotBlank(message = "{missing.field}")
    @Size(max = 250, message = "{invalid.field}")
    private String phone;

    private LocalDateTime createdAt;

    private LocalDateTime lastLogged;
}

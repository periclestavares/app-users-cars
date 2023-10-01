package com.veiculos.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for the SignIn route data.
 * @author Pericles Tavares
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInDTO {
    @NotBlank(message = "{missing.field}")
    private String login;

    @NotBlank(message = "{missing.field}")
    private String password;
}

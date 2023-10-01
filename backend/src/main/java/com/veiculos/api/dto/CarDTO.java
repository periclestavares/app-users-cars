package com.veiculos.api.dto;

import com.veiculos.api.model.Car;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO for the car entity.
 * @author Péricles Tavares
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {
    private Long id;

    @NotNull(message = "{missing.field}")
    @Positive(message = "{invalid.field}")
    private Integer carYear;

    @NotBlank(message = "{missing.field}")
    @Size(max = 250, message = "{invalid.field}")
    private String licensePlate;

    @NotBlank(message = "{missing.field}")
    @Size(max = 250, message = "{invalid.field}")
    private String model;

    @NotBlank(message = "{missing.field}")
    @Size(max = 250, message = "{invalid.field}")
    private String color;
}

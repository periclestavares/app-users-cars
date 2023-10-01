package com.veiculos.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for the error of the app
 * @author Péricles Tavares
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorDTO {
    private String message;
    private String errorCode;
}

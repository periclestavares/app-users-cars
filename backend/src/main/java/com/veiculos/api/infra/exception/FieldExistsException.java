package com.veiculos.api.infra.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Exception for fields that already exists on the app.
 * @author Péricles Tavares
 */
@Getter
@Setter
public class FieldExistsException extends Exception {
    private Map<Long, String> fields;

    public FieldExistsException(Map<Long, String> fields) {
        super("exist.field");
        this.fields = fields;
    }

}

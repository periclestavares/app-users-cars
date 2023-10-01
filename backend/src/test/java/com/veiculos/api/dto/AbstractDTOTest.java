package com.veiculos.api.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;

/**
 * Abstract class for common logic for DTO test
 * @author Pericles Tavares
 */
public abstract class AbstractDTOTest {
    protected static Validator validator;
    protected final String missingField = "{missing.field}";
    protected final String invalidField = "{invalid.field}";

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
}

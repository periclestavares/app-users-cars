package com.veiculos.api.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;

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

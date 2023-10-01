package com.veiculos.api.dto;

import jakarta.validation.ConstraintViolation;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for User DTO
 * @author Pericles Tavares
 */
public class CarDTOTest extends AbstractDTOTest {
    private static CarDTO generateValidDTO() {
        CarDTO dto = new CarDTO();
        dto.setCarYear(2023);
        dto.setLicensePlate("123456");
        dto.setModel("model");
        dto.setColor("color");
        return dto;
    }

    @Test
    public void testNoValidations() {
        CarDTO dto = generateValidDTO();
        Set<ConstraintViolation<CarDTO>> violations = validator.validate(dto);
        assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    public void testMissingYearValidation() {
        CarDTO dto = generateValidDTO();
        dto.setCarYear(null);
        Set<ConstraintViolation<CarDTO>> nullCheck = validator.validate(dto);
        assertThat(nullCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
    }

    @Test
    public void testPositiveYearValidation() {
        CarDTO dto = generateValidDTO();
        dto.setCarYear(-1);
        Set<ConstraintViolation<CarDTO>> violations = validator.validate(dto);
        assertThat(violations.iterator().next().getMessageTemplate()).isEqualTo(invalidField);
    }

    @Test
    public void testMissingLicensePlateValidation() {
        CarDTO dto = generateValidDTO();
        dto.setLicensePlate(null);
        Set<ConstraintViolation<CarDTO>> nullCheck = validator.validate(dto);
        assertThat(nullCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
        dto.setLicensePlate(" ");
        Set<ConstraintViolation<CarDTO>> emptyCheck = validator.validate(dto);
        assertThat(emptyCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
    }

    @Test
    public void testSizeLicensePlateValidation() {
        CarDTO dto = generateValidDTO();
        dto.setLicensePlate(RandomStringUtils.random(251));
        Set<ConstraintViolation<CarDTO>> violations = validator.validate(dto);
        assertThat(violations.iterator().next().getMessageTemplate()).isEqualTo(invalidField);
    }

    @Test
    public void testMissingModelValidation() {
        CarDTO dto = generateValidDTO();
        dto.setModel(null);
        Set<ConstraintViolation<CarDTO>> nullCheck = validator.validate(dto);
        assertThat(nullCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
        dto.setModel(" ");
        Set<ConstraintViolation<CarDTO>> emptyCheck = validator.validate(dto);
        assertThat(emptyCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
    }

    @Test
    public void testSizeModelValidation() {
        CarDTO dto = generateValidDTO();
        dto.setLicensePlate(RandomStringUtils.random(251));
        Set<ConstraintViolation<CarDTO>> violations = validator.validate(dto);
        assertThat(violations.iterator().next().getMessageTemplate()).isEqualTo(invalidField);
    }


    @Test
    public void testMissingColorValidation() {
        CarDTO dto = generateValidDTO();
        dto.setColor(null);
        Set<ConstraintViolation<CarDTO>> nullCheck = validator.validate(dto);
        assertThat(nullCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
        dto.setColor(" ");
        Set<ConstraintViolation<CarDTO>> emptyCheck = validator.validate(dto);
        assertThat(emptyCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
    }

    @Test
    public void testSizeColorValidation() {
        CarDTO dto = generateValidDTO();
        dto.setColor(RandomStringUtils.random(251));
        Set<ConstraintViolation<CarDTO>> violations = validator.validate(dto);
        assertThat(violations.iterator().next().getMessageTemplate()).isEqualTo(invalidField);
    }
}

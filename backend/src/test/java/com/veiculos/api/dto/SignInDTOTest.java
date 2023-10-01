package com.veiculos.api.dto;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for the SignInDTO
 * @author Pericles Tavares
 */
public class SignInDTOTest extends AbstractDTOTest {
    private static SignInDTO generateValidDTO() {
        SignInDTO dto = new SignInDTO();
        dto.setLogin("login");
        dto.setPassword("123456");
        return dto;
    }

    @Test
    public void testNoValidations(){
        SignInDTO dto = generateValidDTO();
        Set<ConstraintViolation<SignInDTO>> violations = validator.validate(dto);
        assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    public void testMissingLoginValidation(){
        SignInDTO dto = generateValidDTO();
        dto.setLogin(null);
        Set<ConstraintViolation<SignInDTO>> nullCheck = validator.validate(dto);
        assertThat(nullCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
        dto.setLogin(" ");
        Set<ConstraintViolation<SignInDTO>> emptyCheck = validator.validate(dto);
        assertThat(emptyCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
    }

    @Test
    public void testMissingPasswordValidation(){
        SignInDTO dto = generateValidDTO();
        dto.setPassword(null);
        Set<ConstraintViolation<SignInDTO>> nullCheck = validator.validate(dto);
        assertThat(nullCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
        dto.setPassword(" ");
        Set<ConstraintViolation<SignInDTO>> emptyCheck = validator.validate(dto);
        assertThat(emptyCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
    }
}

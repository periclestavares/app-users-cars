package com.veiculos.api.dto;

import jakarta.validation.ConstraintViolation;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDTOTest extends AbstractDTOTest {
    private static UserDTO generateValidDTO() {
        UserDTO dto = new UserDTO();
        dto.setFirstName("First Name");
        dto.setLastName("Last Name");
        dto.setEmail("email@email.com");
        dto.setBirthday(LocalDate.now());
        dto.setLogin("login");
        dto.setPassword("password");
        dto.setPhone("1234567");
        return dto;
    }

    @Test
    public void testNoValidations(){
        UserDTO dto = generateValidDTO();
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    public void testMissingFirstNameValidation(){
        UserDTO dto = generateValidDTO();
        dto.setFirstName(null);
        Set<ConstraintViolation<UserDTO>> nullCheck = validator.validate(dto);
        assertThat(nullCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
        dto.setFirstName(" ");
        Set<ConstraintViolation<UserDTO>> emptyCheck = validator.validate(dto);
        assertThat(emptyCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
    }

    @Test
    public void testSizeFirstNameValidation(){
        UserDTO dto = generateValidDTO();
        dto.setFirstName(RandomStringUtils.random(251));
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assertThat(violations.iterator().next().getMessageTemplate()).isEqualTo(invalidField);
    }

    @Test
    public void testMissingLastNameValidation(){
        UserDTO dto = generateValidDTO();
        dto.setLastName(null);
        Set<ConstraintViolation<UserDTO>> nullCheck = validator.validate(dto);
        assertThat(nullCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
        dto.setLastName(" ");
        Set<ConstraintViolation<UserDTO>> emptyCheck = validator.validate(dto);
        assertThat(emptyCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
    }

    @Test
    public void testSizeLastNameValidation(){
        UserDTO dto = generateValidDTO();
        dto.setLastName(RandomStringUtils.random(251));
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assertThat(violations.iterator().next().getMessageTemplate()).isEqualTo(invalidField);
    }

    @Test
    public void testMissingEmailValidation(){
        UserDTO dto = generateValidDTO();
        dto.setEmail(null);
        Set<ConstraintViolation<UserDTO>> nullCheck = validator.validate(dto);
        assertThat(nullCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
        dto.setEmail(" ");
        Set<ConstraintViolation<UserDTO>> emptyCheck = validator.validate(dto);
        assertThat(emptyCheck.iterator().next().getMessageTemplate()).isEqualTo(invalidField);
    }

    @Test
    public void testSizeEmailValidation(){
        UserDTO dto = generateValidDTO();
        dto.setEmail(RandomStringUtils.random(251));
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assertThat(violations.iterator().next().getMessageTemplate()).isEqualTo(invalidField);
    }

    @Test
    public void testValidEmailValidation(){
        UserDTO dto = generateValidDTO();
        dto.setEmail("email.email@");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assertThat(violations.iterator().next().getMessageTemplate()).isEqualTo(invalidField);
    }

    @Test
    public void testMissingBirthdayValidation(){
        UserDTO dto = generateValidDTO();
        dto.setBirthday(null);
        Set<ConstraintViolation<UserDTO>> nullCheck = validator.validate(dto);
        assertThat(nullCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
    }

    @Test
    public void testMissingLoginValidation(){
        UserDTO dto = generateValidDTO();
        dto.setLogin(null);
        Set<ConstraintViolation<UserDTO>> nullCheck = validator.validate(dto);
        assertThat(nullCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
        dto.setLogin(" ");
        Set<ConstraintViolation<UserDTO>> emptyCheck = validator.validate(dto);
        assertThat(emptyCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
    }

    @Test
    public void testSizeLoginValidation(){
        UserDTO dto = generateValidDTO();
        dto.setLogin(RandomStringUtils.random(251));
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assertThat(violations.iterator().next().getMessageTemplate()).isEqualTo(invalidField);
    }

    @Test
    public void testMissingPasswordValidation(){
        UserDTO dto = generateValidDTO();
        dto.setPassword(null);
        Set<ConstraintViolation<UserDTO>> nullCheck = validator.validate(dto);
        assertThat(nullCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
        dto.setPassword(" ");
        Set<ConstraintViolation<UserDTO>> emptyCheck = validator.validate(dto);
        assertThat(emptyCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
    }

    @Test
    public void testSizePasswordValidation(){
        UserDTO dto = generateValidDTO();
        dto.setPassword(RandomStringUtils.random(251));
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assertThat(violations.iterator().next().getMessageTemplate()).isEqualTo(invalidField);
    }

    @Test
    public void testMissingPhoneValidation(){
        UserDTO dto = generateValidDTO();
        dto.setPhone(null);
        Set<ConstraintViolation<UserDTO>> nullCheck = validator.validate(dto);
        assertThat(nullCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
        dto.setPhone(" ");
        Set<ConstraintViolation<UserDTO>> emptyCheck = validator.validate(dto);
        assertThat(emptyCheck.iterator().next().getMessageTemplate()).isEqualTo(missingField);
    }

    @Test
    public void testSizePhoneValidation(){
        UserDTO dto = generateValidDTO();
        dto.setPhone(RandomStringUtils.random(251));
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assertThat(violations.iterator().next().getMessageTemplate()).isEqualTo(invalidField);
    }

}

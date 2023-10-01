package com.veiculos.api.infra.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.veiculos.api.dto.ErrorDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Handler for the exceptions throwed by the app
 *
 * @author Pericles Tavares
 */
@RestControllerAdvice
public class CustomExceptionHandler {
    @Value("${missing.field}")
    private String missingField;
    @Value("${exist.field}")
    private String existFieldMessage;

    @Value("${authentication.invalid}")
    private String authenticationInvalid;

    @Value("${token.invalid}")
    private String tokenInvalid;

    @Value("${token.expired}")
    private String tokenExpired;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, List<ErrorDTO>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorDTO> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .distinct()
                .forEach(message -> errors.add(new ErrorDTO(message, "1")));
        return Map.of("errors", errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, List<ErrorDTO>> handleValidationExceptions() {
        return Map.of("errors", List.of(new ErrorDTO(missingField, "1")));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FieldExistsException.class)
    public Map<String, List<ErrorDTO>> handleFieldExistsExceptions(FieldExistsException ex) {
        List<ErrorDTO> errors = new ArrayList<>();
        ex.getFields().forEach((Long key, String value) -> {
            errors.add(new ErrorDTO(String.format(existFieldMessage, value), key.toString()));
        });
        return Map.of("errors", errors);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TokenExpiredException.class)
    public Map<String, List<ErrorDTO>> handleJWTExpiredToken() {
        return Map.of("errors", List.of(new ErrorDTO(tokenExpired, "5")));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({JWTVerificationException.class, InsufficientAuthenticationException.class})
    public Map<String, List<ErrorDTO>> handleJWTInvalidToken() {
        return Map.of("errors", List.of(new ErrorDTO(tokenInvalid, "6")));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public Map<String, List<ErrorDTO>> handleAuthenticationException() {
        return Map.of("errors", List.of(new ErrorDTO(authenticationInvalid, "7")));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, List<ErrorDTO>> handleErrorAuthentication(Exception e) {
        return Map.of("errors", List.of(new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "8")));
    }
}

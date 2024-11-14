package faang.school.urlshortenerservice.exception.handler;


import faang.school.urlshortenerservice.dto.handler.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class UrlExceptionHandler {

    @Value("${spring.datasource.service-name}")
    private String serviceName;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleRuntimeException(RuntimeException ex) {
        log.error("A system error has occurred: {}", ex.getMessage(), ex);

        return ErrorResponse.builder()
                .message("An error occurred, please contact support")
                .serviceName(serviceName)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> Optional.ofNullable(error.getDefaultMessage())
                                .orElse("Invalid input for field " + error.getField())
                ));

        log.error("Error validation {}: {}", errors, ex.getMessage(), ex);

        return ErrorResponse.builder()
                .serviceName(serviceName)
                .details(errors)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);

        return ErrorResponse.builder()
                .message("Error occurred")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .serviceName(serviceName)
                .build();
    }
}
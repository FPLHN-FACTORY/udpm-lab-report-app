package com.labreportapp.infrastructure.exception.rest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author thangncph26123
 */

@RestControllerAdvice
public final class RestExceptionHandler extends
        LabReportAppExceptionRestHandler<ConstraintViolationException> {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<?> handlerException(RestApiException restApiException) {
        ApiError apiError = new ApiError(restApiException.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected Object wrapApi(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        List<ErrorModel> errors = violations.stream()
                .map(violation ->
                        new ErrorModel(getPropertyName(violation.getPropertyPath()), violation.getMessage()))
                .collect(Collectors.toList());
        return errors;
    }

    private String getPropertyName(Path path) {
        String pathStr = path.toString();
        String[] comps = pathStr.split("\\.");
        if (comps.length > 0) {
            return comps[comps.length - 1];
        } else {
            return pathStr;
        }
    }

    private void log(Exception ex) {
        System.out.println(ex.getMessage());
    }
}

package com.labreportapp.portalprojects.infrastructure.exception.rest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author thangncph26123
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerException(Exception ex) {
        System.out.println(ex.getClass() + " aaaaaaaaaaaaaaaaaaaaaaa");
        if (ex instanceof RestApiException) {
            ApiError apiError = new ApiError(ex.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        } else if (ex instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) ex).getConstraintViolations();
            List<ErrorModel> errors = violations.stream()
                    .map(violation ->
                            new ErrorModel(getPropertyName(violation.getPropertyPath()), violation.getMessage()))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        } else if (ex instanceof CustomException) {
            ApiError apiError = new ApiError(ex.getMessage());
            System.out.println("=========================================================================================");
            return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
        } else if (ex instanceof NoSuchElementException) {
            return ResponseEntity.notFound().build();
        } else {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @MessageExceptionHandler(MessageHandlingException.class)
    public void handleMessageException(MessageHandlingException ex, @Header("simpSessionId") String sessionId) {
        ErrorObject errorObject = new ErrorObject(ex.getMessage());
        simpMessagingTemplate.convertAndSend("/portal-projects/error/" + sessionId, errorObject);
    }

    @MessageExceptionHandler
    public void handleException(Exception ex, @Header("simpSessionId") String sessionId) {
        if (ex instanceof ConstraintViolationException) {
            log(ex);
            ConstraintViolationException cve = (ConstraintViolationException) ex;
            Set<ConstraintViolation<?>> violations = cve.getConstraintViolations();
            List<ErrorModel> errors = violations.stream()
                    .map(violation -> new ErrorModel(getPropertyName(violation.getPropertyPath()), violation.getMessage()))
                    .collect(Collectors.toList());
            simpMessagingTemplate.convertAndSend("/portal-projects/error/" + sessionId, errors);
        }
    }

    public String getPropertyName(Path path) {
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

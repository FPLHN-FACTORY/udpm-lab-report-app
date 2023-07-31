package com.labreportapp.infrastructure.exception.rest;

import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author thangncph26123
 */

@RestControllerAdvice
public final class UnknownExceptionRestHandler extends
        LabReportAppExceptionRestHandler<Exception> {

    @Override
    protected Object wrapApi(Exception ex) {
        return ex.getMessage();
    }
}

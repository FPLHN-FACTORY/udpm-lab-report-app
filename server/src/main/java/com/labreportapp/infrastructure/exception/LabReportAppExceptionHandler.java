package com.labreportapp.infrastructure.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author thangncph26123
 */

public abstract class LabReportAppExceptionHandler<T, Z extends Exception> {

    @ExceptionHandler(Exception.class)
    public T handle(Z ex) {
        log(ex);
        return wrap(ex);
    }

    protected abstract T wrap(Z ex);

    private void log(Z ex) {
        System.out.println(ex.getMessage());
    }
}

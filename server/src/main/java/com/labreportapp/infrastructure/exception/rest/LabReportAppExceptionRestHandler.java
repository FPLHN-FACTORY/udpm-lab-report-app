package com.labreportapp.infrastructure.exception.rest;

import com.labreportapp.infrastructure.exception.LabReportAppExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author thangncph26123
 */

public abstract class LabReportAppExceptionRestHandler<Z extends Exception>
        extends LabReportAppExceptionHandler<ResponseEntity<?>,Z> {

    @Override
    protected ResponseEntity<?> wrap(Z ex) {
        return new ResponseEntity<>(wrapApi(ex), HttpStatus.BAD_REQUEST);
    }

    protected abstract Object wrapApi(Z ex);
}

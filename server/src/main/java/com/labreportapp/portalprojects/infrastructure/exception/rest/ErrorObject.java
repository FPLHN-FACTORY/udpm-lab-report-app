package com.labreportapp.portalprojects.infrastructure.exception.rest;

/**
 * @author thangncph26123
 */
public class ErrorObject {

    private String errorMessage;

    public ErrorObject(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

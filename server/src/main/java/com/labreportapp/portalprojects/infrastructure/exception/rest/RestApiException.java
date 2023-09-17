package com.labreportapp.portalprojects.infrastructure.exception.rest;


import com.labreportapp.portalprojects.infrastructure.constant.Message;

/**
 * @author thangncph26123
 */

public class RestApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    public RestApiException() {
    }

    public RestApiException(Message statusCode) {
        this.message = statusCode.getMessage();
    }

    public RestApiException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

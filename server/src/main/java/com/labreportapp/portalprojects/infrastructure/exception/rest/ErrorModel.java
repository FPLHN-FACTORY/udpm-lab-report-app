package com.labreportapp.portalprojects.infrastructure.exception.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorModel {

    private String fieldError;

    private String message;

}

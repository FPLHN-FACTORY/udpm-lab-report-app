package com.labreportapp.infrastructure.exception.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */

@AllArgsConstructor
@Getter
@Setter
public class ApiError {

    private String message;

}

package com.labreportapp.core.common.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@ToString
public class SimpleResponse {

    private String id;

    private String name;

    private String username;

    private String email;
}

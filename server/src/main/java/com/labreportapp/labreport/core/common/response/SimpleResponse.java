package com.labreportapp.labreport.core.common.response;

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

    private String userName;

    private String email;

    private String picture;
}
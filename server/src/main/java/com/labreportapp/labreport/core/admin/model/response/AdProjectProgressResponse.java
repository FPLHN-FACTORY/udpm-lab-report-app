package com.labreportapp.labreport.core.admin.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894 - duchieu212
 */
@Getter
@Setter
@ToString
public class AdProjectProgressResponse {

    private String id;

    private String name;

    private String code;

    private Float progress;

}

package com.labreportapp.labreport.core.common.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author todo thangncph26123
 */
@Getter
@Setter
@ToString
public class HoneyConversionRequest {

    private String code;

    private String categoryId;

    private List<ItemStudentHoneyRequest> listStudent;

    private String name;

    private String username;

}

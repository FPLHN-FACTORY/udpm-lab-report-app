package com.labreportapp.labreport.core.teacher.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894
 */
@Getter
@Setter
@ToString
public class TeFindUpdateStatusClassRequest {

    private String idClass;

    private Integer status;

}

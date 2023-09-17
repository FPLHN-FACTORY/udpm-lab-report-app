package com.labreportapp.labreport.core.teacher.model.request;

import com.labreportapp.labreport.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeFindClassRequest extends PageableRequest {

    private String idTeacher;

    private String idSemester;

    private String idActivity;

    private String code;

    private String classPeriod;

    private String level;

}

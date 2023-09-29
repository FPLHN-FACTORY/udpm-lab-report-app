package com.labreportapp.labreport.core.teacher.model.request;

import com.labreportapp.labreport.core.common.base.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeFindClassSentStudentRequest extends PageableRequest {

    private String idSemester;

    private String idActivity;

    private String idLevel;

    private String idClass;

    private Integer countStudent;

}

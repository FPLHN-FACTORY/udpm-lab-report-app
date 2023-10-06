package com.labreportapp.labreport.core.teacher.model.request;

import com.labreportapp.labreport.core.common.base.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TeFindClassStatisticalRequest extends PageableRequest {

    private String idSemester;

    private String idActivity;

    private String idTeacher;

}

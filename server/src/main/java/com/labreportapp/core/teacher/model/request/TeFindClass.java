package com.labreportapp.core.teacher.model.request;

import com.labreportapp.core.common.base.PageableRequest;
import com.labreportapp.infrastructure.constant.PaginationConstant;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeFindClass extends PageableRequest {

    private String idTeacher;

    private String idSemester;

    private String idActivity;

}

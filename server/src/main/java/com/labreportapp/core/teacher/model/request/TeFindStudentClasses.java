package com.labreportapp.core.teacher.model.request;

import com.labreportapp.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeFindStudentClasses extends PageableRequest {

    private String idClass;

    private String idTeam;

}

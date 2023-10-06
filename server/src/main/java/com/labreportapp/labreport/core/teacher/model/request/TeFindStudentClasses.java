package com.labreportapp.labreport.core.teacher.model.request;

import com.labreportapp.labreport.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeFindStudentClasses extends PageableRequest {

    private String idClass;

    private String idMeeting;

    private String idTeam;

}

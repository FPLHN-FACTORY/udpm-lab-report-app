package com.labreportapp.labreport.core.student.model.request;

import com.labreportapp.labreport.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StFindAttendanceRequest extends PageableRequest {

    private String idStudent;

    private String idClass;
}

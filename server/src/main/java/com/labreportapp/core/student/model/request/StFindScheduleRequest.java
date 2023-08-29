package com.labreportapp.core.student.model.request;

import com.labreportapp.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StFindScheduleRequest extends PageableRequest {

    private String idStudent;

    private Long searchTime;
}

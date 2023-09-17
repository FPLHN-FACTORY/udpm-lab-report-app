package com.labreportapp.labreport.core.admin.model.request;

import com.labreportapp.labreport.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdFindSemesterRequest extends PageableRequest {

    private String name;

    private Long startTime;

    private Long endTime;
}

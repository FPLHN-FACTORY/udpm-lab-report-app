package com.labreportapp.core.admin.model.request;

import com.labreportapp.core.common.base.PageableRequest;
import com.labreportapp.infrastructure.constant.Level;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdFindActivityRequest extends PageableRequest {
    private String name;

    private Long startTime;

    private Long endTime;

    private Level level;

    private String semesterId;
}

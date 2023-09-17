package com.labreportapp.labreport.core.admin.model.request;

import com.labreportapp.labreport.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdFindActivityRequest extends PageableRequest {

    private String name;

    private String level;

    private String semesterId;
}

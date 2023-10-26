package com.labreportapp.portalprojects.core.admin.model.request;

import com.labreportapp.portalprojects.core.common.base.PageableRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894
 */
@Getter
@Setter
@ToString
public class AdFindProjectRequest extends PageableRequest {

    private String code;

    private String name;

    private String startTime;

    private String endTime;

    private String statusProject;

    private String idCategory;

    private Long startTimeLong;

    private Long endTimeLong;

    private String groupProjectId;

}

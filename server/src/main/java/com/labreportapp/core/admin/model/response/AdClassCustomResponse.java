package com.labreportapp.core.admin.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdClassCustomResponse {

    private String id;

    private String code;

    private Long startTime;

    private Long classPeriod;

    private Integer classSize;

    private String teacherId;

    private String userNameTeacher;

    private String activityName;
}

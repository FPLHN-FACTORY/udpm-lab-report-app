package com.labreportapp.labreport.core.admin.model.response;

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

    private String classPeriod;

    private Integer startHour;

    private Integer startMinute;

    private Integer endHour;

    private Integer endMinute;

    private Integer classSize;

    private String teacherId;

    private String userNameTeacher;

    private String activityName;

    private String nameLevel;

    private String statusClass;

    private Integer statusTeacherEdit;
}

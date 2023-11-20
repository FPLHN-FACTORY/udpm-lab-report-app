package com.labreportapp.labreport.core.student.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StClassCustomResponse {

    private String id;

    private Integer stt;

    private String classCode;

    private Integer classSize;

    private Long startTime;

    private String classPeriod;

    private Integer startHour;

    private Integer startMinute;

    private Integer endHour;

    private Integer endMinute;

    private String level;

    private String nameTeacher;

    private String userNameTeacher;

    private String activityName;

    private Long startTimeStudent;

    private Long endTimeStudent;

    private String descriptions;

    private String passWord;
}

package com.labreportapp.labreport.core.student.model.response;

import com.labreportapp.labreport.infrastructure.constant.ClassPeriod;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class StDetailClassCustomResponse {

    private String id;

    private String code;

    private Long startTime;

    private ClassPeriod classPeriod;

    private Integer classSize;

    private String descriptions;

    private String teacherId;

    private String activityId;

    private String nameTeacher;

    private String usernameTeacher;
}

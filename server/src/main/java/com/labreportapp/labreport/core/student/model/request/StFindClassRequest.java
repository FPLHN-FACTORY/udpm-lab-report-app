package com.labreportapp.labreport.core.student.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@ToString
public class StFindClassRequest {

    private String semesterId;

    private String activityId;

    private String code;

    private Short classPeriod;

    private String level;

    private String studentId;
}

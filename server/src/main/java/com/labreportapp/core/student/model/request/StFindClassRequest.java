package com.labreportapp.core.student.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class StFindClassRequest {

    private String semesterId;

    private String activityId;

    private String code;

    private String name;

    private Short classPeriod;

    private Short level;

    private String studentId;
}

package com.labreportapp.labreport.core.student.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class StStudentFeedBackRequest {

    private String classId;

    private Integer rateQuestion1;

    private Integer rateQuestion2;

    private Integer rateQuestion3;

    private Integer rateQuestion4;

    private Integer rateQuestion5;

    private Boolean status;

    private String descriptions;
}

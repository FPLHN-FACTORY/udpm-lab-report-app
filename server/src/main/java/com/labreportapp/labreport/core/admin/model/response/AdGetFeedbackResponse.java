package com.labreportapp.labreport.core.admin.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author quynhncph26201
 */
@Getter
@Setter
@ToString
public class AdGetFeedbackResponse {

    private Integer stt;

    private String id;

    private Integer rateQuestion1;

    private Integer rateQuestion2;

    private Integer rateQuestion3;

    private Integer rateQuestion4;

    private Integer rateQuestion5;

    private Float averageRate;

    private Integer statusShow;

    private String descriptions;

    private String studentId;

    private String studentName;

    private String studentUserName;
}

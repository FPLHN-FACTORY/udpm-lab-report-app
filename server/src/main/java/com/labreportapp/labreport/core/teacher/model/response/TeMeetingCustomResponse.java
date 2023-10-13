package com.labreportapp.labreport.core.teacher.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894
 */
@Getter
@Setter
@ToString
public class TeMeetingCustomResponse {

    private String id;

    private String name;

    private String descriptions;

    private Long meetingDate;

    private Integer typeMeeting;

    private String meetingPeriod;

    private Integer startHour;

    private Integer startMinute;

    private Integer endHour;

    private Integer endMinute;

    private String idClass;

    private String idTeacher;

    private String userNameTeacher;

    private Integer statusMeeting;

}

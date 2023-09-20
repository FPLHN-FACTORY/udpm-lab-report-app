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
public class TeMeetingCustomRespone {

    private String id;

    private String name;

    private String descriptions;

    private Long meetingDate;

    private Integer typeMeeting;

    private Integer meetingPeriod;

    private String idClass;

    private String idTeacher;

    private String userNameTeacher;

}

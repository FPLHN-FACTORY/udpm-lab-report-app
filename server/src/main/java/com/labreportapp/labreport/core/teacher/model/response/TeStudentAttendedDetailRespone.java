package com.labreportapp.labreport.core.teacher.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeStudentAttendedDetailRespone {

    private Integer stt;

    private String idStudent;

    private String nameMeeting;

    private Long meetingDate;

    private String meetingPeriod;

    private Integer startHour;

    private Integer startMinute;

    private Integer endHour;

    private Integer endMinute;

    private Integer typeMeeting;

    private Integer statusMeeting;

    private String notes;

    private Integer status;

    private String idTeacher;

    private String usernameTeacher;

}

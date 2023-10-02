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

    private Integer meetingPeriod;

    private Integer typeMeeting;

    private Integer status;

    private String idTeacher;

    private String usernameTeacher;

}

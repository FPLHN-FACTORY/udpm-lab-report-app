package com.labreportapp.labreport.core.student.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StAttendenceAllCustomResponse {

    private Integer stt;

    private String name;

    private Long meetingDate;

    private String meetingPeriod;

    private Integer startHour;

    private Integer startMinute;

    private Integer endHour;

    private Integer endMinute;

    private Integer typeMeeting;

    private Integer status;

    private String teacherId;

    private String teacherUsername;

}

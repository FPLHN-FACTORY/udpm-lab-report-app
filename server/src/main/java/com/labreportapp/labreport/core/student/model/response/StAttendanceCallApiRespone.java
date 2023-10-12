package com.labreportapp.labreport.core.student.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StAttendanceCallApiRespone {
    private String id;

    private String name;

    private String userName;

    private String email;

    private Integer stt;

    private String lesson;

    private Long meetingDate;

    private String meetingPeriod;

    private Integer typeMeeting;

    private String teacherId;

    private Integer status;

    private String notes;
}

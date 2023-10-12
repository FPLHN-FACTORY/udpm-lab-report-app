package com.labreportapp.labreport.core.student.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StAttendenceAllCustomResponse {

  private Integer stt;

  private String name;

  private Long meetingDate;

  private String meetingPeriod;

  private Integer typeMeeting;

  private Integer status;

  private String teacherId;

  private String teacherUsername;

}

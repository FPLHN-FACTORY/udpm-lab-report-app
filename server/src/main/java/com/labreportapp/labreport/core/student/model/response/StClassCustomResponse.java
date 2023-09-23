package com.labreportapp.labreport.core.student.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StClassCustomResponse {

  private String id;

  private Integer stt;

  private String classCode;

  private String teacherUsername;

  private Integer classSize;

  private Long startTime;

  private Short classPeriod;

  private String level;

  private String activityName;

  private Long startTimeStudent;

  private Long endTimeStudent;

}

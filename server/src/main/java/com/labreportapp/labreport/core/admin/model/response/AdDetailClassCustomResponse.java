package com.labreportapp.labreport.core.admin.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdDetailClassCustomResponse {
  private String id;

  private String code;

  private Long startTime;

  private String passWord;

  private Integer classPeriod;

  private Integer classSize;

  private String descriptions;

  private String teacherId;

  private String teacherName;

  private String teacherUserName;

  private String activityId;

  private String activityName;

  private String activityLevel;

  private String semesterId;

  private String semesterName;

  private Integer statusTeacherEdit;

  private String levelId;


}

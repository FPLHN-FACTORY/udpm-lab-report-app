package com.labreportapp.labreport.core.student.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

public interface StClassResponse extends IsIdentified {

  @Value("#{target.stt}")
  Integer getStt();

  @Value("#{target.code}")
  String getCode();

  @Value("#{target.teacher_id}")
  String getTeacherId();

  @Value("#{target.class_size}")
  Integer getClassSize();

  @Value("#{target.start_time}")
  Long getStartTime();

  @Value("#{target.class_period}")
  Short getClassPeriod();

  @Value("#{target.name}")
  String getLevel();

  @Value("#{target.activityName}")
  String getActivityName();

  @Value("#{target.start_time_student}")
  Long getStartTimeStudent();

  @Value("#{target.end_time_student}")
  Long getEndTimeStudent();

}

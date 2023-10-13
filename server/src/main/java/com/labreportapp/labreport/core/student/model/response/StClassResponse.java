package com.labreportapp.labreport.core.student.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

public interface StClassResponse extends IsIdentified {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.class_size}")
    Integer getClassSize();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.class_period}")
    String getClassPeriod();

    @Value("#{target.start_hour}")
    Integer getStartHour();

    @Value("#{target.start_minute}")
    Integer getStartMinute();

    @Value("#{target.end_hour}")
    Integer getEndHour();

    @Value("#{target.end_minute}")
    Integer getEndMinute();

    @Value("#{target.name}")
    String getLevel();

    @Value("#{target.activityName}")
    String getActivityName();

    @Value("#{target.start_time_student}")
    Long getStartTimeStudent();

    @Value("#{target.end_time_student}")
    Long getEndTimeStudent();

    @Value("#{target.descriptions}")
    String getDescriptions();

}

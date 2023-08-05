package com.labreportapp.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeClassResponse {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.id}")
    String getId();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.start_time}")
    Long getStartTime();

//    @Value("#{target.end_time}")
//    Long getEndTime();

    @Value("#{target.password}")
    String getPassword();

    @Value("#{target.class_period}")
    Integer getClassPeriod();

    @Value("#{target.class_size}")
    Integer getClassSize();

    @Value("#{target.teacher_id}")
    String getTeacherId();

    @Value("#{target.activity_id}")
    String getActivityId();

    @Value("#{target.created_date}")
    Long getCreatedDate();

    @Value("#{target.descriptions}")
    String getDescriptions();

    @Value("#{target.level}")
    Integer getLevel();

}

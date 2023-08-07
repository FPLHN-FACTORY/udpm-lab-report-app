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

    @Value("#{target.class_period}")
    Integer getClassPeriod();

    @Value("#{target.level}")
    Integer getLevel();

}

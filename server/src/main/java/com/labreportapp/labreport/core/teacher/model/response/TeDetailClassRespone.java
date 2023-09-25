package com.labreportapp.labreport.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeDetailClassRespone {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.password}")
    String getPassWord();

    @Value("#{target.class_period}")
    Integer getClassPeriod();

    @Value("#{target.class_size}")
    Integer getClassSize();

    @Value("#{target.activityName}")
    String getActivityName();

    @Value("#{target.descriptions}")
    String getDescriptions();

    @Value("#{target.activityLevel}")
    String getActivityLevel();

    @Value("#{target.semesterName}")
    String getSemesterName();

    @Value("#{target.status_class}")
    Integer getStatusClass();

    @Value("#{target.allow_use_trello}")
    Integer getAllowUseTrello();



}

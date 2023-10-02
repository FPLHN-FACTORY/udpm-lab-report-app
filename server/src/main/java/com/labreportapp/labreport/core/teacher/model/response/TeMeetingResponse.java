package com.labreportapp.labreport.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeMeetingResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.descriptions}")
    String getDescriptions();

    @Value("#{target.meeting_date}")
    Long getMeetingDate();

    @Value("#{target.type_meeting}")
    Integer getTypeMeeting();

    @Value("#{target.meeting_period}")
    Integer getMeetingPeriod();

    @Value("#{target.class_id}")
    String getIdClass();

    @Value("#{target.teacher_id}")
    String getIdTeacher();

}

package com.labreportapp.labreport.core.student.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author quynhncph26201
 */
public interface StMeetingResponse {
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
    String getMeetingPeriod();

    @Value("#{target.start_hour}")
    Integer getStartHour();

    @Value("#{target.start_minute}")
    Integer getStartMinute();

    @Value("#{target.end_hour}")
    Integer getEndHour();

    @Value("#{target.end_minute}")
    Integer getEndMinute();

    @Value("#{target.class_id}")
    String getIdClass();
}

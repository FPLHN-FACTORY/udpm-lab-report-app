package com.labreportapp.labreport.core.student.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface StScheduleResponse {

    @Value("#{target.meeting_name}")
    String getMeetingName();

    @Value("#{target.meeting_date}")
    Long getMeetingDate();

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

    @Value("#{target.type_meeting}")
    Integer getTypeMeeting();

    @Value("#{target.address}")
    String getAddress();

    @Value("#{target.class_code}")
    String getClassCode();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.descriptions}")
    String getDescriptions();
}

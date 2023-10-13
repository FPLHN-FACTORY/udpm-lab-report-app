package com.labreportapp.labreport.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeMeetingCustomToAttendanceResponse {

    @Value("#{target.id}")
    String getIdMeeting();

    @Value("#{target.name}")
    String getNameMeeting();

    @Value("#{target.class_id}")
    String getIdClass();

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

    @Value("#{target.status_meeting}")
    Integer getStatusMeeting();

}

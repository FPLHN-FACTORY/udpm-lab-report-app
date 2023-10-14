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

    @Value("#{target.notes}")
    String getNotes();

    @Value("#{target.meeting_date}")
    Long getMeetingDate();

    @Value("#{target.type_meeting}")
    Integer getTypeMeeting();

    @Value("#{target.id_meeting_period}")
    String getIdMeetingPeriod();

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

    @Value("#{target.teacher_id}")
    String getIdTeacher();

    @Value("#{target.status_meeting}")
    Integer getStatusMeeting();
}

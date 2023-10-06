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
    Integer getMeetingPeriod();

    @Value("#{target.status_meeting}")
    Integer getStatusMeeting();

}

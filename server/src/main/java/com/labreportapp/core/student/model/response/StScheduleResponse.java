package com.labreportapp.core.student.model.response;

import com.labreportapp.entity.Meeting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

public interface StScheduleResponse {

    @Value("#{target.meeting_name}")
    String getMeetingName();

    @Value("#{target.meeting_date}")
    Long getMeetingDate();

    @Value("#{target.meeting_period}")
    Integer getMeetingPeriod();

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

package com.labreportapp.core.student.model.response;

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
    Integer getMeetingPeriod();

    @Value("#{target.class_id}")
    String getIdClass();
}

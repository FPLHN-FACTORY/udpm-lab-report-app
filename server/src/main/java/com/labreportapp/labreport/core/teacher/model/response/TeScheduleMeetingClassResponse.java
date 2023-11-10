package com.labreportapp.labreport.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeScheduleMeetingClassResponse {

    Integer getStt();

    @Value("#{target.id_class}")
    String getIdClass();

    @Value("#{target.code_class}")
    String getCodeClass();

    @Value("#{target.id_meeting}")
    String getIdMeeting();

    @Value("#{target.meeting_date}")
    Long getMeetingDate();

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

    @Value("#{target.name_meeting}")
    String getMeetingName();

    @Value("#{target.type_meeting}")
    Integer getTypeMeeting();

    @Value("#{target.address_meeting}")
    String getMeetingAddress();

    @Value("#{target.descriptions_meeting}")
    String getDescriptionsMeeting();

    @Value("#{target.level}")
    String getLevel();

    @Value("#{target.notes}")
    String getNotes();

    @Value("#{target.status_meeting}")
    String getStatusMeeting();

}

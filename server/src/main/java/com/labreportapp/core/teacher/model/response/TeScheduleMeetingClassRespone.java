package com.labreportapp.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeScheduleMeetingClassRespone {

    @Value("#{target.id_class}")
    String getIdClass();

    @Value("#{target.code_class}")
    String getCodeClass();

    @Value("#{target.id_meeting}")
    String getIdMeeting();

    @Value("#{target.meeting_date}")
    Long getMeetingDate();

    @Value("#{target.meeting_period}")
    Integer getMeetingPeriod();

    @Value("#{target.name_meeting}")
    String getMeetingName();

    @Value("#{target.type_meeting}")
    Integer getTypeMeeting();

    @Value("#{target.address_meeting}")
    String getMeetingAddress();

    @Value("#{target.descriptions_meeting}")
    String getDescriptionsMeeting();

}

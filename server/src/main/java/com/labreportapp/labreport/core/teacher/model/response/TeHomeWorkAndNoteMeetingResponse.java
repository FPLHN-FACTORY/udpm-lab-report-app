package com.labreportapp.labreport.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeHomeWorkAndNoteMeetingResponse {

    @Value("#{target.idMeeting}")
    String getIdMeeting();

    @Value("#{target.nameMeeting}")
    String getNameMeeting();

    @Value("#{target.descriptionsMeeting}")
    String getDescriptionsMeeting();

    @Value("#{target.idHomeWork}")
    String getIdHomeWork();

    @Value("#{target.descriptionsHomeWork}")
    String getDescriptionsHomeWork();

    @Value("#{target.idNote}")
    String getIdNote();

    @Value("#{target.descriptionsNote}")
    String getDescriptionsNote();

    @Value("#{target.idReport}")
    String getIdReport();

    @Value("#{target.descriptionsReport}")
    String getDescriptionsReport();

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


}

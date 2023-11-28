package com.labreportapp.labreport.core.admin.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author todo thangncph26123
 */
public interface AdMeetingRequestResponse extends IsIdentified {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.meeting_date}")
    Long getMeetingDate();

    @Value("#{target.meeting_period_id}")
    String getMeetingPeriodId();

    @Value("#{target.name_meeting_period}")
    String getNameMeetingPeriod();

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

    @Value("#{target.teacher_id}")
    String getTeacherId();

}

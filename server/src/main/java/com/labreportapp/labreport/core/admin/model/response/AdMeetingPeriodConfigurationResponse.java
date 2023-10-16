package com.labreportapp.labreport.core.admin.model.response;

import com.labreportapp.labreport.entity.MeetingPeriod;
import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author quynhncph26201
 */
@Projection(types = {MeetingPeriod.class})
public interface AdMeetingPeriodConfigurationResponse extends IsIdentified {

    @Value("#{target.stt}")
    Integer STT();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.startHour}")
    Integer getStartHour();

    @Value("#{target.startMinute}")
    Integer getStartMinute();

    @Value("#{target.endHour}")
    Integer getEndHour();

    @Value("#{target.endMinute}")
    Integer getEndMinute();
}

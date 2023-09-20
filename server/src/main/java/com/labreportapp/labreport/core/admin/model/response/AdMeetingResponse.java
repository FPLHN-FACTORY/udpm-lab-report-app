package com.labreportapp.labreport.core.admin.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface AdMeetingResponse extends IsIdentified {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.meeting_date}")
    Long getMeetingDate();

    @Value("#{target.meeting_period}")
    Integer getMeetingPeriod();

    @Value("#{target.type_meeting}")
    Integer getTypeMeeting();

    @Value("#{target.address}")
    String getAddress();

    @Value("#{target.teacher_id}")
    String getTeacherId();

    @Value("#{target.descriptions}")
    String getDescriptions();

    @Value("#{target.so_diem_danh}")
    Integer getSoDiemDanh();
}

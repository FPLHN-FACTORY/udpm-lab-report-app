package com.labreportapp.labreport.core.student.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

public interface StAttendanceRespone extends IsIdentified {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.name}")
    String getLesson();

    @Value("#{target.meeting_date}")
    Long getMeetingDate();

    @Value("#{target.meeting_periods}")
    String getMeetingPeriod();

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

    @Value("#{target.status}")
    Integer getStatus();

    @Value("#{target.notes}")
    String getNotes();
}

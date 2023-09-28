package com.labreportapp.labreport.core.teacher.model.response;

import com.labreportapp.labreport.core.teacher.model.response.Base.TeIsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeStudentAttendanceRespone extends TeIsIdentified {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.name}")
    String getLesson();

    @Value("#{target.meeting_date}")
    Long getMeetingDate();

    @Value("#{target.meeting_period}")
    Integer getMeetingPeriod();

    @Value("#{target.type_meeting}")
    Integer getTypeMeeting();

    @Value("#{target.teacher_id}")
    String getTeacherId();

    @Value("#{target.status}")
    Integer getStatus();

}

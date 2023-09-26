package com.labreportapp.labreport.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeAttendanceResponse {

    @Value("#{target.idAttendance}")
    String getIdAttendance();

    @Value("#{target.name_meeting}")
    String getNameMeeting();

    @Value("#{target.status}")
    String getStatusAttendance();

    @Value("#{target.student_id}")
    String getIdStudent();

    @Value("#{target.meeting_id}")
    String getIdMeeting();

    @Value("#{target.meeting_date}")
    Long getMeetingDate();

}

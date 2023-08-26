package com.labreportapp.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeAttendanceRespone {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.name_meeting}")
    String getNameMeeting();

    @Value("#{target.status}")
    Integer getStatus();

    @Value("#{target.student_id}")
    String getStudentId();

    @Value("#{target.meeting_id}")
    String getMeetingId();

}

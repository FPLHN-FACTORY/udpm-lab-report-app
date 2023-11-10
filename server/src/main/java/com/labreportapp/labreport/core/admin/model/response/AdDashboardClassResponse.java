package com.labreportapp.labreport.core.admin.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author todo thangncph26123
 */
public interface AdDashboardClassResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.name_level}")
    String getNameLevel();

    @Value("#{target.name_activity}")
    String getNameActivity();

    @Value("#{target.class_size}")
    Integer getClassSize();

    @Value("#{target.number_post}")
    Integer getNumberPost();

    @Value("#{target.number_team}")
    Integer getNumberTeam();

    @Value("#{target.number_meeting}")
    Integer getNumberMeeting();

    @Value("#{target.number_meeting_took_place}")
    Integer getNumberMeetingTookPlace();

    @Value("#{target.number_student_pass}")
    Integer getNumberStudentPass();

    @Value("#{target.number_student_fail}")
    Integer getNumberStudentFail();
}

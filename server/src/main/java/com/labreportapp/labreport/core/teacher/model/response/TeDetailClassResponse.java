package com.labreportapp.labreport.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeDetailClassResponse {

    @Value("#{target.id_class}")
    String getId();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.password}")
    String getPassWord();

    @Value("#{target.class_period}")
    Integer getClassPeriod();

    @Value("#{target.class_size}")
    Integer getClassSize();

    @Value("#{target.activityName}")
    String getActivityName();

    @Value("#{target.descriptions}")
    String getDescriptions();

    @Value("#{target.activity_id}")
    String getActivityId();

    @Value("#{target.level_id}")
    String getLevelId();

    @Value("#{target.activityLevel}")
    String getActivityLevel();

    @Value("#{target.semester_id}")
    String getSemesterId();

    @Value("#{target.semesterName}")
    String getSemesterName();

    @Value("#{target.status_class}")
    Integer getStatusClass();

    @Value("#{target.allow_use_trello}")
    Integer getAllowUseTrello();

    @Value("#{target.status_teacher_edit}")
    Integer getStatusTeacherEdit();

}

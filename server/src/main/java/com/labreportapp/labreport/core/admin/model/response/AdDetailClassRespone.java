package com.labreportapp.labreport.core.admin.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface AdDetailClassRespone {
    @Value("#{target.id}")
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

    @Value("#{target.descriptions}")
    String getDescriptions();

    @Value("#{target.teacherId}")
    String getTeacherId();

    @Value("#{target.activityId}")
    String getActivityId();

    @Value("#{target.activityName}")
    String getActivityName();

    @Value("#{target.activityLevel}")
    String getActivityLevel();

    @Value("#{target.semesterId}")
    String getSemesterId();

    @Value("#{target.semesterName}")
    String getSemesterName();

    @Value("#{target.status_teacher_edit}")
    Integer getStatusTeacherEdit();
}

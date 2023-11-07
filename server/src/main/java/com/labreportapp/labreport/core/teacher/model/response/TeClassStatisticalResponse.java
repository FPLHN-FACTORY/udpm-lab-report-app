package com.labreportapp.labreport.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeClassStatisticalResponse {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.id}")
    String getId();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.class_period}")
    String getClassPeriod();

    @Value("#{target.start_hour}")
    Integer getStartHour();

    @Value("#{target.start_minute}")
    Integer getStartMinute();

    @Value("#{target.end_hour}")
    Integer getEndHour();

    @Value("#{target.end_minute}")
    Integer getEndMinute();

    @Value("#{target.class_size}")
    Integer getClassSize();

    @Value("#{target.level}")
    String getLevel();

    @Value("#{target.activity}")
    String getActivity();

    @Value("#{target.teacher_id}")
    String getTeacherId();

    @Value("#{target.count_team}")
    Integer getCountTeam();

    @Value("#{target.count_lesson}")
    Integer getCountLesson();

    @Value("#{target.count_lesson_off}")
    Integer getCountLessonOff();

    @Value("#{target.count_post}")
    Integer getCountPost();

    @Value("#{target.count_pass}")
    Integer getCountPass();

    @Value("#{target.count_faild}")
    Integer getCountFaild();

}

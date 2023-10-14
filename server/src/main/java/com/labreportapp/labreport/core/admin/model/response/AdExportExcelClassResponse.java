package com.labreportapp.labreport.core.admin.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface AdExportExcelClassResponse {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.name_class_period}")
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

    @Value("#{target.teacher_id}")
    String getTeacherId();

    @Value("#{target.name_level}")
    String getNameLevel();

    @Value("#{target.name_activity}")
    String getNameActivity();
}

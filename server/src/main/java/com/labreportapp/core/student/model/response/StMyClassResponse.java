package com.labreportapp.core.student.model.response;

import com.labreportapp.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface StMyClassResponse extends IsIdentified {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.class_period}")
    Short getClassPeriod();

    @Value("#{target.teacher_id}")
    String getTeacherId();

    @Value("#{target.level}")
    Short getLevel();
}

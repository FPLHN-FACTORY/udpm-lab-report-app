package com.labreportapp.labreport.core.student.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface StMyClassResponse extends IsIdentified {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.class_period}")
    Short getClassPeriod();

    @Value("#{target.teacher_id}")
    String getTeacherId();

    @Value("#{target.name}")
    String getLevel();

    @Value("#{target.nameActivity}")
    String getNameActivity();
}

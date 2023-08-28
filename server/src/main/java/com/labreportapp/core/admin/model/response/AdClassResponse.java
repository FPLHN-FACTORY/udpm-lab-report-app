package com.labreportapp.core.admin.model.response;

import com.labreportapp.entity.Activity;
import com.labreportapp.entity.Class;
import com.labreportapp.entity.base.IsIdentified;
import com.labreportapp.infrastructure.constant.ClassPeriod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author quynhncph26201
 */
@Projection(types = {Class.class, Activity.class})
public interface AdClassResponse extends IsIdentified {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.class_period}")
    Long getClassPeriod();

    @Value("#{target.class_size}")
    Integer getClassSize();

    @Value("#{target.teacher_id}")
    String getTeacherId();

    @Value("#{target.nameActivity}")
    String getActivityName();

//    @Value("#{target.semesterId}")
//    String getSemesterName();
}

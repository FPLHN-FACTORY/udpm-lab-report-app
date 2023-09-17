package com.labreportapp.labreport.core.admin.model.response;

import com.labreportapp.labreport.entity.Activity;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.base.IsIdentified;
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

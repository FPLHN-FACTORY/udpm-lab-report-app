package com.labreportapp.core.admin.model.response;

import com.labreportapp.entity.Activity;
import com.labreportapp.entity.base.IsIdentified;
import com.labreportapp.infrastructure.constant.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Activity.class})
public interface AdActivityResponse extends IsIdentified {

    Integer getSTT();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.startTime}")
    Long getStartTime();

    @Value("#{target.endTime}")
    Long getEndTime();

    @Value("#{target.level}")
    Short getLevel();

    @Value("#{target.semesterId}")
    String getSemesterId();

    @Value("#{target.nameSemester}")
    String getNameSemester();
}

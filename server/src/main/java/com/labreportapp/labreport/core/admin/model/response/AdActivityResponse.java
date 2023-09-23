package com.labreportapp.labreport.core.admin.model.response;

import com.labreportapp.labreport.entity.Activity;
import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Activity.class})
public interface AdActivityResponse extends IsIdentified {

    Integer getSTT();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.startTime}")
    Long getStartTime();

    @Value("#{target.endTime}")
    Long getEndTime();

    @Value("#{target.level}")
    String getLevel();

    @Value("#{target.semesterId}")
    String getSemesterId();

    @Value("#{target.nameSemester}")
    String getNameSemester();

    @Value("#{target.descriptions}")
    String getDescriptions();
}

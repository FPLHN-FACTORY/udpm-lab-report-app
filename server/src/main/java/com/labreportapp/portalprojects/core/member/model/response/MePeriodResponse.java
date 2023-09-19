package com.labreportapp.portalprojects.core.member.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import com.labreportapp.portalprojects.entity.Period;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {Period.class})
public interface MePeriodResponse extends IsIdentified {

    @Value("#{target.stt}")
    Integer getSTT();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.progress}")
    Float getProgress();

    @Value("#{target.target}")
    String getTarget();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.end_time}")
    Long getEndTime();

    @Value("#{target.descriptions}")
    String getDescriptions();

    @Value("#{target.status_period}")
    Integer getStatus();

}

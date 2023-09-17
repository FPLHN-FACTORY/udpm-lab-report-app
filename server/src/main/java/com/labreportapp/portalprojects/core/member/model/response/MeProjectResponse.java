package com.labreportapp.portalprojects.core.member.model.response;

import com.labreportapp.portalprojects.entity.Project;
import com.labreportapp.portalprojects.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {Project.class})
public interface MeProjectResponse extends IsIdentified {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.descriptions}")
    String getDescriptions();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.end_time}")
    Long getEndTime();

    @Value("#{target.status}")
    Integer getStatus();

    @Value("#{target.progress}")
    Float getProgress();

    @Value("#{target.background_image}")
    String getBackgroundImage();

    @Value("#{target.background_color}")
    String getBackgroundColor();
}
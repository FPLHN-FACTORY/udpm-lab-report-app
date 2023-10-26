package com.labreportapp.portalprojects.core.member.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import com.labreportapp.portalprojects.entity.Project;
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

    @Value("#{target.name_group_project}")
    String getNameGroupProject();

    @Value("#{target.id_group_project}")
    String getGroupProjectId();

    @Value("#{target.type_project}")
    Integer getTypeProject();

}
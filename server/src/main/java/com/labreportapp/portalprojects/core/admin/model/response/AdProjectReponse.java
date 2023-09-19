package com.labreportapp.portalprojects.core.admin.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import com.labreportapp.portalprojects.entity.Category;
import com.labreportapp.portalprojects.entity.Project;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author hieundph25894
 */
@Projection(types = {Project.class, Category.class})
public interface AdProjectReponse extends IsIdentified {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.descriptions}")
    String getDescriptions();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.end_time}")
    Long getEndTime();

    @Value("#{target.progress}")
    Float getProgress();

    @Value("#{target.created_date}")
    Long getCreateDate();

    @Value("#{target.status_project}")
    String getStatusProject();

    @Value("#{target.background_image}")
    String getBackGroundImage();

    @Value("#{target.background_color}")
    String getBackGroundColor();

    @Value("#{target.nameCategorys}")
    String getNameCategorys();

}

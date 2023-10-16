package com.labreportapp.labreport.core.admin.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import com.labreportapp.portalprojects.entity.TypeProject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author quynhncph26201
 */
@Projection(types = {TypeProject.class})
public interface AdTypeProjectResponse extends IsIdentified {
    @Value("#{target.stt}")
    Integer STT();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.description}")
    String getDescription();
}

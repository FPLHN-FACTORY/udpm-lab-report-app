package com.labreportapp.labreport.core.admin.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import com.labreportapp.portalprojects.entity.RoleProject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author quynhncph26201
 */
@Projection(types = {RoleProject.class})
public interface AdRoleProjectResponse extends IsIdentified {
    @Value("#{target.stt}")
    Integer STT();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.description}")
    String getDescription();

    @Value("#{target.idProject}")
    String getIdProject();
}

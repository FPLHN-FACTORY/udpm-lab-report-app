package com.labreportapp.portalprojects.core.member.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface MeRoleProjectResponse extends IsIdentified {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.description}")
    String getDescription();

    @Value("#{target.role_default}")
    Integer getRoleDefault();
}

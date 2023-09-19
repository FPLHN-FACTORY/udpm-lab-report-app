package com.labreportapp.portalprojects.infrastructure.projection;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author phongtt35
 */
@Projection(types = {})
public interface SimpleEntityProj extends IsIdentified {
    String getName();
}

package com.labreportapp.portalprojects.core.admin.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface AdGroupProjectResponse extends IsIdentified {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.description}")
    String getDescriptions();

    @Value("#{target.background_image}")
    String getBackgroundImage();
}

package com.labreportapp.portalprojects.core.admin.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import com.labreportapp.portalprojects.entity.ProjectCategory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author hieundph25894
 */
@Projection(types = {ProjectCategory.class})
public interface AdProjectCategoryReponse extends IsIdentified {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.projectId}")
    String getIdProject();

    @Value("#{target.categoryId}")
    String getIdCategory();

    @Value("#{target.categoryCode}")
    String getCodeCategory();

    @Value("#{target.categoryName}")
    String getNameCategory();

}

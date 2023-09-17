package com.labreportapp.portalprojects.core.admin.model.response;

import com.labreportapp.portalprojects.entity.Category;
import com.labreportapp.portalprojects.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Category.class})
public interface AdCategoryPesponse extends IsIdentified {
    @Value("#{target.stt}")
    Integer STT();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.name}")
    String getName();
}

package com.labreportapp.labreport.core.admin.model.response;

import com.labreportapp.labreport.entity.Level;
import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author quynhncph26201
 */

@Projection(types = {Level.class})
public interface AdLevelResponse extends IsIdentified {
    @Value("#{target.stt}")
    Integer STT();

    @Value("#{target.name}")
    String getName();
}

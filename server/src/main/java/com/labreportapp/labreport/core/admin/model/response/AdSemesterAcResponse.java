package com.labreportapp.labreport.core.admin.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author quynhncph26201
 */
public interface AdSemesterAcResponse extends IsIdentified {
    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();
}

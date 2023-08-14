package com.labreportapp.core.admin.model.response;

import com.labreportapp.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author quynhncph26201
 */
public interface AdActivityClassResponse extends IsIdentified {
    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();
}

package com.labreportapp.labreport.core.common.base;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface SimpleEntityProjection {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();
}

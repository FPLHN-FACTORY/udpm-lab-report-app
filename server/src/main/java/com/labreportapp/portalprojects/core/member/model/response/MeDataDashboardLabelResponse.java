package com.labreportapp.portalprojects.core.member.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface MeDataDashboardLabelResponse {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.label}")
    Integer getLabel();
}

package com.labreportapp.labreport.core.admin.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author quynhncph26201
 */
public interface AdTeamResponse {
    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.descriptions}")
    String getDescriptions();
}

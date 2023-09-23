package com.labreportapp.labreport.core.admin.model.response;


import org.springframework.beans.factory.annotation.Value;

public interface AdActivityLevelResponse {
    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();
}

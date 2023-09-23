package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.AdUpdateClassConfigurationRequest;
import com.labreportapp.labreport.core.admin.model.response.AdClassConfigurationCustomResponse;
import com.labreportapp.labreport.core.admin.model.response.AdClassConfigurationResponse;
import com.labreportapp.labreport.entity.ClassConfiguration;
import jakarta.validation.Valid;

import java.util.List;

public interface AdCLassConfigurationService {
    List<AdClassConfigurationCustomResponse> getAllClassConfiguration();

    ClassConfiguration updateClassConfiguration(@Valid AdUpdateClassConfigurationRequest adUpdateClassConfigurationRequest);

    ClassConfiguration getOneByIdClassConfiguration(String id);
}

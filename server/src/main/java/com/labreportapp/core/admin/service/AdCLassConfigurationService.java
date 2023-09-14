package com.labreportapp.core.admin.service;

import com.labreportapp.core.admin.model.request.AdUpdateClassConfigurationRequest;
import com.labreportapp.core.admin.model.response.AdClassConfigurationResponse;
import com.labreportapp.entity.ClassConfiguration;
import jakarta.validation.Valid;

import java.util.List;

public interface AdCLassConfigurationService {
    List<AdClassConfigurationResponse> getAllClassConfiguration();

    ClassConfiguration updateClassConfiguration(@Valid AdUpdateClassConfigurationRequest adUpdateClassConfigurationRequest);

    ClassConfiguration getOneByIdClassConfiguration(String id);
}

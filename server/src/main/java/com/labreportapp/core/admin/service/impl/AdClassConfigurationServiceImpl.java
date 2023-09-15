package com.labreportapp.core.admin.service.impl;

import com.labreportapp.core.admin.model.request.AdUpdateClassConfigurationRequest;
import com.labreportapp.core.admin.model.response.AdClassConfigurationResponse;
import com.labreportapp.core.admin.repository.AdClassConfigurationRepository;
import com.labreportapp.core.admin.service.AdCLassConfigurationService;
import com.labreportapp.entity.ClassConfiguration;
import com.labreportapp.infrastructure.constant.Message;
import com.labreportapp.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdClassConfigurationServiceImpl implements AdCLassConfigurationService {
    @Autowired
    private AdClassConfigurationRepository adClassConfigurationRepository;
    @Override
    public List<AdClassConfigurationResponse> getAllClassConfiguration() {
        return adClassConfigurationRepository.getAllClassConfiguration();
    }

    @Override
    public ClassConfiguration updateClassConfiguration(AdUpdateClassConfigurationRequest adUpdateClassConfigurationRequest) {
        Optional<ClassConfiguration> optionalClassConfiguration = adClassConfigurationRepository.findById(adUpdateClassConfigurationRequest.getId());
        if(!optionalClassConfiguration.isPresent()){
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        ClassConfiguration classConfiguration = optionalClassConfiguration.get();
        classConfiguration.setClassSizeMax(adUpdateClassConfigurationRequest.getClassSizeMax());
        return adClassConfigurationRepository.save(classConfiguration);
    }

    @Override
    public ClassConfiguration getOneByIdClassConfiguration(String id) {
        Optional<ClassConfiguration> optionalClassConfiguration = adClassConfigurationRepository.findById(id);
        if (!optionalClassConfiguration.isPresent()){
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        return optionalClassConfiguration.get();
    }
}

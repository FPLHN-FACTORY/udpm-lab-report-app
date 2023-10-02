package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdUpdateClassConfigurationRequest;
import com.labreportapp.labreport.core.admin.model.response.AdClassConfigurationCustomResponse;
import com.labreportapp.labreport.core.admin.model.response.AdClassConfigurationResponse;
import com.labreportapp.labreport.core.admin.repository.AdClassConfigurationRepository;
import com.labreportapp.labreport.core.admin.service.AdCLassConfigurationService;
import com.labreportapp.labreport.entity.ClassConfiguration;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Service
@Validated
public class AdClassConfigurationServiceImpl implements AdCLassConfigurationService {

    @Autowired
    private AdClassConfigurationRepository adClassConfigurationRepository;

    @Override
    public List<AdClassConfigurationCustomResponse> getAllClassConfiguration() {
        AdClassConfigurationResponse classConfigurationResponses = adClassConfigurationRepository.getAllClassConfiguration();
        List<AdClassConfigurationCustomResponse> classConfigurationCustomList = new ArrayList<>();
        if (classConfigurationResponses != null) {
            classConfigurationCustomList.add(new AdClassConfigurationCustomResponse(classConfigurationResponses.getId(), 1, "Số lượng tối thiểu", Double.valueOf(classConfigurationResponses.getClassSizeMin())));
            classConfigurationCustomList.add(new AdClassConfigurationCustomResponse(classConfigurationResponses.getId(), 2, "Số lượng tối đa", Double.valueOf(classConfigurationResponses.getClassSizeMax())));
            classConfigurationCustomList.add(new AdClassConfigurationCustomResponse(classConfigurationResponses.getId(), 3, "Điểm tối thiểu", classConfigurationResponses.getPointMin()));
            classConfigurationCustomList.add(new AdClassConfigurationCustomResponse(classConfigurationResponses.getId(), 4, "Tỉ lệ nghỉ", classConfigurationResponses.getMaximumNumberOfBreaks()));
        }
        return classConfigurationCustomList;
    }

    @Override
    public ClassConfiguration updateClassConfiguration(@Valid AdUpdateClassConfigurationRequest adUpdateClassConfigurationRequest) {
        List<ClassConfiguration> classConfigurationList = adClassConfigurationRepository.findAll();
        if (classConfigurationList == null || classConfigurationList.isEmpty()) {
            ClassConfiguration classConfigurationNew = new ClassConfiguration();
            classConfigurationNew.setClassSizeMax(adUpdateClassConfigurationRequest.getClassSizeMax());
            classConfigurationNew.setClassSizeMin(adUpdateClassConfigurationRequest.getClassSizeMin());
            classConfigurationNew.setPointMin(adUpdateClassConfigurationRequest.getPointMin());
            classConfigurationNew.setMaximumNumberOfBreaks(adUpdateClassConfigurationRequest.getMaximumNumberOfBreaks());
            return adClassConfigurationRepository.save(classConfigurationNew);
        }
        ClassConfiguration optionalClassConfiguration = classConfigurationList.get(0);
        if (optionalClassConfiguration == null) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        optionalClassConfiguration.setClassSizeMax(adUpdateClassConfigurationRequest.getClassSizeMax());
        optionalClassConfiguration.setClassSizeMin(adUpdateClassConfigurationRequest.getClassSizeMin());
        optionalClassConfiguration.setPointMin(adUpdateClassConfigurationRequest.getPointMin());
        optionalClassConfiguration.setMaximumNumberOfBreaks(adUpdateClassConfigurationRequest.getMaximumNumberOfBreaks());
        return adClassConfigurationRepository.save(optionalClassConfiguration);
    }

    @Override
    public ClassConfiguration getOneByIdClassConfiguration() {
        List<ClassConfiguration> classConfigurationList = adClassConfigurationRepository.findAll();
        if (classConfigurationList == null || classConfigurationList.isEmpty()) {
            return null;
        }
        return classConfigurationList.get(0);
    }
}

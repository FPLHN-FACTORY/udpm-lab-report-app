package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdUpdateClassConfigurationRequest;
import com.labreportapp.labreport.core.admin.model.response.AdClassConfigurationCustomResponse;
import com.labreportapp.labreport.core.admin.model.response.AdClassConfigurationResponse;
import com.labreportapp.labreport.core.admin.repository.AdClassConfigurationRepository;
import com.labreportapp.labreport.core.admin.service.AdCLassConfigurationService;
import com.labreportapp.labreport.entity.ClassConfiguration;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdClassConfigurationServiceImpl implements AdCLassConfigurationService {

    @Autowired
    private AdClassConfigurationRepository adClassConfigurationRepository;
    @Override
    public List<AdClassConfigurationCustomResponse> getAllClassConfiguration() {
        AdClassConfigurationResponse classConfigurationResponses = adClassConfigurationRepository.getAllClassConfiguration();
        List<AdClassConfigurationCustomResponse> classConfigurationCustomList = new ArrayList<>();
        if(classConfigurationResponses != null){
            classConfigurationCustomList.add(new AdClassConfigurationCustomResponse(classConfigurationResponses.getId(),1,"Số lượng tối thiểu",Double.valueOf(classConfigurationResponses.getClassSizeMin())));
            classConfigurationCustomList.add(new AdClassConfigurationCustomResponse(classConfigurationResponses.getId(),2,"Số lượng tối đa",Double.valueOf(classConfigurationResponses.getClassSizeMax())));
            classConfigurationCustomList.add(new AdClassConfigurationCustomResponse(classConfigurationResponses.getId(),3,"Điểm tối thiểu",classConfigurationResponses.getPointMin()));
            classConfigurationCustomList.add(new AdClassConfigurationCustomResponse(classConfigurationResponses.getId(),4,"Tỉ lệ nghỉ",classConfigurationResponses.getMaximumNumberOfBreaks()));
        }
        return classConfigurationCustomList;
    }

    @Override
    public ClassConfiguration updateClassConfiguration(AdUpdateClassConfigurationRequest adUpdateClassConfigurationRequest) {
        Optional<ClassConfiguration> optionalClassConfiguration = adClassConfigurationRepository.findById(adUpdateClassConfigurationRequest.getId());
        if(!optionalClassConfiguration.isPresent()){
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        ClassConfiguration classConfiguration = optionalClassConfiguration.get();
        classConfiguration.setClassSizeMax(adUpdateClassConfigurationRequest.getClassSizeMax());
        classConfiguration.setClassSizeMin(adUpdateClassConfigurationRequest.getClassSizeMin());
        classConfiguration.setPointMin(adUpdateClassConfigurationRequest.getPointMin());
        classConfiguration.setMaximumNumberOfBreaks(adUpdateClassConfigurationRequest.getMaximumNumberOfBreaks());
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

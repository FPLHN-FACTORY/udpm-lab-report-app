package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdUpdateClassConfigurationRequest;
import com.labreportapp.labreport.core.admin.model.response.AdClassConfigurationCustomResponse;
import com.labreportapp.labreport.core.admin.model.response.AdClassConfigurationResponse;
import com.labreportapp.labreport.core.admin.repository.AdClassConfigurationRepository;
import com.labreportapp.labreport.core.admin.service.AdCLassConfigurationService;
import com.labreportapp.labreport.entity.ClassConfiguration;
import com.labreportapp.labreport.util.LoggerUtil;
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

    @Autowired
    private LoggerUtil loggerUtil;

    @Override
    public List<AdClassConfigurationCustomResponse> getAllClassConfiguration() {
        AdClassConfigurationResponse classConfigurationResponses = adClassConfigurationRepository.getAllClassConfiguration();
        List<AdClassConfigurationCustomResponse> classConfigurationCustomList = new ArrayList<>();
        if (classConfigurationResponses != null) {
            classConfigurationCustomList.add(new AdClassConfigurationCustomResponse(classConfigurationResponses.getId(), 1, "Số lượng sinh viên trong lớp tối thiểu", Double.valueOf(classConfigurationResponses.getClassSizeMin())));
            classConfigurationCustomList.add(new AdClassConfigurationCustomResponse(classConfigurationResponses.getId(), 2, "Số lượng sinh viên trong lớp tối đa", Double.valueOf(classConfigurationResponses.getClassSizeMax())));
            classConfigurationCustomList.add(new AdClassConfigurationCustomResponse(classConfigurationResponses.getId(), 3, "Điểm tối thiểu (từ 1 -> 10)", classConfigurationResponses.getPointMin()));
            classConfigurationCustomList.add(new AdClassConfigurationCustomResponse(classConfigurationResponses.getId(), 4, "Tỉ lệ nghỉ tối đa (%)", classConfigurationResponses.getMaximumNumberOfBreaks()));
            classConfigurationCustomList.add(new AdClassConfigurationCustomResponse(classConfigurationResponses.getId(), 5, "Số lượng lớp học tối đa sinh viên có thể tham gia trong 1 học kỳ", Double.valueOf(classConfigurationResponses.getNumberClassMax())));
            classConfigurationCustomList.add(new AdClassConfigurationCustomResponse(classConfigurationResponses.getId(), 6, "Số lượng mật ong", Double.valueOf(classConfigurationResponses.getNumberHoney())));
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
            classConfigurationNew.setNumberHoney(adUpdateClassConfigurationRequest.getNumberHoney());
            classConfigurationNew.setNumberClassMax(adUpdateClassConfigurationRequest.getNumberClassMax());
            return adClassConfigurationRepository.save(classConfigurationNew);
        }
        ClassConfiguration optionalClassConfiguration = classConfigurationList.get(0);
        if (optionalClassConfiguration == null) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Đã cập nhật cấu hình lớp học: ");
        if (!optionalClassConfiguration.getClassSizeMax().equals(adUpdateClassConfigurationRequest.getClassSizeMax())) {
            stringBuilder.append(" số lượng tối đa từ ").append(optionalClassConfiguration.getClassSizeMax()).append(" thành ").append(adUpdateClassConfigurationRequest.getClassSizeMax())
                    .append(",");
        }
        if (!optionalClassConfiguration.getClassSizeMin().equals(adUpdateClassConfigurationRequest.getClassSizeMin())) {
            stringBuilder.append(" số lượng thiểu từ ").append(optionalClassConfiguration.getClassSizeMin()).append(" thành ").append(adUpdateClassConfigurationRequest.getClassSizeMin())
                    .append(",");
        }
        if (!optionalClassConfiguration.getPointMin().equals(adUpdateClassConfigurationRequest.getPointMin())) {
            stringBuilder.append(" điểm tối thiểu từ ").append(optionalClassConfiguration.getPointMin()).append(" thành ").append(adUpdateClassConfigurationRequest.getPointMin())
                    .append(",");
        }
        if (!optionalClassConfiguration.getMaximumNumberOfBreaks().equals(adUpdateClassConfigurationRequest.getMaximumNumberOfBreaks())) {
            stringBuilder.append(" tỉ lệ nghỉ từ ").append(optionalClassConfiguration.getMaximumNumberOfBreaks()).append("% thành ").append(adUpdateClassConfigurationRequest.getMaximumNumberOfBreaks()).append("%")
                    .append(",");
        }
        if (!optionalClassConfiguration.getMaximumNumberOfBreaks().equals(adUpdateClassConfigurationRequest.getMaximumNumberOfBreaks())) {
            stringBuilder.append(" số lượng mật ong từ ").append(optionalClassConfiguration.getNumberHoney()).append(" thành ").append(adUpdateClassConfigurationRequest.getNumberHoney())
                    .append(".");
        }
        if (stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
            stringBuilder.setCharAt(stringBuilder.length() - 1, '.');
        }
        loggerUtil.sendLogScreen(stringBuilder.toString(), "");
        optionalClassConfiguration.setClassSizeMax(adUpdateClassConfigurationRequest.getClassSizeMax());
        optionalClassConfiguration.setClassSizeMin(adUpdateClassConfigurationRequest.getClassSizeMin());
        optionalClassConfiguration.setPointMin(adUpdateClassConfigurationRequest.getPointMin());
        optionalClassConfiguration.setMaximumNumberOfBreaks(adUpdateClassConfigurationRequest.getMaximumNumberOfBreaks());
        optionalClassConfiguration.setNumberHoney(adUpdateClassConfigurationRequest.getNumberHoney());
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

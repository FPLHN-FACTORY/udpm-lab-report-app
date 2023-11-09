package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdDashboardLabReportAppRequest;
import com.labreportapp.labreport.core.admin.model.response.AdDashboardLabReportAppResponse;
import com.labreportapp.labreport.core.admin.repository.AdDashboardLabReportAppRepository;
import com.labreportapp.labreport.core.admin.service.AdDashboardLabReportAppService;
import com.labreportapp.labreport.entity.ClassConfiguration;
import com.labreportapp.labreport.repository.ClassConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author todo thangncph26123
 */
@Service
@Validated
public class AdDashboardLabReportAppServiceImpl implements AdDashboardLabReportAppService {

    @Autowired
    private AdDashboardLabReportAppRepository adDashboardLabReportAppRepository;

    @Autowired
    @Qualifier(ClassConfigurationRepository.NAME)
    private ClassConfigurationRepository classConfigurationRepository;

    @Override
    public AdDashboardLabReportAppResponse dashboard(final AdDashboardLabReportAppRequest request) {
        ClassConfiguration classConfiguration = classConfigurationRepository.findAll().get(0);

        return null;
    }
}

package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.service.AdProjectStatisticsService;
import com.labreportapp.portalprojects.core.admin.repository.AdProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hieundph25894-duchieu212
 */
@Service
public class AdProjectStatisticsServiceImpl implements AdProjectStatisticsService {

    @Autowired
    private AdProjectRepository adProjectRepository;
}

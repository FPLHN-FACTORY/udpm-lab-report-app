package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.AdFindProjectStatisticTopRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindProjectStatisticsRequest;
import com.labreportapp.labreport.core.admin.service.AdProjectStatisticsService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hieundph25894-duchieu212
 */
@RestController
@RequestMapping("/api/admin/project-statistics")
public class AdProjectStatisticsController {

    @Autowired
    private AdProjectStatisticsService adProjectStatisticsService;

    @GetMapping
    public ResponseObject getAllProjectDuAn(final AdFindProjectStatisticsRequest request) {
        return new ResponseObject(adProjectStatisticsService.findAllProjectStatisticTypeXuong(request));
    }

    @GetMapping("/type-project")
    public ResponseObject getProjectFindTable(final AdFindProjectStatisticTopRequest request) {
        return new ResponseObject(adProjectStatisticsService.getProjectFindTop(request));
    }
}

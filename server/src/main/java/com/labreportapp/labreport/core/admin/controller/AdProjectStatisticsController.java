package com.labreportapp.labreport.core.admin.controller;

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
@RequestMapping("/admin/project-statistics")
@CrossOrigin(origins = {"*"}, maxAge = -1)
public class AdProjectStatisticsController {

    @Autowired
    private AdProjectStatisticsService adProjectStatisticsService;

    @GetMapping
    public ResponseObject getPage(final AdFindProjectStatisticsRequest request) {
        return new ResponseObject(request);
    }
}

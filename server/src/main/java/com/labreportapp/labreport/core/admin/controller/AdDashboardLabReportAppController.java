package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.service.AdDashboardLabReportAppService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author todo thangncph26123
 */

@RestController
@RequestMapping("/admin/dashboard-lab-report-app")
@CrossOrigin("*")
public class AdDashboardLabReportAppController {

    @Autowired
    private AdDashboardLabReportAppService adDashboardLabReportAppService;

    @GetMapping
    public ResponseObject dashboard() {
        return new ResponseObject(null);
    }
}

package com.labreportapp.labreport.core.admin.controller;


import com.labreportapp.labreport.core.admin.model.request.AdUpdateClassConfigurationRequest;
import com.labreportapp.labreport.core.admin.service.AdCLassConfigurationService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/class-configuration")
@CrossOrigin(origins = {"*"})
public class AdClassConfigurationController {
    @Autowired
    private AdCLassConfigurationService adCLassConfigurationService;

    @GetMapping("")
    public ResponseObject viewClassConfiguration() {
        return new ResponseObject(adCLassConfigurationService.getAllClassConfiguration());
    }

    @GetMapping("/detail")
    public ResponseObject getOne() {
        return new ResponseObject(adCLassConfigurationService.getOneByIdClassConfiguration());
    }

    @PutMapping
    public ResponseObject updateClassConfiguration(@RequestBody AdUpdateClassConfigurationRequest adUpdateClassConfigurationRequest) {
        return new ResponseObject(adCLassConfigurationService.updateClassConfiguration(adUpdateClassConfigurationRequest));
    }
}

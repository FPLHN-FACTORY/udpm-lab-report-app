package com.labreportapp.labreport.core.admin.controller;


import com.labreportapp.labreport.core.admin.model.request.AdUpdateClassConfigurationRequest;
import com.labreportapp.labreport.core.admin.service.AdCLassConfigurationService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/class-configuration")
@CrossOrigin(origins = {"*"})
public class AdClassConfigurationController {
    @Autowired
    private AdCLassConfigurationService adCLassConfigurationService;

    @GetMapping("")
    public ResponseObject viewClassConfiguration(){
        return new ResponseObject(adCLassConfigurationService.getAllClassConfiguration());
    }

    @GetMapping("/{id}")
    public ResponseObject getOne(@PathVariable("id")String id){
        return new ResponseObject(adCLassConfigurationService.getOneByIdClassConfiguration(id));
    }
    @PutMapping("/{id}")
    public ResponseObject updateClassConfiguration(@PathVariable("id") String id, @RequestBody AdUpdateClassConfigurationRequest adUpdateClassConfigurationRequest){
        adUpdateClassConfigurationRequest.setId(id);
        return new ResponseObject(adCLassConfigurationService.updateClassConfiguration(adUpdateClassConfigurationRequest));
    }
}

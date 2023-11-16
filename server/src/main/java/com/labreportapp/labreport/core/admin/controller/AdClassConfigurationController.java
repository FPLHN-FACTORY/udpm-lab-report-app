package com.labreportapp.labreport.core.admin.controller;


import com.labreportapp.labreport.core.admin.model.request.AdUpdateClassConfigurationRequest;
import com.labreportapp.labreport.core.admin.service.AdCLassConfigurationService;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.infrastructure.logger.LoggerObject;
import com.labreportapp.labreport.util.CallApiConsumer;
import com.labreportapp.labreport.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/class-configuration")
public class AdClassConfigurationController {

    @Autowired
    private AdCLassConfigurationService adCLassConfigurationService;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private CallApiConsumer callApiConsumer;

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

    @GetMapping("/download-log")
    public ResponseEntity<Resource> downloadCsv() {
        String pathFile = loggerUtil.getPathFileSendLogScreen("");
        return callApiConsumer.handleCallApiDowloadFileLog(pathFile);
    }

    @GetMapping("/history")
    public ResponseEntity<?> showHistory(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", defaultValue = "50") Integer size
    ) {
        String pathFile = loggerUtil.getPathFileSendLogScreen("");
        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setPathFile(pathFile);
        return new ResponseEntity<>(callApiConsumer.handleCallApiReadFileLog(loggerObject, page, size), HttpStatus.OK);
    }
}

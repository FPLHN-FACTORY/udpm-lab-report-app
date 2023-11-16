package com.labreportapp.portalprojects.core.admin.controller;

import com.labreportapp.labreport.infrastructure.logger.LoggerObject;
import com.labreportapp.labreport.util.CallApiConsumer;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.portalprojects.core.admin.model.request.AdCreatLabelRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdFindLabelRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateLabelRequest;
import com.labreportapp.portalprojects.core.admin.service.AdLabelService;
import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NguyenVinh
 */
@RestController
@RequestMapping("/admin/label")
public class AdLabelController {

    @Autowired
    private AdLabelService adLabelService;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private CallApiConsumer callApiConsumer;

    @GetMapping("")
    public ResponseObject viewLabel(@ModelAttribute final AdFindLabelRequest request) {
        return new ResponseObject(adLabelService.searchLabel(request));
    }

    @PostMapping
    public ResponseObject creatLabel(@RequestBody AdCreatLabelRequest request) {
        return new ResponseObject(adLabelService.creatLabel(request));
    }

    @DeleteMapping("/{id}")
    public ResponseObject deleteLabel(@PathVariable("id") String id) {
        return new ResponseObject(adLabelService.deleteLabel(id));
    }

    @PutMapping("/{id}")
    public ResponseObject updateLabel(@PathVariable("id") String id,
                                      @RequestBody AdUpdateLabelRequest request) {
        return new ResponseObject(adLabelService.updateLabel(request));
    }

    @GetMapping("/{id}")
    public ResponseObject getOne(@PathVariable("id") String id) {
        return new ResponseObject(adLabelService.getOneByIdLable(id));
    }

    @GetMapping("/label-status/{status}")
    public ResponseObject getAllIdByStatus(@PathVariable("status") String status) {
        return new ResponseObject(adLabelService.getAllIdByStatus(status));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject deleteLabelManage(@PathVariable("id") String id) {
        return new ResponseObject(adLabelService.deleteLabelById(id));
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

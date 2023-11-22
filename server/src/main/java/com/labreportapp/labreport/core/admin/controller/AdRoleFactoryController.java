package com.labreportapp.labreport.core.admin.controller;

import com.labreportapp.labreport.core.admin.model.request.*;
import com.labreportapp.labreport.core.admin.model.response.AdRoleFactoryResponse;
import com.labreportapp.labreport.core.admin.service.AdRoleFactoryService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.entity.RoleFactory;
import com.labreportapp.labreport.infrastructure.logger.LoggerObject;
import com.labreportapp.labreport.util.CallApiConsumer;
import com.labreportapp.labreport.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/api/admin/role-factory")
public class AdRoleFactoryController {

    @Autowired
    private AdRoleFactoryService adRoleFactoryService;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private CallApiConsumer callApiConsumer;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> getAllRoleFactory(@PathVariable int page) {
        Pageable pageResquest = PageRequest.of(page - 1, 5);
        List<RoleFactory> RoleFactoryList = adRoleFactoryService.findAllRoleFactory(pageResquest);
        return ResponseEntity.ok(RoleFactoryList);
    }

    @GetMapping("")
    public ResponseObject viewRoleFactory(@ModelAttribute final AdFindRoleFactoryRequest request) {
        return new ResponseObject((adRoleFactoryService.searchRoleFactory(request)));
    }

    @GetMapping("/search")
    public ResponseObject searchRoleFactory(final AdFindRoleFactoryRequest request) {
        PageableObject<AdRoleFactoryResponse> listRoleFactory = adRoleFactoryService.searchRoleFactory(request);
        return new ResponseObject(listRoleFactory);
    }

    @PostMapping("/add")
    public ResponseObject addRoleFactory(@RequestBody AdCreateRoleFactoryRequest obj) {
        return new ResponseObject(adRoleFactoryService.createRoleFactory(obj));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject deleteRoleFactory(@PathVariable("id") String id) {
        return new ResponseObject(adRoleFactoryService.deleteRoleFactory(id));
    }

    @PutMapping("/update/{id}")
    public ResponseObject updateRoleFactory(@PathVariable("id") String id,
                                     @RequestBody AdUpdateRoleFactoryRequest obj) {
        obj.setId(id);
        return new ResponseObject(adRoleFactoryService.updateRoleFactory(obj));
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

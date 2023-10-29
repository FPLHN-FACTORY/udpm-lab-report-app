package com.labreportapp.labreport.core.student.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.student.model.request.StFindMemberFactoryRequest;
import com.labreportapp.labreport.core.student.service.StMemberFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/student/member-factory")
@CrossOrigin(origins = {"*"}, maxAge = -1)
public class StMemberFactoryController {

    @Autowired
    private StMemberFactoryService stMemberFactoryService;

    @GetMapping
    public ResponseObject getPage(final StFindMemberFactoryRequest request) {
        return new ResponseObject(stMemberFactoryService.getPage(request));
    }

    @GetMapping("/roles")
    public ResponseObject getRoles() {
        return new ResponseObject(stMemberFactoryService.getRoles());
    }

    @GetMapping("/teams")
    public ResponseObject getTeams() {
        return new ResponseObject(stMemberFactoryService.getTeams());
    }

    @GetMapping("/number-member-factory")
    public ResponseObject getNumberMemberFactory() {
        return new ResponseObject(stMemberFactoryService.getNumberMemberFactory());
    }

}

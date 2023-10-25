package com.labreportapp.labreport.core.teacher.controller;

import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.core.teacher.model.request.TeFindMemberFactoryRequest;
import com.labreportapp.labreport.core.teacher.service.TeMemberFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author quynhncph26201
 */
@RestController
@RequestMapping("/teacher/member-factory")
@CrossOrigin(origins = {"*"}, maxAge = -1)
public class TeMemberFactoryController {

    @Autowired
    private TeMemberFactoryService teMemberFactoryService;

    @GetMapping
    public ResponseObject getPage(final TeFindMemberFactoryRequest request) {
        return new ResponseObject(teMemberFactoryService.getPage(request));
    }

    @GetMapping("/roles")
    public ResponseObject getRoles() {
        return new ResponseObject(teMemberFactoryService.getRoles());
    }

    @GetMapping("/teams")
    public ResponseObject getTeams() {
        return new ResponseObject(teMemberFactoryService.getTeams());
    }

    @GetMapping("/number-member-factory")
    public ResponseObject getNumberMemberFactory() {
        return new ResponseObject(teMemberFactoryService.getNumberMemberFactory());
    }
}

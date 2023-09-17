package com.labreportapp.portalprojects.core.member.controller;

import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import com.labreportapp.portalprojects.core.member.model.request.MeFindActivityRequest;
import com.labreportapp.portalprojects.core.member.service.MeActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/member/activity")
@CrossOrigin("*")
public class MeActivityController {

    @Autowired
    private MeActivityService meActivityService;

    @GetMapping
    public ResponseObject getAll(final MeFindActivityRequest request){
        return new ResponseObject(meActivityService.getAll(request));
    }
}

package com.labreportapp.portalprojects.core.member.controller;

import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import com.labreportapp.portalprojects.core.member.model.request.DesVarProjectIdAndPeriodIdRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateResourceRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeDeleteResourceRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateResourceRequest;
import com.labreportapp.portalprojects.core.member.service.MeResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/member/resource")
public class MeResourceController {

    @Autowired
    private MeResourceService meResourceService;

    @GetMapping
    public ResponseObject getAll(@RequestParam("idTodo") String idTodo) {
        return new ResponseObject(meResourceService.getAll(idTodo));
    }

    @MessageMapping("/create-resource/{projectId}/{periodId}")
    @SendTo("/portal-projects/create-resource/{projectId}/{periodId}")
    public ResponseObject create(@RequestBody MeCreateResourceRequest request,
                                 @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                 StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meResourceService.create(request, headerAccessor));
    }

    @MessageMapping("/update-resource/{projectId}/{periodId}")
    @SendTo("/portal-projects/update-resource/{projectId}/{periodId}")
    public ResponseObject update(@RequestBody MeUpdateResourceRequest request,
                                 @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                 StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meResourceService.update(request, headerAccessor));
    }

    @MessageMapping("/delete-resource/{projectId}/{periodId}")
    @SendTo("/portal-projects/delete-resource/{projectId}/{periodId}")
    public ResponseObject delete(@RequestBody MeDeleteResourceRequest request,
                                 @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                 StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meResourceService.delete(request, headerAccessor));
    }
}

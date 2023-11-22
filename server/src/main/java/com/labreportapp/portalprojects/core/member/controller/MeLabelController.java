package com.labreportapp.portalprojects.core.member.controller;

import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateLabelProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeDeleteLabelProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateLabelProjectRequest;
import com.labreportapp.portalprojects.core.member.service.MeLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/api/member/label")
public class MeLabelController {

    @Autowired
    private MeLabelService meLabelService;

    @GetMapping
    public ResponseObject getAllLabelByIdTodo(@RequestParam("idTodo") String idTodo) {
        return new ResponseObject(meLabelService.getAllLabelByIdTodo(idTodo));
    }

    @GetMapping("/list")
    public ResponseObject getAll(@RequestParam("idProject") String idProject) {
        return new ResponseObject(meLabelService.getAll(idProject));
    }

    @GetMapping("/detail/{id}")
    public ResponseObject detail(@PathVariable("id") String id) {
        return new ResponseObject(meLabelService.detail(id));
    }

    @MessageMapping("/create-label/{projectId}")
    @SendTo("/portal-projects/create-label/{projectId}")
    public ResponseObject create(@RequestBody MeCreateLabelProjectRequest request,
                                 @DestinationVariable String projectId,
                                 StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meLabelService.create(request, headerAccessor));
    }

    @MessageMapping("/update-label/{projectId}")
    @SendTo("/portal-projects/update-label/{projectId}")
    public ResponseObject update(@RequestBody MeUpdateLabelProjectRequest request,
                                 @DestinationVariable String projectId,
                                 StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meLabelService.update(request, headerAccessor));
    }

    @MessageMapping("/delete-label/{projectId}")
    @SendTo("/portal-projects/delete-label/{projectId}")
    public ResponseObject delete(@RequestBody MeDeleteLabelProjectRequest request,
                                 @DestinationVariable String projectId,
                                 StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meLabelService.delete(request, headerAccessor));
    }

}

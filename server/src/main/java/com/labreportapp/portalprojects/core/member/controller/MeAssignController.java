package com.labreportapp.portalprojects.core.member.controller;

import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import com.labreportapp.portalprojects.core.member.model.request.DesVarProjectIdAndPeriodIdRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateOrDeleteAssignRequest;
import com.labreportapp.portalprojects.core.member.service.MeAssignService;
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
@RequestMapping("/member/assign")
public class MeAssignController {

    @Autowired
    private MeAssignService meAssignService;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @GetMapping
    public ResponseObject getMemberByIdTodo(@RequestParam("idTodo") String idTodo) {
        return new ResponseObject(meAssignService.getAllMemberByIdTodo(idTodo));
    }

    @MessageMapping("/create-assign/{projectId}/{periodId}")
    @SendTo("/portal-projects/assign/{projectId}/{periodId}")
    public ResponseObject create(@RequestBody MeCreateOrDeleteAssignRequest request,
                                 @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                 StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meAssignService.create(request, headerAccessor));
    }


    @MessageMapping("/delete-assign/{projectId}/{periodId}")
    @SendTo("/portal-projects/assign/{projectId}/{periodId}")
    public ResponseObject delete(@RequestBody MeCreateOrDeleteAssignRequest request,
                                 @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                 StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meAssignService.delete(request, headerAccessor));
    }

    @MessageMapping("/join-assign/{projectId}/{periodId}")
    @SendTo("/portal-projects/assign/{projectId}/{periodId}")
    public ResponseObject joinAssign(@RequestBody MeCreateOrDeleteAssignRequest request,
                                     @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                     StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meAssignService.create(request, headerAccessor));
    }

    @MessageMapping("/out-assign/{projectId}/{periodId}")
    @SendTo("/portal-projects/assign/{projectId}/{periodId}")
    public ResponseObject outAssign(@RequestBody MeCreateOrDeleteAssignRequest request,
                                    @ModelAttribute DesVarProjectIdAndPeriodIdRequest des,
                                    StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meAssignService.delete(request, headerAccessor));
    }
}

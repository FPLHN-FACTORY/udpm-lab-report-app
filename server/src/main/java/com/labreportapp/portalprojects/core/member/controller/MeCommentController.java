package com.labreportapp.portalprojects.core.member.controller;

import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import com.labreportapp.portalprojects.core.member.model.request.DesVarProjectIdAndPeriodIdRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateCommentRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeDeleteCommentRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeFindCommentRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateCommentRequest;
import com.labreportapp.portalprojects.core.member.service.MeCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/member/comment")
@CrossOrigin("*")
public class MeCommentController {

    @Autowired
    private MeCommentService meCommentService;

    @MessageMapping("/create-comment/{projectId}/{periodId}")
    @SendTo("/portal-projects/create-comment/{projectId}/{periodId}")
    public ResponseObject add(@RequestBody MeCreateCommentRequest request,
                              @ModelAttribute DesVarProjectIdAndPeriodIdRequest des) {
        return new ResponseObject(meCommentService.add(request));
    }

    @GetMapping()
    public ResponseObject getAllCommentByIdTodo(final MeFindCommentRequest request){
        return new ResponseObject(meCommentService.getAllCommentByIdTodo(request));
    }

    @MessageMapping("/update-comment/{projectId}/{periodId}")
    @SendTo("/portal-projects/update-comment/{projectId}/{periodId}")
    public ResponseObject update(@RequestBody MeUpdateCommentRequest request,
                              @ModelAttribute DesVarProjectIdAndPeriodIdRequest des) {
        return new ResponseObject(meCommentService.update(request));
    }

    @MessageMapping("/delete-comment/{projectId}/{periodId}")
    @SendTo("/portal-projects/delete-comment/{projectId}/{periodId}")
    public ResponseObject delete(@RequestBody MeDeleteCommentRequest request,
                                 @ModelAttribute DesVarProjectIdAndPeriodIdRequest des) {
        return new ResponseObject(meCommentService.delete(request));
    }
}

package com.labreportapp.portalprojects.core.member.controller;

import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import com.labreportapp.portalprojects.core.member.model.request.FindHistoryProgressBarRequest;
import com.labreportapp.portalprojects.core.member.service.MeHistoryProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/member/history-progress")
@CrossOrigin("*")
public class MeHistoryProgressController {

    @Autowired
    private MeHistoryProgressService meHistoryProgressService;

    @GetMapping()
    public ResponseObject getAllCommentByIdTodo(final FindHistoryProgressBarRequest request){
        return new ResponseObject(meHistoryProgressService.getAllHistoryProgressByIdProject(request));
    }
}

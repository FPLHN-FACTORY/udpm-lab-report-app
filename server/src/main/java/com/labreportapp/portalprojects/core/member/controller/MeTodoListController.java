package com.labreportapp.portalprojects.core.member.controller;

import com.labreportapp.portalprojects.core.common.base.ResponseObject;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateTodoListRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeDeleteTodoListRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateNameTodoListRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateTodoListRequest;
import com.labreportapp.portalprojects.core.member.service.MeTodoListService;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping("/member/todo-list")
@CrossOrigin("*")
public class MeTodoListController {

    @Autowired
    private MeTodoListService meTodoListService;

    @GetMapping("/{id}")
    public ResponseObject getAllTodoList(@PathVariable("id") String id) {
        return new ResponseObject(meTodoListService.getAllTodoList(id));
    }

    @MessageMapping("/update-index-todo-list/{projectId}")
    @SendTo("/portal-projects/update-index-todo-list/{projectId}")
    public ResponseObject updateIndexTodoList(@RequestBody MeUpdateTodoListRequest request,
                                              @DestinationVariable String projectId) {
        request.setIdProject(projectId);
        return new ResponseObject(meTodoListService.updateIndexTodoList(request));
    }

    @MessageMapping("/create-todo-list/{projectId}")
    @SendTo("/portal-projects/create-todo-list/{projectId}")
    public ResponseObject createTodoList(@RequestBody MeCreateTodoListRequest request,
                                         @DestinationVariable String projectId,
                                         StompHeaderAccessor headerAccessor) {
        return new ResponseObject(meTodoListService.createTodoList(request, headerAccessor));
    }

    @MessageMapping("/update-name-todo-list/{projectId}")
    @SendTo("/portal-projects/update-name-todo-list/{projectId}")
    public ResponseObject updateNameTodoList(@RequestBody MeUpdateNameTodoListRequest request,
                                         @DestinationVariable String projectId) {
        return new ResponseObject(meTodoListService.updateNameTodoList(request));
    }

    @MessageMapping("/delete-todo-list/{projectId}")
    @SendTo("/portal-projects/delete-todo-list/{projectId}")
    public ResponseObject deleteTodoList(@RequestBody MeDeleteTodoListRequest request,
                                         @DestinationVariable String projectId) {
        return new ResponseObject(meTodoListService.deleteTodoList(request));
    }
}

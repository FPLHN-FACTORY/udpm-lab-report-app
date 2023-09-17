package com.labreportapp.portalprojects.core.member.service;

import com.labreportapp.portalprojects.core.common.base.TodoAndTodoListObject;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateTodoListRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeDeleteTodoListRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateNameTodoListRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateTodoListRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeTodoListResponse;
import com.labreportapp.portalprojects.entity.TodoList;
import jakarta.validation.Valid;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.util.List;

/**
 * @author thangncph26123
 */

public interface MeTodoListService {

    List<MeTodoListResponse> getAllTodoList(String idProject);

    TodoAndTodoListObject updateIndexTodoList(@Valid MeUpdateTodoListRequest request);

    TodoList createTodoList(@Valid MeCreateTodoListRequest request, StompHeaderAccessor headerAccessor);

    TodoList updateNameTodoList(@Valid MeUpdateNameTodoListRequest request);

    String deleteTodoList(@Valid MeDeleteTodoListRequest request);
}

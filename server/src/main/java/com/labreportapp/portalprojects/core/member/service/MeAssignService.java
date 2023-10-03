package com.labreportapp.portalprojects.core.member.service;

import com.labreportapp.portalprojects.core.common.base.TodoObject;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateOrDeleteAssignRequest;
import jakarta.validation.Valid;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface MeAssignService {

    List<String> getAllMemberByIdTodo(String idTodo);

    TodoObject create(@Valid MeCreateOrDeleteAssignRequest request, StompHeaderAccessor headerAccessor);

    TodoObject delete(@Valid MeCreateOrDeleteAssignRequest request, StompHeaderAccessor headerAccessor);

}

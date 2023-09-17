package com.labreportapp.portalprojects.core.member.service;

import com.labreportapp.portalprojects.core.common.base.TodoObject;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateResourceRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeDeleteResourceRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateResourceRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeResourceResponse;
import jakarta.validation.Valid;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface MeResourceService {

    List<MeResourceResponse> getAll(String idTodo);

    TodoObject create(@Valid MeCreateResourceRequest request, StompHeaderAccessor headerAccessor);

    TodoObject update(@Valid MeUpdateResourceRequest request, StompHeaderAccessor headerAccessor);

    TodoObject delete(@Valid MeDeleteResourceRequest request, StompHeaderAccessor headerAccessor);
}

package com.labreportapp.portalprojects.core.member.service;

import com.labreportapp.portalprojects.core.common.base.PageableObject;
import com.labreportapp.portalprojects.core.common.base.TodoObject;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateCommentRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeDeleteCommentRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeFindCommentRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateCommentRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeCommentResponse;
import jakarta.validation.Valid;

/**
 * @author thangncph26123
 */
public interface MeCommentService {

    TodoObject add(@Valid MeCreateCommentRequest request);

    PageableObject<MeCommentResponse> getAllCommentByIdTodo(MeFindCommentRequest request);

    TodoObject update(@Valid MeUpdateCommentRequest request);

    TodoObject delete(@Valid MeDeleteCommentRequest request);
}

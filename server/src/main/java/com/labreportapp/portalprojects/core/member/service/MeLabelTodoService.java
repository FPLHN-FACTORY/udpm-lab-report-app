package com.labreportapp.portalprojects.core.member.service;

import com.labreportapp.portalprojects.core.common.base.TodoObject;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateOrDeleteLabelTodoRequest;

/**
 * @author thangncph26123
 */
public interface MeLabelTodoService {

    TodoObject create(MeCreateOrDeleteLabelTodoRequest request);

    TodoObject delete(MeCreateOrDeleteLabelTodoRequest request);
}

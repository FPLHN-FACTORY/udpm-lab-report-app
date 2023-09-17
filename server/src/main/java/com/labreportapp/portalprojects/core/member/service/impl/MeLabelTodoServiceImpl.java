package com.labreportapp.portalprojects.core.member.service.impl;

import com.labreportapp.portalprojects.core.common.base.TodoObject;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateOrDeleteLabelTodoRequest;
import com.labreportapp.portalprojects.core.member.repository.MeLabelTodoRepository;
import com.labreportapp.portalprojects.core.member.service.MeLabelTodoService;
import com.labreportapp.portalprojects.entity.LabelProjectTodo;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.MessageHandlingException;
import jakarta.transaction.Transactional;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class MeLabelTodoServiceImpl implements MeLabelTodoService {

    @Autowired
    private MeLabelTodoRepository meLabelTodoRepository;

    @Override
    @Synchronized
    @CacheEvict(value = {"allLabelsByProject", "todosByPeriodAndTodoList", "todosByFilter"}, allEntries = true)
    public TodoObject create(MeCreateOrDeleteLabelTodoRequest request) {
        LabelProjectTodo labelTodoFind = meLabelTodoRepository.findByLabelProjectIdAndTodoId(request.getIdLabel(), request.getIdTodo());
        if (labelTodoFind != null) {
            throw new MessageHandlingException(Message.LABEL_PROJECT_TODO_EXISTS);
        }
        LabelProjectTodo labelTodo = new LabelProjectTodo();
        labelTodo.setTodoId(request.getIdTodo());
        labelTodo.setLabelProjectId(request.getIdLabel());
        TodoObject todoObject = TodoObject.builder().data(meLabelTodoRepository.save(labelTodo)).idTodoList(request.getIdTodoList()).idTodo(request.getIdTodo()).build();
        return todoObject;
    }

    @Override
    @Synchronized
    @CacheEvict(value = {"allLabelsByProject", "todosByPeriodAndTodoList", "todosByFilter"}, allEntries = true)
    @Transactional
    public TodoObject delete(MeCreateOrDeleteLabelTodoRequest request) {
        LabelProjectTodo labelTodoFind = meLabelTodoRepository.findByLabelProjectIdAndTodoId(request.getIdLabel(), request.getIdTodo());
        if (labelTodoFind == null) {
            throw new MessageHandlingException(Message.LABEL_PROJECT_TODO_NOT_EXISTS);
        }
        meLabelTodoRepository.delete(request.getIdLabel(), request.getIdTodo());
        TodoObject todoObject = TodoObject.builder().data(request.getIdLabel()).idTodoList(request.getIdTodoList()).idTodo(request.getIdTodo()).build();
        return todoObject;
    }
}

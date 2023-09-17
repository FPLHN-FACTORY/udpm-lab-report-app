package com.labreportapp.portalprojects.core.member.service.impl;

import com.labreportapp.portalprojects.core.common.base.TodoAndTodoListObject;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateTodoListRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeDeleteTodoListRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateNameTodoListRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateTodoListRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeTodoListResponse;
import com.labreportapp.portalprojects.core.member.repository.MeTodoListRepository;
import com.labreportapp.portalprojects.core.member.service.MeTodoListService;
import com.labreportapp.portalprojects.entity.TodoList;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.MessageHandlingException;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import com.labreportapp.portalprojects.infrastructure.successnotification.ConstantMessageSuccess;
import com.labreportapp.portalprojects.infrastructure.successnotification.SuccessNotificationSender;
import com.labreportapp.portalprojects.util.TodoListHelper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class MeTodoListServiceImpl implements MeTodoListService {

    @Autowired
    private MeTodoListRepository meTodoListRepository;

    @Autowired
    private TodoListHelper todoListHelper;

    @Autowired
    private SuccessNotificationSender successNotificationSender;

    @Override
    @Cacheable(value = "todoListByProject", key = "#idProject")
    public List<MeTodoListResponse> getAllTodoList(String idProject) {
        return meTodoListRepository.getAllTodoList(idProject);
    }

    @Override
    @Synchronized
    @CacheEvict(value = {"todoListByProject", "todosByPeriodAndTodoList"}, allEntries = true)
    @Transactional
    public TodoAndTodoListObject updateIndexTodoList(@Valid MeUpdateTodoListRequest request) {
        if (Integer.parseInt(request.getIndexBefore()) == Integer.parseInt(request.getIndexAfter())) {
            throw new RestApiException(Message.ERROR_UNKNOWN);
        }
        if (Integer.parseInt(request.getIndexBefore()) < Integer.parseInt(request.getIndexAfter())) {
            meTodoListRepository.updateIndexTodoListDecs(Integer.parseInt(request.getIndexBefore()), Integer.parseInt(request.getIndexAfter()), request.getIdProject());
        } else {
            meTodoListRepository.updateIndexTodoListAsc(Integer.parseInt(request.getIndexBefore()), Integer.parseInt(request.getIndexAfter()), request.getIdProject());
        }
        meTodoListRepository.updateIndexTodoList(request.getIdTodoList(), Integer.parseInt(request.getIndexAfter()));
        return TodoAndTodoListObject.builder().data(request.getIdTodoList())
                .indexBefore(Integer.parseInt(request.getIndexBefore()))
                .indexAfter(Integer.parseInt(request.getIndexAfter()))
                .sessionId(request.getSessionId())
                .build();
    }

    @Override
    @Synchronized
    @CacheEvict(value = {"todoListByProject", "todosByPeriodAndTodoList"}, allEntries = true)
    public TodoList createTodoList(@Valid MeCreateTodoListRequest request, StompHeaderAccessor headerAccessor) {
        TodoList todoList = new TodoList();
        todoList.setCode(todoListHelper.genCodeTodoList(request.getIdProject()));
        todoList.setName(request.getName());
        todoList.setIndexTodoList(todoListHelper.genIndexTodoList(request.getIdProject()));
        todoList.setProjectId(request.getIdProject());
        TodoList newTodoList = meTodoListRepository.save(todoList);
        successNotificationSender.senderNotification(ConstantMessageSuccess.THEM_THANH_CONG, headerAccessor);
        return newTodoList;
    }

    @Override
    @Synchronized
    @CacheEvict(value = {"todoListByProject", "todosByPeriodAndTodoList"}, allEntries = true)
    @Transactional
    public TodoList updateNameTodoList(@Valid MeUpdateNameTodoListRequest request) {
        Optional<TodoList> todoListFind = meTodoListRepository.findById(request.getIdTodoList());
        if (!todoListFind.isPresent()) {
            throw new MessageHandlingException(Message.TODO_LIST_NOT_EXISTS);
        }
        todoListFind.get().setName(request.getName());
        return meTodoListRepository.save(todoListFind.get());
    }

    @Override
    @Synchronized
    @Transactional
    @CacheEvict(value = {"todoListByProject", "todosByPeriodAndTodoList"}, allEntries = true)
    public String deleteTodoList(@Valid MeDeleteTodoListRequest request) {
        Optional<TodoList> todoListFind = meTodoListRepository.findById(request.getId());
        if (!todoListFind.isPresent()) {
            throw new MessageHandlingException(Message.TODO_LIST_NOT_EXISTS);
        }
        meTodoListRepository.updateIndexTodoList((int) todoListFind.get().getIndexTodoList(), request.getProjectId());
        meTodoListRepository.deleteById(request.getId());
        return request.getId();
    }
}

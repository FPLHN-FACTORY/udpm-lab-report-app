package com.labreportapp.portalprojects.core.member.service.impl;

import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.portalprojects.core.common.base.TodoObject;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateOrDeleteAssignRequest;
import com.labreportapp.portalprojects.core.member.repository.MeActivityRepository;
import com.labreportapp.portalprojects.core.member.repository.MeAssignRepository;
import com.labreportapp.portalprojects.core.member.service.MeAssignService;
import com.labreportapp.portalprojects.entity.ActivityTodo;
import com.labreportapp.portalprojects.entity.Assign;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.MessageHandlingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class MeAssignServiceImpl implements MeAssignService {

    @Autowired
    private MeAssignRepository meAssignRepository;

    @Autowired
    private MeActivityRepository meActivityRepository;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Override
    public List<String> getAllMemberByIdTodo(String idTodo) {
        return meAssignRepository.getAllMemberByIdTodo(idTodo);
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todosByFilter"}, allEntries = true)
    @Synchronized
    @Transactional
    public TodoObject delete(@Valid MeCreateOrDeleteAssignRequest request) {
        Assign assignFind = meAssignRepository.findByMemberIdAndTodoId(request.getIdMember(), request.getIdTodo());
        if (assignFind == null) {
            throw new MessageHandlingException(Message.ASSIGN_NOT_EXISTS);
        }
        meAssignRepository.delete(request.getIdMember(), request.getIdTodo());
        ActivityTodo activityTodo = new ActivityTodo();

        if (request.getIdMember().equals(labReportAppSession.getUserId())) {
            activityTodo.setProjectId(request.getProjectId());
            activityTodo.setTodoId(request.getIdTodo());
            activityTodo.setTodoListId(request.getIdTodoList());
            activityTodo.setMemberCreatedId(labReportAppSession.getUserId());
            activityTodo.setContentAction("đã rời khỏi thẻ này");
        } else {
            activityTodo.setProjectId(request.getProjectId());
            activityTodo.setTodoId(request.getIdTodo());
            activityTodo.setTodoListId(request.getIdTodoList());
            activityTodo.setMemberCreatedId(labReportAppSession.getUserId());
            activityTodo.setMemberId(request.getIdMember());
            activityTodo.setContentAction("đã xóa " + request.getNameMember() + " khỏi thẻ này");
        }
        TodoObject todoObject = TodoObject.builder().data(request.getIdMember())
                .dataActivity(meActivityRepository.save(activityTodo)).idTodoList(request.getIdTodoList())
                .idTodo(request.getIdTodo()).build();
        return todoObject;
    }

    @Override
    @CacheEvict(value = {"todosByPeriodAndTodoList", "todosByFilter"}, allEntries = true)
    @Synchronized
    public TodoObject create(@Valid MeCreateOrDeleteAssignRequest request) {
        Assign assignFind = meAssignRepository.findByMemberIdAndTodoId(request.getIdMember(), request.getIdTodo());
        if (assignFind != null) {
            throw new MessageHandlingException(Message.ASSIGN_EXISTS);
        }
        Assign assign = new Assign();
        assign.setTodoId(request.getIdTodo());
        assign.setEmail(request.getEmail());
        assign.setMemberId(request.getIdMember());

        ActivityTodo activityTodo = new ActivityTodo();
        if (request.getIdMember().equals(labReportAppSession.getUserId())) {
            activityTodo.setProjectId(request.getProjectId());
            activityTodo.setTodoId(request.getIdTodo());
            activityTodo.setTodoListId(request.getIdTodoList());
            activityTodo.setMemberCreatedId(labReportAppSession.getUserId());
            activityTodo.setContentAction("đã tham gia thẻ này");
        } else {
            activityTodo.setProjectId(request.getProjectId());
            activityTodo.setTodoId(request.getIdTodo());
            activityTodo.setTodoListId(request.getIdTodoList());
            activityTodo.setMemberCreatedId(labReportAppSession.getUserId());
            activityTodo.setMemberId(request.getIdMember());
            activityTodo.setContentAction("đã thêm " + request.getNameMember() + " vào thẻ này");
        }

        TodoObject todoObject = TodoObject.builder().data(meAssignRepository.save(assign)).
                dataActivity(meActivityRepository.save(activityTodo)).
                idTodoList(request.getIdTodoList()).idTodo(request.getIdTodo()).build();
        return todoObject;
    }
}

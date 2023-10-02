package com.labreportapp.portalprojects.core.member.service.impl;

import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.portalprojects.core.common.base.PageableObject;
import com.labreportapp.portalprojects.core.common.base.TodoObject;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateCommentRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeDeleteCommentRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeFindCommentRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateCommentRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeCommentResponse;
import com.labreportapp.portalprojects.core.member.repository.MeCommentRepository;
import com.labreportapp.portalprojects.core.member.service.MeCommentService;
import com.labreportapp.portalprojects.entity.Comment;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.MessageHandlingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class MeCommentServiceImpl implements MeCommentService {

    @Autowired
    private MeCommentRepository meCommentRepository;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Override
    @Synchronized
    @CacheEvict(value = {"todosByPeriodAndTodoList", "detailTodosById"}, allEntries = true)
    public TodoObject add(@Valid MeCreateCommentRequest request) {
        Comment comment = new Comment();
        comment.setMemberId(labReportAppSession.getUserId());
        comment.setTodoId(request.getIdTodo());
        comment.setContent(request.getContent());
        comment.setStatusEdit(0);
        return TodoObject.builder().data(meCommentRepository.save(comment))
                .idTodoList(request.getIdTodoList())
                .idTodo(request.getIdTodo()).build();
    }

    @Override
    public PageableObject<MeCommentResponse> getAllCommentByIdTodo(final MeFindCommentRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), 5);
        Page<MeCommentResponse> res = meCommentRepository.getAllCommentByIdTodo(pageable, request);
        return new PageableObject(res);
    }

    @Override
    @Synchronized
    @Transactional
    @CacheEvict(value = {"detailTodosById"}, allEntries = true)
    public TodoObject update(@Valid MeUpdateCommentRequest request) {
        Optional<Comment> commentFind = meCommentRepository.findById(request.getId());
        if (!commentFind.isPresent()) {
            throw new MessageHandlingException(Message.COMMENT_NOT_EXISTS);
        }
        if (!commentFind.get().getMemberId().equals(labReportAppSession.getUserId())) {
            throw new MessageHandlingException(Message.USER_NOT_ALLOWED);
        }
        commentFind.get().setContent(request.getContent());
        commentFind.get().setStatusEdit(1);
        return TodoObject.builder().data(meCommentRepository.save(commentFind.get()))
                .idTodo(request.getIdTodo())
                .idTodoList(request.getIdTodoList())
                .build();
    }

    @Override
    @Synchronized
    @Transactional
    @CacheEvict(value = {"todosByPeriodAndTodoList", "detailTodosById"}, allEntries = true)
    public TodoObject delete(@Valid MeDeleteCommentRequest request) {
        Optional<Comment> commentFind = meCommentRepository.findById(request.getId());
        if (!commentFind.isPresent()) {
            throw new MessageHandlingException(Message.COMMENT_NOT_EXISTS);
        }
        if (!commentFind.get().getMemberId().equals(labReportAppSession.getUserId())) {
            throw new MessageHandlingException(Message.USER_NOT_ALLOWED);
        }
        meCommentRepository.delete(commentFind.get());
        return TodoObject.builder().data(request.getId()).idTodo(request.getIdTodo()).idTodoList(request.getIdTodoList()).build();
    }

}

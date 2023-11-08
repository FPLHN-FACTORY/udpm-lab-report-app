package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.model.request.TeCreatePostRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindPostClassRepquest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdatePostRequest;
import com.labreportapp.labreport.core.teacher.model.response.TePostResponse;
import com.labreportapp.labreport.core.teacher.model.response.TePostTeacherResponse;
import com.labreportapp.labreport.core.teacher.repository.TePostRepository;
import com.labreportapp.labreport.core.teacher.service.TePostService;
import com.labreportapp.labreport.entity.Post;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author hieundph25894
 */
@Service
public class TePostServiceImpl implements TePostService {

    @Autowired
    private TePostRepository tePostRepository;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Override
    public PageableObject<TePostTeacherResponse> searchPagePost(TeFindPostClassRepquest repquest) {
        Pageable pageable = PageRequest.of(repquest.getPage() - 1, repquest.getSize());
        Page<TePostResponse> listPage = tePostRepository.searchPostByIdClass(repquest, pageable);
        List<String> idUsers = listPage.getContent().stream()
                .map(TePostResponse::getIdTeacher)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listTeacher = callApiIdentity.handleCallApiGetListUserByListId(idUsers);
        Page<TePostTeacherResponse> pageNew = listPage.map(item -> {
            TePostTeacherResponse objNew = new TePostTeacherResponse();
            objNew.setId(item.getId());
            objNew.setDescriptions(item.getDescriptions());
            objNew.setCreatedDate(item.getCreatedDate());
            objNew.setTeacherId(item.getIdTeacher());
            objNew.setTeacherName(null);
            objNew.setTeacherUsername(null);
            if (item.getIdTeacher() != null && listTeacher.size() != 0) {
                listTeacher.forEach(user -> {
                    if (user.getId().equals(item.getIdTeacher())) {
                        objNew.setTeacherName(user.getName());
                        objNew.setTeacherUsername(user.getUserName());
                    }
                });
            }
            return objNew;
        });
        return new PageableObject<>(pageNew);
    }

    @Override
    public TePostTeacherResponse create(TeCreatePostRequest request) {
        Post post = new Post();
        if (request.getDescriptions().equals("")) {
            throw new RestApiException(Message.DESCRIPTIONS_IS_EMPTY);
        }
        SimpleResponse simpleResponse = callApiIdentity.handleCallApiGetUserById(labReportAppSession.getUserId());
        post.setDescriptions(request.getDescriptions());
        post.setCreatedDate(new Date().getTime());
        post.setClassId(request.getIdClass());
        post.setTeacherId(labReportAppSession.getUserId());
        Post postSave = tePostRepository.save(post);
        TePostTeacherResponse tePostTeacherResponse = new TePostTeacherResponse();
        tePostTeacherResponse.setId(postSave.getId());
        tePostTeacherResponse.setDescriptions(postSave.getDescriptions());
        tePostTeacherResponse.setCreatedDate(postSave.getCreatedDate());
        tePostTeacherResponse.setTeacherId(postSave.getTeacherId());
        if (simpleResponse != null) {
            tePostTeacherResponse.setTeacherName(simpleResponse.getName());
            tePostTeacherResponse.setTeacherUsername(simpleResponse.getUserName());
        }
        return tePostTeacherResponse;
    }

    @Override
    public TePostTeacherResponse update(TeUpdatePostRequest request) {
        Optional<Post> findObj = tePostRepository.findById(request.getId());
        if (!findObj.isPresent()) {
            throw new RestApiException(Message.POST_IS_EXIST);
        }
        if (request.getDescriptions().equals("")) {
            throw new RestApiException(Message.DESCRIPTIONS_IS_EMPTY);
        }
        SimpleResponse simpleResponse = callApiIdentity.handleCallApiGetUserById(labReportAppSession.getUserId());
        Post post = findObj.get();
        post.setCreatedDate(new Date().getTime());
        post.setDescriptions(request.getDescriptions());
        Post postSave = tePostRepository.save(post);
        TePostTeacherResponse tePostTeacherResponse = new TePostTeacherResponse();
        tePostTeacherResponse.setId(postSave.getId());
        tePostTeacherResponse.setDescriptions(postSave.getDescriptions());
        tePostTeacherResponse.setCreatedDate(postSave.getCreatedDate());
        tePostTeacherResponse.setTeacherId(postSave.getTeacherId());
        if (simpleResponse != null) {
            tePostTeacherResponse.setTeacherName(simpleResponse.getName());
            tePostTeacherResponse.setTeacherUsername(simpleResponse.getUserName());
        }
        return tePostTeacherResponse;
    }

    @Override
    public Post deleteById(String idPost) {
        Optional<Post> findObj = tePostRepository.findById(idPost);
        if (!findObj.isPresent()) {
            throw new RestApiException(Message.POST_IS_EXIST);
        }
        tePostRepository.deleteById(idPost);
        return findObj.get();
    }

}

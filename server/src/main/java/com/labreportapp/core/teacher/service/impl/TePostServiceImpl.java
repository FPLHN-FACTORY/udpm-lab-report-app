package com.labreportapp.core.teacher.service.impl;

import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.core.teacher.model.request.TeCreatePostRequest;
import com.labreportapp.core.teacher.model.request.TeFindPostClassRepquest;
import com.labreportapp.core.teacher.model.request.TeUpdatePostRequest;
import com.labreportapp.core.teacher.model.response.TePostRespone;
import com.labreportapp.core.teacher.repository.TePostRepository;
import com.labreportapp.core.teacher.service.TePostService;
import com.labreportapp.entity.Post;
import com.labreportapp.infrastructure.constant.Message;
import com.labreportapp.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author hieundph25894
 */
@Service
public class TePostServiceImpl implements TePostService {

    @Autowired
    private TePostRepository tePostRepository;

    @Override
    public PageableObject<TePostRespone> searchPagePost(TeFindPostClassRepquest repquest) {
        Pageable pageable = PageRequest.of(repquest.getPage() - 1, repquest.getSize());
        Page<TePostRespone> list = tePostRepository.searchPostByIdTeacherIdClass(repquest, pageable);
        return new PageableObject<>(list);
    }

    @Override
    public Post create(TeCreatePostRequest request) {
        Post post = new Post();
        post.setDescriptions(request.getDescriptions());
        post.setCreatedDate(new Date().getTime());
        post.setClassId(request.getIdClass());
        post.setTeacherId(request.getIdTeacher());
        return tePostRepository.save(post);
    }

    @Override
    public Post update(TeUpdatePostRequest request) {
        Optional<Post> findObj = tePostRepository.findById(request.getId());
        if (!findObj.isPresent()) {
            throw new RestApiException(Message.POST_IS_EXIST);
        }
        Post post = findObj.get();
        post.setDescriptions(request.getDescriptions());
        return tePostRepository.save(post);
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

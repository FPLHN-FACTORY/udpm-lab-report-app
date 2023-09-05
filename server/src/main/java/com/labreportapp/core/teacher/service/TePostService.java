package com.labreportapp.core.teacher.service;

import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.core.teacher.model.request.TeCreatePostRequest;
import com.labreportapp.core.teacher.model.request.TeFindPostClassRepquest;
import com.labreportapp.core.teacher.model.request.TeUpdatePostRequest;
import com.labreportapp.core.teacher.model.response.TePostRespone;
import com.labreportapp.entity.Post;
import jakarta.validation.Valid;

/**
 * @author hieundph25894
 */
public interface TePostService {

    PageableObject<TePostRespone> searchPagePost(final TeFindPostClassRepquest repquest);

    Post create(@Valid final TeCreatePostRequest request);

    Post update(@Valid TeUpdatePostRequest request);

    Post deleteById(String idPost);
}

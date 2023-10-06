package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.teacher.model.request.TeCreatePostRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindPostClassRepquest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdatePostRequest;
import com.labreportapp.labreport.core.teacher.model.response.TePostTeacherResponse;
import com.labreportapp.labreport.entity.Post;
import jakarta.validation.Valid;

/**
 * @author hieundph25894
 */
public interface TePostService {

    PageableObject<TePostTeacherResponse> searchPagePost(final TeFindPostClassRepquest repquest);

    TePostTeacherResponse create(@Valid final TeCreatePostRequest request);

    TePostTeacherResponse update(@Valid TeUpdatePostRequest request);

    Post deleteById(String idPost);

}

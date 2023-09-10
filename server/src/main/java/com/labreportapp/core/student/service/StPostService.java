package com.labreportapp.core.student.service;

import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.core.student.model.request.StFindPostRequest;
import com.labreportapp.core.student.model.response.StPostResponse;

/**
 * @author quynhncph26201
 */
public interface StPostService {

    PageableObject<StPostResponse> searchPagePost(final StFindPostRequest repquest);

}

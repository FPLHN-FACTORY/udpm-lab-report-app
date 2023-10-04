package com.labreportapp.labreport.core.student.service;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.student.model.request.StFindPostRequest;
import com.labreportapp.labreport.core.student.model.response.StPostTeacherResponse;

/**
 * @author quynhncph26201
 */
public interface StPostService {

    PageableObject<StPostTeacherResponse> searchPagePost(final StFindPostRequest repquest);

}

package com.labreportapp.labreport.core.student.service;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.student.model.request.StClassRequest;
import com.labreportapp.labreport.core.student.model.request.StFindClassRequest;
import com.labreportapp.labreport.core.student.model.response.StClassCustomResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailClassResponse;

public interface StClassService {

    PageableObject<StClassCustomResponse> getAllClassByCriteriaAndIsActive(final StFindClassRequest req);

    StClassCustomResponse joinClass(final StClassRequest req);

    TeDetailClassResponse findClassById(final String id);

}

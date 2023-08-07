package com.labreportapp.core.teacher.service;

import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.core.teacher.model.request.TeFindClass;
import com.labreportapp.core.teacher.model.response.TeClassResponse;
import com.labreportapp.core.teacher.model.response.TeDetailClassRespone;

import java.util.Optional;

/**
 * @author hieundph25894
 */
public interface TeClassService {

    PageableObject<TeClassResponse> searchTeacherClass(final TeFindClass teFindClass);

    TeDetailClassRespone findClassById(final String id);

}

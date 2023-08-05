package com.labreportapp.core.teacher.service;

import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.core.teacher.model.request.TeFindClass;
import com.labreportapp.core.teacher.model.response.TeClassResponse;

/**
 * @author hieundph25894
 */
public interface TeClassService {

    PageableObject<TeClassResponse> searchTeacherClass(final TeFindClass teFindClass);

}

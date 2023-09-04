package com.labreportapp.core.teacher.service;

import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.core.teacher.model.response.TeClassResponse;
import com.labreportapp.core.teacher.model.response.TeDetailClassRespone;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeClassService {

    PageableObject<TeClassResponse> searchTeacherClass(final TeFindClassRequest teFindClass);

    TeDetailClassRespone findClassById(final String id);

    List<TeClassResponse> getClassClosestToTheDateToSemester(String idTeacher);

}

package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeClassResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailClassRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeFindUpdateStatusClassRequest;
import com.labreportapp.labreport.entity.Class;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeClassService {

    PageableObject<TeClassResponse> searchTeacherClass(final TeFindClassRequest teFindClass);

    TeDetailClassRespone findClassById(final String id);

    List<TeClassResponse> getClassClosestToTheDateToSemester(String idTeacher);

    Class updateStatusClass(final TeFindUpdateStatusClassRequest request);

    Class randomPassword(String idClass);
}

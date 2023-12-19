package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassSelectRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassSentStudentRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassStatisticalRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindUpdateStatusClassRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdateFiledClassRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeClassResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeClassSentStudentRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeClassStatisticalResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailClassResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeFindClassSelectResponse;
import com.labreportapp.labreport.entity.Class;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeClassService {

    PageableObject<TeClassResponse> searchTeacherClass(final TeFindClassRequest request);

    TeClassResponse updateFiledClass(TeUpdateFiledClassRequest request);

    PageableObject<TeClassSentStudentRespone> findClassBySentStudent(final TeFindClassSentStudentRequest request);

    TeDetailClassResponse findClassById(final String id);

    List<TeClassResponse> getClassClosestToTheDateToSemester(String idTeacher);

    Class updateStatusClass(final TeFindUpdateStatusClassRequest request);

    Class randomPassword(String idClass);

    PageableObject<TeClassStatisticalResponse> searchClassStatistical(final TeFindClassStatisticalRequest request);

    List<TeFindClassSelectResponse> listClass(final TeFindClassSelectRequest request);

}

package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentApiRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentCallApiResponse;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeStudentClassesService {

    List<TeStudentCallApiResponse> searchApiStudentClassesByIdClass(String idClass);

    List<SimpleResponse> searchAllStudentByIdClass(String idClass);

    List<TeStudentCallApiResponse> searchStudentClassesByIdClassAndIdTeam(final TeFindStudentClasses teFindStudentClasses);

    List<TeStudentCallApiResponse> callApiStudent(final TeFindStudentApiRequest teFindStudentApiRequest);

}

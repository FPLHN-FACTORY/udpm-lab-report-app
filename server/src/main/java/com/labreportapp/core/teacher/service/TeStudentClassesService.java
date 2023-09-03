package com.labreportapp.core.teacher.service;

import com.labreportapp.core.teacher.model.request.TeFindStudentApiRequest;
import com.labreportapp.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.core.teacher.model.response.TeStudentCallApiResponse;
import com.labreportapp.core.teacher.model.response.TeStudentClassesRespone;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeStudentClassesService {

    List<TeStudentCallApiResponse> searchStudentClassesByIdClass(final TeFindStudentClasses teFindStudentClasses);

    List<TeStudentCallApiResponse> searchStudentClassesByIdClassAndIdTeam(final TeFindStudentClasses teFindStudentClasses);

    List<TeStudentCallApiResponse> callApiStudent(final TeFindStudentApiRequest teFindStudentApiRequest);

}

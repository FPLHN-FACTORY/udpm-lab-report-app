package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentApiRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.labreport.core.teacher.model.request.TeSentStudentClassRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentCallApiResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentStatusApiResponse;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeStudentClassesService {

    List<TeStudentCallApiResponse> searchApiStudentClassesByIdClass(String idClass);

    List<TeStudentStatusApiResponse> searchApiStudentClassesStatusByIdClass(String idClass);

    List<SimpleResponse> searchAllStudentByIdClass(String idClass);

    List<TeStudentCallApiResponse> searchStudentClassesByIdClassAndIdTeam(final TeFindStudentClasses teFindStudentClasses);

    List<TeStudentCallApiResponse> callApiStudent(final TeFindStudentApiRequest teFindStudentApiRequest);

    Boolean updateKickStudentClasses(TeSentStudentClassRequest request);

    Boolean updateSentStudentClassesToClass(@Valid final TeSentStudentClassRequest request);

}

package com.labreportapp.core.teacher.service;

import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.core.teacher.model.response.TeStudentClassesRespone;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeStudentClassesService {

    List<TeStudentClassesRespone> searchStudentClassesByIdClass(final TeFindStudentClasses teFindStudentClasses);

    List<TeStudentClassesRespone> searchStudentClassesByIdClassAndIdTeam(final TeFindStudentClasses teFindStudentClasses);

}

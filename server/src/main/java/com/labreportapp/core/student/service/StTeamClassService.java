package com.labreportapp.core.student.service;

import com.labreportapp.core.student.model.request.FindTeamByIdClass;
import com.labreportapp.core.student.model.request.FindTeamClassRequest;
import com.labreportapp.core.student.model.response.StMyStudentTeamResponse;
import com.labreportapp.core.student.model.response.StMyTeamInClassResponse;
import com.labreportapp.entity.Class;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface StTeamClassService {

    List<StMyTeamInClassResponse> getTeamInClassByIdStudent(final FindTeamClassRequest req);

    List<StMyStudentTeamResponse> getMyStudentTeam(final FindTeamClassRequest req);

    List<StMyTeamInClassResponse> getListTeamInClass(FindTeamByIdClass requét);

    Class detailClass(String idClass);

}

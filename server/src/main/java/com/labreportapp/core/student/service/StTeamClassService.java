package com.labreportapp.core.student.service;

import com.labreportapp.core.student.model.request.FindTeamClassRequest;
import com.labreportapp.core.student.model.response.StMyStudentTeamResponse;
import com.labreportapp.core.student.model.response.StMyTeamInClassResponse;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface StTeamClassService {
    List<StMyTeamInClassResponse> getTeamInClassByIdStudent(final FindTeamClassRequest req);

    List<StMyStudentTeamResponse> getMyStudentTeam(final FindTeamClassRequest req);

    List<StMyTeamInClassResponse> getTeamStNotJoin(final FindTeamClassRequest req);



}

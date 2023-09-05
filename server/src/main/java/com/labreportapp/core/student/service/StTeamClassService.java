package com.labreportapp.core.student.service;

import com.labreportapp.core.common.response.SimpleResponse;
import com.labreportapp.core.student.model.request.FindTeamByIdClass;
import com.labreportapp.core.student.model.request.FindTeamClassRequest;
import com.labreportapp.core.student.model.request.StJoinTeamRequest;
import com.labreportapp.core.student.model.request.StOutTeamRequest;
import com.labreportapp.core.student.model.response.StDetailClassCustomResponse;
import com.labreportapp.core.student.model.response.StMyStudentTeamResponse;
import com.labreportapp.core.student.model.response.StMyTeamInClassResponse;
import com.labreportapp.core.student.model.response.StStudentCallApiResponse;
import com.labreportapp.entity.Class;
import com.labreportapp.entity.Team;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface StTeamClassService {

    List<StMyTeamInClassResponse> getTeamInClass(final FindTeamByIdClass req);

    List<StMyStudentTeamResponse> getStudentInMyTeam(final FindTeamClassRequest req);

    StDetailClassCustomResponse detailClass(String idClass);

    Team checkStatusStudentInClass(String idClass, String studentId);

    List<StStudentCallApiResponse> callApi();

    Team detailTeam(String idTeam);

    String joinTeam(@Valid StJoinTeamRequest request);

    String outTeam(@Valid StOutTeamRequest request);

    List<SimpleResponse> test();

}

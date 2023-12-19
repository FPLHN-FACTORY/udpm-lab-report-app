package com.labreportapp.labreport.core.student.service;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.student.model.request.FindTeamByIdClass;
import com.labreportapp.labreport.core.student.model.request.FindTeamClassRequest;
import com.labreportapp.labreport.core.student.model.request.StChangeLeaderRequest;
import com.labreportapp.labreport.core.student.model.request.StJoinTeamRequest;
import com.labreportapp.labreport.core.student.model.request.StOutTeamRequest;
import com.labreportapp.labreport.core.student.model.response.StDetailClassCustomResponse;
import com.labreportapp.labreport.core.student.model.response.StMyStudentTeamCustom;
import com.labreportapp.labreport.core.student.model.response.StMyTeamInClassResponse;
import com.labreportapp.labreport.core.student.model.response.StStudentCallApiResponse;
import com.labreportapp.labreport.entity.Team;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface StTeamClassService {

    List<StMyTeamInClassResponse> getTeamInClass(final FindTeamByIdClass req);

    List<StMyStudentTeamCustom> getStudentInMyTeam(final FindTeamClassRequest req);

    StDetailClassCustomResponse detailClass(String idClass);

    Team checkStatusStudentInClass(String idClass, String studentId);

    List<StStudentCallApiResponse> callApi();

    Team detailTeam(String idTeam);

    String joinTeam(@Valid StJoinTeamRequest request);

    String outTeam(@Valid StOutTeamRequest request);

    List<SimpleResponse> test();

    Boolean changeLeader(@Valid StChangeLeaderRequest request);

}

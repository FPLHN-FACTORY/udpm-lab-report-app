package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.teacher.model.request.TeCreateTeamsRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdateTeamsRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeTeamsRespone;
import com.labreportapp.labreport.entity.Team;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeTeamsService {

    List<TeTeamsRespone> getAllTeams(final TeFindStudentClasses teFindStudentClasses);

    Team createTeam(@Valid final TeCreateTeamsRequest request);

    Team updateTeam(@Valid TeUpdateTeamsRequest request);

    String deleteTeamById(String idTeam);

    void exportExcelTeam(HttpServletResponse response, String idClass);

}

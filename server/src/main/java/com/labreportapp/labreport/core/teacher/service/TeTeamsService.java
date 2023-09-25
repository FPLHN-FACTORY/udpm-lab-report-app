package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.teacher.model.request.TeCreateTeamsRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdateTeamsRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeExcelResponseMessage;
import com.labreportapp.labreport.core.teacher.model.response.TeTeamsRespone;
import com.labreportapp.labreport.entity.Team;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeTeamsService {

    List<TeTeamsRespone> getAllTeams(final TeFindStudentClasses teFindStudentClasses);

    Team createTeam(@Valid final TeCreateTeamsRequest request);

    Team updateTeam(@Valid TeUpdateTeamsRequest request);

    String deleteTeamById(String idTeam);

    ByteArrayOutputStream exportExcelTeam(HttpServletResponse response, String idClass);

    TeExcelResponseMessage importExcelTeam(MultipartFile file, String idClass);

}

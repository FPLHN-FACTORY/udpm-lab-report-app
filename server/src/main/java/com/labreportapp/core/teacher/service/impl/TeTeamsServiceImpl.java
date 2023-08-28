package com.labreportapp.core.teacher.service.impl;

import com.labreportapp.core.teacher.model.request.TeCreateTeamsRequest;
import com.labreportapp.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.core.teacher.model.request.TeTeamUpdateStudentClassRequest;
import com.labreportapp.core.teacher.model.request.TeUpdateTeamsRequest;
import com.labreportapp.core.teacher.model.response.TeTeamsRespone;
import com.labreportapp.core.teacher.repository.TeStudentClassesRepository;
import com.labreportapp.core.teacher.repository.TeTeamsRepositoty;
import com.labreportapp.core.teacher.service.TeTeamsService;
import com.labreportapp.entity.StudentClasses;
import com.labreportapp.entity.Team;
import com.labreportapp.infrastructure.constant.Message;
import com.labreportapp.infrastructure.constant.RoleTeam;
import com.labreportapp.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author hieundph25894
 */
@Service
public class TeTeamsServiceImpl implements TeTeamsService {

    @Autowired
    private TeTeamsRepositoty teTeamsRepositoty;

    @Autowired
    private TeStudentClassesRepository teStudentClassesRepository;

    @Override
    public List<TeTeamsRespone> getAllTeams(final TeFindStudentClasses teFindStudentClasses) {
        return teTeamsRepositoty.findTeamsByIdClass(teFindStudentClasses);
    }

    @Override
    public Team createTeam(@Valid TeCreateTeamsRequest request) {
//        String checkCode = teTeamsRepositoty.getTeamByCode(request.getCode());
//        if (checkCode != null) {
//            throw new RestApiException(Message.CODE_TEAM_EXISTS);
//        }
        List<TeTeamUpdateStudentClassRequest> studentClassesRequest = request.getListStudentClasses();
        int checkLeader = 0;
        for (int i = 0; i < studentClassesRequest.size(); i++) {
            if (studentClassesRequest.get(i).getRole().equals("0") || studentClassesRequest.get(i).getRole() == "0") {
                checkLeader++;
            }
        }
        if (checkLeader > 1) {
            throw new RestApiException(Message.UNIQUE_LEADER_TEAM);
        }
        Team team = new Team();
        team.setCode(request.getCode());
        team.setName(request.getName());
        team.setSubjectName(request.getSubjectName());
        team.setClassId(request.getClassId());
        Team teamCreate = teTeamsRepositoty.save(team);
        List<StudentClasses> studentClassesNew = new ArrayList<>();
        studentClassesRequest.forEach(item -> {
            StudentClasses studentClasses = teStudentClassesRepository.findStudentClassesById(item.getIdStudentClass());
            if (studentClasses != null) {
                if ("0".equals(item.getRole())) {
                    studentClasses.setRole(RoleTeam.LEADER);
                } else {
                    studentClasses.setRole(RoleTeam.MEMBER);
                }
                studentClasses.setClassId(request.getClassId());
                studentClasses.setTeamId(teamCreate.getId());
                studentClassesNew.add(studentClasses);
            }
        });
        teStudentClassesRepository.saveAll(studentClassesNew);
        return teamCreate;
    }

    @Override
    public Team updateTeam(@Valid TeUpdateTeamsRequest request) {
        Optional<Team> teamFilter = teTeamsRepositoty.findById(request.getId());
        if (teamFilter == null) {
            throw new RestApiException(Message.TEAM_NOT_EXISTS);
        }
        Team team = teamFilter.get();
//        String checkCode = teTeamsRepositoty.getTeamByCode(request.getCode());
//        if (checkCode != null && !team.getCode().equals(request.getCode())) {
//            throw new RestApiException(Message.CODE_TEAM_EXISTS);
//        }
        List<TeTeamUpdateStudentClassRequest> studentClassesRequest = request.getListStudentClasses();
        int checkLeader = 0;
        for (int i = 0; i < studentClassesRequest.size(); i++) {
            if (studentClassesRequest.get(i).getRole().equals("0") || studentClassesRequest.get(i).getRole() == "0") {
                checkLeader++;
            }
        }
        if (checkLeader > 1) {
            throw new RestApiException(Message.UNIQUE_LEADER_TEAM);
        }
        team.setCode(request.getCode());
        team.setName(request.getName());
        team.setSubjectName(request.getSubjectName());
        List<StudentClasses> studentClassesNew = new ArrayList<>();
        studentClassesRequest.forEach(item -> {
            StudentClasses studentClasses = teStudentClassesRepository.findStudentClassesById(item.getIdStudentClass());
            if (studentClasses != null) {
                if ("0".equals(item.getRole())) {
                    studentClasses.setRole(RoleTeam.LEADER);
                } else {
                    studentClasses.setRole(RoleTeam.MEMBER);
                }
                studentClasses.setTeamId(team.getId());
                studentClassesNew.add(studentClasses);
            }
        });
        teStudentClassesRepository.saveAll(studentClassesNew);
        List<StudentClasses> studentClassesDeleteId = new ArrayList<>();
        List<TeTeamUpdateStudentClassRequest> studentClassesDeleteIdTeamRequest = request.getListStudentClassesDeleteIdTeam();
        studentClassesDeleteIdTeamRequest.forEach(item -> {
            StudentClasses studentClasses = teStudentClassesRepository.findStudentClassesById(item.getIdStudentClass());
            if (studentClasses != null) {
                studentClasses.setTeamId(null);
                studentClassesDeleteId.add(studentClasses);
            }
        });
        teStudentClassesRepository.saveAll(studentClassesDeleteId);
        return teTeamsRepositoty.save(team);
    }

    @Override
    public String deleteTeamById(String idTeam) {
        Team team = teTeamsRepositoty.findById(idTeam).get();
        if (team != null) {
            List<StudentClasses> listStudentClass = teStudentClassesRepository.findAllStudentClassesByIdTeam(idTeam);
            listStudentClass.forEach(item -> {
                item.setTeamId(null);
                teStudentClassesRepository.save(item);
            });
            teTeamsRepositoty.delete(team);
            return "Xóa nhóm thành công !";
        } else {
            return "Không tìm thấy nhóm, xóa thất bại !";
        }
    }

}

package com.labreportapp.core.student.service.impl;

import com.labreportapp.core.common.response.SimpleResponse;
import com.labreportapp.core.student.model.request.FindTeamByIdClass;
import com.labreportapp.core.student.model.request.FindTeamClassRequest;
import com.labreportapp.core.student.model.response.StDetailClassCustomResponse;
import com.labreportapp.core.student.model.response.StMyStudentTeamResponse;
import com.labreportapp.core.student.model.response.StMyTeamInClassResponse;
import com.labreportapp.core.student.model.response.StStudentCallApiResponse;
import com.labreportapp.core.student.repository.StMyClassRepository;
import com.labreportapp.core.student.service.StTeamClassService;
import com.labreportapp.entity.Class;
import com.labreportapp.entity.Team;
import com.labreportapp.infrastructure.apiconstant.ApiConstants;
import com.labreportapp.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author quynhncph26201
 */
@Service
public class StTeamClassServiceImpl implements StTeamClassService {

    @Autowired
    private StMyClassRepository repository;

    @Autowired
    @Qualifier(TeamRepository.NAME)
    private TeamRepository teamRepository;

    @Autowired
    private RestTemplate restTemplate;


    public List<StStudentCallApiResponse> callApi() {
        String apiUrl = ApiConstants.API_LIST_STUDENT;

        ResponseEntity<List<StStudentCallApiResponse>> responseEntity =
                restTemplate.exchange(apiUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<StStudentCallApiResponse>>() {});

        List<StStudentCallApiResponse> response = responseEntity.getBody();
        return response;
    }

    @Override
    public List<StMyTeamInClassResponse> getTeamInClass(final FindTeamByIdClass req) {
        return repository.getTeamInClass(req);
    }

    @Override
    public List<StMyStudentTeamResponse> getStudentInMyTeam(final FindTeamClassRequest req) {
        return repository.getStudentInMyTeam(req);
    }

    @Override
    public StDetailClassCustomResponse detailClass(String idClass) {
        Class classFind = repository.findById(idClass).get();
        String apiUrl = ApiConstants.API_LIST_TEACHER;

        ResponseEntity<SimpleResponse> responseEntity =
                restTemplate.exchange(apiUrl + "/" + classFind.getTeacherId(), HttpMethod.GET, null,
                        new ParameterizedTypeReference<SimpleResponse>() {});

        SimpleResponse response = responseEntity.getBody();
        StDetailClassCustomResponse stDetailClassCustomResponse = new StDetailClassCustomResponse();
        stDetailClassCustomResponse.setId(classFind.getId());
        stDetailClassCustomResponse.setCode(classFind.getCode());
        stDetailClassCustomResponse.setClassSize(classFind.getClassSize());
        stDetailClassCustomResponse.setClassPeriod(classFind.getClassPeriod());
        stDetailClassCustomResponse.setDescriptions(classFind.getDescriptions());
        stDetailClassCustomResponse.setActivityId(classFind.getActivityId());
        stDetailClassCustomResponse.setName(classFind.getName());
        stDetailClassCustomResponse.setNameTeacher(response.getName());
        stDetailClassCustomResponse.setStartTime(classFind.getStartTime());
        stDetailClassCustomResponse.setTeacherId(classFind.getTeacherId());
        stDetailClassCustomResponse.setUsernameTeacher(response.getUsername());
        return stDetailClassCustomResponse;
    }

    @Override
    public Team checkStatusStudentInClass(String idClass, String studentId) {
        String id = repository.checkStatusStudentInClass(idClass, studentId);
        if (id == null) {
            return null;
        } else {
            return teamRepository.findById(id).get();
        }
    }
}

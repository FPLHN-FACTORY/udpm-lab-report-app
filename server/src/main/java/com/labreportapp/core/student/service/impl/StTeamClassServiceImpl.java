package com.labreportapp.core.student.service.impl;

import com.labreportapp.core.common.response.SimpleResponse;
import com.labreportapp.core.student.model.request.FindTeamByIdClass;
import com.labreportapp.core.student.model.request.FindTeamClassRequest;
import com.labreportapp.core.student.model.request.StJoinTeamRequest;
import com.labreportapp.core.student.model.request.StOutTeamRequest;
import com.labreportapp.core.student.model.response.StDetailClassCustomResponse;
import com.labreportapp.core.student.model.response.StMyStudentTeamResponse;
import com.labreportapp.core.student.model.response.StMyTeamInClassResponse;
import com.labreportapp.core.student.model.response.StStudentCallApiResponse;
import com.labreportapp.core.student.repository.StMyClassRepository;
import com.labreportapp.core.student.repository.StStudentClassesRepository;
import com.labreportapp.core.student.service.StTeamClassService;
import com.labreportapp.entity.Class;
import com.labreportapp.entity.StudentClasses;
import com.labreportapp.entity.Team;
import com.labreportapp.infrastructure.apiconstant.ApiConstants;
import com.labreportapp.infrastructure.constant.Message;
import com.labreportapp.infrastructure.constant.RoleTeam;
import com.labreportapp.infrastructure.constant.StatusTeam;
import com.labreportapp.infrastructure.exception.rest.RestApiException;
import com.labreportapp.repository.StudentClassesRepository;
import com.labreportapp.repository.TeamRepository;
import com.labreportapp.util.ConvertListIdToString;
import jakarta.validation.Valid;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author quynhncph26201
 */
@Service
@Validated
public class StTeamClassServiceImpl implements StTeamClassService {

    @Autowired
    private StMyClassRepository repository;

    @Autowired
    @Qualifier(TeamRepository.NAME)
    private TeamRepository teamRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StStudentClassesRepository stStudentClassesRepository;

    @Autowired
    @Qualifier(StudentClassesRepository.NAME)
    private StudentClassesRepository studentClassesRepository;

    public List<StStudentCallApiResponse> callApi() {
        String apiUrl = ApiConstants.API_LIST_STUDENT;

        ResponseEntity<List<StStudentCallApiResponse>> responseEntity =
                restTemplate.exchange(apiUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<StStudentCallApiResponse>>() {
                        });

        List<StStudentCallApiResponse> response = responseEntity.getBody();
        return response;
    }

    @Override
    public Team detailTeam(String idTeam) {
        return teamRepository.findById(idTeam).get();
    }

    @Override
    @Synchronized
    public String joinTeam(@Valid StJoinTeamRequest request) {
        StudentClasses studentClasses = stStudentClassesRepository.findStudentClassesByClassIdAndStudentId(request.getIdClass(), request.getIdStudent()).get();
        if (studentClasses == null) {
            throw new RestApiException(Message.STUDENT_CLASSES_NOT_EXISTS);
        }
        if (studentClasses.getTeamId() != null) {
            throw new RestApiException(Message.STUDENT_HAD_TEAM);
        }
        studentClasses.setTeamId(request.getIdTeam());
        studentClasses.setEmail(request.getEmail());
        studentClasses.setStatus(StatusTeam.ACTIVE);
        Integer countMemberInTeam = repository.countMemberInTeam(request.getIdClass(), request.getIdTeam());
        if (countMemberInTeam == 0) {
            studentClasses.setRole(RoleTeam.LEADER);
        } else {
            studentClasses.setRole(RoleTeam.MEMBER);
        }
        studentClassesRepository.save(studentClasses);
        return request.getIdTeam();
    }

    @Override
    @Synchronized
    public String outTeam(@Valid StOutTeamRequest request) {
        StudentClasses studentClasses = stStudentClassesRepository.findStudentClassesByClassIdAndStudentId(request.getIdClass(), request.getIdStudent()).get();
        if (studentClasses == null) {
            throw new RestApiException(Message.STUDENT_CLASSES_NOT_EXISTS);
        }
        if (studentClasses.getTeamId() == null) {
            throw new RestApiException(Message.STUDENT_HAD_NOT_TEAM);
        }
        studentClasses.setTeamId(null);
        studentClasses.setRole(null);
        studentClasses.setStatus(null);
        studentClasses.setEmail(null);
        studentClassesRepository.save(studentClasses);
        return request.getIdClass();
    }

    @Override
    public List<SimpleResponse> test() {
        List<String> listStudentId =new ArrayList<>();
        listStudentId.add("99b84d22-2edb-4ede-a5c4-ec78f4791fee");
        listStudentId.add("6f0e60a6-a3a8-45d3-b6e6-d7632eb64c1a");
        String url = ConvertListIdToString.convert(listStudentId);
        String apiUrl = ApiConstants.API_LIST_TEACHER;

        ResponseEntity<List<SimpleResponse>> responseEntity =
                restTemplate.exchange(apiUrl + "?id=" + url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<SimpleResponse>>() {
                        });

        List<SimpleResponse> response = responseEntity.getBody();
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
    // detail class có idTeacher, call api identity để lấy thêm name, username của teacher
    public StDetailClassCustomResponse detailClass(String idClass) {
        Class classFind = repository.findById(idClass).get();
        String apiUrl = ApiConstants.API_LIST_TEACHER;

        ResponseEntity<SimpleResponse> responseEntity =
                restTemplate.exchange(apiUrl + "/" + classFind.getTeacherId(), HttpMethod.GET, null,
                        new ParameterizedTypeReference<SimpleResponse>() {
                        });

        SimpleResponse response = responseEntity.getBody();

        StDetailClassCustomResponse stDetailClassCustomResponse = new StDetailClassCustomResponse();
        stDetailClassCustomResponse.setId(classFind.getId());
        stDetailClassCustomResponse.setCode(classFind.getCode());
        stDetailClassCustomResponse.setClassSize(classFind.getClassSize());
        stDetailClassCustomResponse.setClassPeriod(classFind.getClassPeriod());
        stDetailClassCustomResponse.setDescriptions(classFind.getDescriptions());
        stDetailClassCustomResponse.setActivityId(classFind.getActivityId());
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

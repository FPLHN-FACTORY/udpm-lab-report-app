package com.labreportapp.labreport.core.student.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.student.model.request.FindTeamByIdClass;
import com.labreportapp.labreport.core.student.model.request.FindTeamClassRequest;
import com.labreportapp.labreport.core.student.model.request.StJoinTeamRequest;
import com.labreportapp.labreport.core.student.model.request.StOutTeamRequest;
import com.labreportapp.labreport.core.student.model.response.StDetailClassCustomResponse;
import com.labreportapp.labreport.core.student.model.response.StMyStudentTeamCustom;
import com.labreportapp.labreport.core.student.model.response.StMyStudentTeamResponse;
import com.labreportapp.labreport.core.student.model.response.StMyTeamInClassResponse;
import com.labreportapp.labreport.core.student.model.response.StStudentCallApiResponse;
import com.labreportapp.labreport.core.student.repository.StMeetingPeriodRepository;
import com.labreportapp.labreport.core.student.repository.StMyClassRepository;
import com.labreportapp.labreport.core.student.repository.StStudentClassesRepository;
import com.labreportapp.labreport.core.student.service.StTeamClassService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.MeetingPeriod;
import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.entity.Team;
import com.labreportapp.labreport.infrastructure.apiconstant.ApiConstants;
import com.labreportapp.labreport.infrastructure.constant.RoleTeam;
import com.labreportapp.labreport.infrastructure.constant.StatusClass;
import com.labreportapp.labreport.infrastructure.constant.StatusTeam;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.labreport.repository.StudentClassesRepository;
import com.labreportapp.labreport.repository.TeamRepository;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.labreport.util.ConvertListIdToString;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private StMeetingPeriodRepository stMeetingPeriodRepository;

    @Autowired
    private LoggerUtil loggerUtil;

    public List<StStudentCallApiResponse> callApi() {
        String apiUrl = ApiConstants.API_GET_USER_BY_ID;

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
        StudentClasses studentClasses = stStudentClassesRepository.findStudentClassesByClassIdAndStudentId(request.getIdClass(), labReportAppSession.getUserId()).get();
        if (studentClasses == null) {
            throw new RestApiException(Message.STUDENT_CLASSES_NOT_EXISTS);
        }
        if (studentClasses.getTeamId() != null) {
            throw new RestApiException(Message.STUDENT_HAD_TEAM);
        }
        studentClasses.setTeamId(request.getIdTeam());
        studentClasses.setEmail(labReportAppSession.getEmail());
        studentClasses.setStatus(StatusTeam.ACTIVE);
        Integer countMemberInTeam = repository.countMemberInTeam(request.getIdClass(), request.getIdTeam());
        if (countMemberInTeam == 0) {
            studentClasses.setRole(RoleTeam.LEADER);
        } else {
            studentClasses.setRole(RoleTeam.MEMBER);
        }
        studentClassesRepository.save(studentClasses);
        SimpleResponse simpleResponse = callApiIdentity.handleCallApiGetUserById(studentClasses.getStudentId());
        if (simpleResponse != null) {
            Optional<Team> teamOptional = teamRepository.findById(studentClasses.getTeamId());
            if (teamOptional.isEmpty()) {
                throw new RestApiException(Message.TEAM_NOT_EXISTS);
            }
            StringBuilder message = new StringBuilder();
            String codeClass = loggerUtil.getCodeClassByIdClass(request.getIdClass());
            String nameSemester = loggerUtil.getNameSemesterByIdClass(request.getIdClass());
            message.append("Sinh viên \"").append(simpleResponse.getName()).append(" - ")
                    .append(simpleResponse.getUserName()).append("\"").append(" đã tham gia vào nhóm:  \"")
                    .append(teamOptional.get().getName()).append("\".");
            loggerUtil.sendLogStreamClass(message.toString(), codeClass, nameSemester);
        }
        return request.getIdTeam();
    }

    @Override
    @Synchronized
    public String outTeam(@Valid StOutTeamRequest request) {
        StudentClasses studentClasses = stStudentClassesRepository.findStudentClassesByClassIdAndStudentId(request.getIdClass(), labReportAppSession.getUserId()).get();
        if (studentClasses == null) {
            throw new RestApiException(Message.STUDENT_CLASSES_NOT_EXISTS);
        }
        if (studentClasses.getTeamId() == null) {
            throw new RestApiException(Message.STUDENT_HAD_NOT_TEAM);
        }
        String idTeam = studentClasses.getTeamId();
        studentClasses.setTeamId(null);
        studentClasses.setRole(null);
        studentClasses.setStatus(null);
        studentClasses.setEmail(studentClasses.getEmail());
        studentClassesRepository.save(studentClasses);
        SimpleResponse simpleResponse = callApiIdentity.handleCallApiGetUserById(studentClasses.getStudentId());
        if (simpleResponse != null) {
            Optional<Team> teamOptional = teamRepository.findById(idTeam);
            if (teamOptional.isEmpty()) {
                throw new RestApiException(Message.TEAM_NOT_EXISTS);
            }
            StringBuilder message = new StringBuilder();
            String codeClass = loggerUtil.getCodeClassByIdClass(request.getIdClass());
            String nameSemester = loggerUtil.getNameSemesterByIdClass(request.getIdClass());
            message.append("Sinh viên \"").append(simpleResponse.getName()).append(" - ")
                    .append(simpleResponse.getUserName()).append("\"").append(" đã rời khỏi nhóm: \"")
                    .append(teamOptional.get().getName()).append("\".");
            loggerUtil.sendLogStreamClass(message.toString(), codeClass, nameSemester);
        }
        return request.getIdClass();
    }

    @Override
    public List<SimpleResponse> test() {
        List<String> listStudentId = new ArrayList<>();
        listStudentId.add("2435c7d5-9bec-45ac-9bfe-08dba87523fe");
        String url = ConvertListIdToString.convert(listStudentId);
        String apiUrl = ApiConstants.API_GET_USER_BY_LIST_ID;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjI0MzVjN2Q1LTliZWMtNDVhYy05YmZlLTA4ZGJhODc1MjNmZSIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWUiOiJOZ3V54buFbiBDw7RuZyBUaOG6r25nIFAgSCAyIDYgMSAyIDMiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9lbWFpbGFkZHJlc3MiOiJ0aGFuZ25jcGgyNjEyM0BmcHQuZWR1LnZuIiwidXNlcm5hbWUiOiJ0aGFuZ25jcGgyNjEyMyIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NMdEFxX2pGQm5ieUZUdUlCdllPbkdnS1ktTVQ5MEFvNkt0ZER6ZTY4Q1JQWW89czk2LWMiLCJpZHRyYWluaW5nZmFjaWxpdHkiOiI3OTZhNGZhNC04YWFiLTQyYzQtOWYzNS04NzBiYjAwMDVhZjEiLCJsb2NhbGhvc3QiOiJodHRwOi8vbG9jYWxob3N0OjMwMDAiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOlsiTUVNQkVSIiwiQURNSU4iXSwicm9sZW5hbWVzIjpbIlRow6BuaCB2acOqbiIsIlF14bqjbiB0cuG7iyB2acOqbiJdLCJleHAiOjE2OTQ4NzA4NzAsImlzcyI6Imh0dHBzOi8vbG9jYWxob3N0OjQ5MDUzIiwiYXVkIjoiaHR0cHM6Ly9sb2NhbGhvc3Q6NDkwNTMifQ.XCLRZGch-k2i6l9O8rW8erLU2ct9L42mzZcwai-Kq2U";
        headers.set("Authorization", "Bearer " + accessToken);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonList = null;
        try {
            jsonList = objectMapper.writeValueAsString(listStudentId);
        } catch (JsonProcessingException e) {
        }

        HttpEntity<String> httpEntity = new HttpEntity<>(jsonList, headers);

        ResponseEntity<List<SimpleResponse>> responseEntity =
                restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity,
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
    public List<StMyStudentTeamCustom> getStudentInMyTeam(final FindTeamClassRequest req) {
        List<StMyStudentTeamResponse> listRepo = repository.getStudentInMyTeam(req);
        List<String> idList = listRepo.stream()
                .map(StMyStudentTeamResponse::getStudentId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listResponse = callApiIdentity.handleCallApiGetListUserByListId(idList);
        List<StMyStudentTeamCustom> listCustom = new ArrayList<>();
        listRepo.forEach(repo -> {
            StMyStudentTeamCustom stMyStudentTeamCustom = new StMyStudentTeamCustom();
            stMyStudentTeamCustom.setStudentId(repo.getStudentId());
            stMyStudentTeamCustom.setClassId(repo.getClassId());
            stMyStudentTeamCustom.setTeamId(repo.getTeamId());
            stMyStudentTeamCustom.setEmail(repo.getEmail());
            stMyStudentTeamCustom.setId(repo.getId());
            stMyStudentTeamCustom.setRole(repo.getRole());
            stMyStudentTeamCustom.setStatus(repo.getStatus());
            for (SimpleResponse simpleResponse : listResponse) {
                if (repo.getStudentId().equals(simpleResponse.getId())) {
                    stMyStudentTeamCustom.setName(simpleResponse.getName());
                    break;
                }
            }
            listCustom.add(stMyStudentTeamCustom);
        });
        return listCustom;
    }

    @Override
    public StDetailClassCustomResponse detailClass(String idClass) {
        Class classFind = repository.findById(idClass).get();
        SimpleResponse response = callApiIdentity.handleCallApiGetUserById(classFind.getTeacherId());

        StDetailClassCustomResponse stDetailClassCustomResponse = new StDetailClassCustomResponse();
        stDetailClassCustomResponse.setId(classFind.getId());
        stDetailClassCustomResponse.setCode(classFind.getCode());
        stDetailClassCustomResponse.setClassSize(classFind.getClassSize());
        stDetailClassCustomResponse.setClassPeriod(classFind.getClassPeriod());
        if (classFind.getClassPeriod() != null) {
            Optional<MeetingPeriod> meetingPeriod = stMeetingPeriodRepository
                    .findById(classFind.getClassPeriod());
            if (meetingPeriod.isPresent()) {
                stDetailClassCustomResponse.setClassPeriod(meetingPeriod.get().getName());
            }
        }
        stDetailClassCustomResponse.setDescriptions(classFind.getDescriptions());
        stDetailClassCustomResponse.setActivityId(classFind.getActivityId());
        stDetailClassCustomResponse.setStartTime(classFind.getStartTime());
        stDetailClassCustomResponse.setTeacherId(classFind.getTeacherId());
        if (response == null) {
            stDetailClassCustomResponse.setNameTeacher(null);
            stDetailClassCustomResponse.setUsernameTeacher(null);
        } else {
            stDetailClassCustomResponse.setNameTeacher(response.getName());
            stDetailClassCustomResponse.setUsernameTeacher(response.getUserName());
        }
        stDetailClassCustomResponse.setStatusClass(classFind.getStatusClass() != null ? classFind.getStatusClass() == StatusClass.OPEN ? 0 : 1 : 1);
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

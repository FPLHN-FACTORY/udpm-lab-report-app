package com.labreportapp.core.student.service.impl;


import com.labreportapp.core.common.response.SimpleResponse;
import com.labreportapp.core.student.model.request.StClassRequest;
import com.labreportapp.core.student.model.request.StFindClassRequest;
import com.labreportapp.core.student.model.response.StClassCustomResponse;
import com.labreportapp.core.student.model.response.StClassResponse;
import com.labreportapp.core.student.repository.StClassConfigurationRepository;
import com.labreportapp.core.student.repository.StClassRepository;
import com.labreportapp.core.student.repository.StStudentClassesRepository;
import com.labreportapp.core.student.service.StClassService;
import com.labreportapp.entity.Class;
import com.labreportapp.entity.StudentClasses;
import com.labreportapp.infrastructure.apiconstant.ApiConstants;
import com.labreportapp.infrastructure.constant.Message;
import com.labreportapp.infrastructure.exception.rest.RestApiException;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StClassServiceImpl implements StClassService {

    @Autowired
    private StClassRepository stClassRepository;

    @Autowired
    private StStudentClassesRepository stStudentClassesRepository;

    @Autowired
    private StClassConfigurationRepository stClassConfigurationRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<StClassCustomResponse> getAllClassByCriteriaAndIsActive(final StFindClassRequest req) {
        List<StClassResponse> getAllClassByCriteria = stClassRepository.getAllClassByCriteriaAndIsActive(req);
        List<StClassCustomResponse> responseClassCustom = new ArrayList<>();

        String apiTeacher = ApiConstants.API_LIST_TEACHER;
        ResponseEntity<List<SimpleResponse>> responseEntity = restTemplate.exchange(apiTeacher, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<SimpleResponse>>() {
                });

        List<SimpleResponse> simplesResponse = responseEntity.getBody();
        Map<String, SimpleResponse> simpleMap = simplesResponse.stream()
                .collect(Collectors.toMap(SimpleResponse::getId, Function.identity()));

        for (StClassResponse stClassResponse : getAllClassByCriteria) {
            if (stClassResponse.getTeacherId() != null) {
                SimpleResponse simpleResponse = simpleMap.get(stClassResponse.getTeacherId());

                StClassCustomResponse stClassCustomResponse = new StClassCustomResponse();
                stClassCustomResponse.setId(stClassResponse.getId());
                stClassCustomResponse.setStt(stClassResponse.getStt());
                stClassCustomResponse.setClassCode(stClassResponse.getCode());
                stClassCustomResponse.setTeacherUsername(stClassResponse.getTeacherId().
                        equals(simpleResponse.getId()) ? simpleResponse.getUsername() : null);
                stClassCustomResponse.setClassSize(stClassResponse.getClassSize());
                stClassCustomResponse.setClassPeriod(stClassResponse.getClassPeriod());
                stClassCustomResponse.setLevel(stClassResponse.getLevel());

                responseClassCustom.add(stClassCustomResponse);
            }
        }

        return responseClassCustom;
    }

    @Override
    @Synchronized
    public StClassCustomResponse joinClass(final StClassRequest req) {
        //Note: Check student exists and conditions for entering class then =>

        Optional<Class> findClass = stClassRepository.findById(req.getIdClass());
        Optional<StudentClasses> findStudentClasses = stStudentClassesRepository.
                findStudentClassesByClassIdAndStudentId(req.getIdClass(), req.getIdStudent());

        Integer configurationSizeMax = stClassConfigurationRepository.
                getClassConfiguration().getClassSizeMax();

        if (!findClass.isPresent()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }

        if (findStudentClasses.isPresent()) {
            throw new RestApiException(Message.YOU_HAD_IN_CLASS);
        }

        if (findClass.get().getClassSize() == configurationSizeMax) {
            throw new RestApiException(Message.CLASS_DID_FULL_CLASS_SIZE);
        }

        String apiStudent = ApiConstants.API_LIST_STUDENT;
        ResponseEntity<SimpleResponse> response = restTemplate.exchange(apiStudent + "/" +
                req.getIdStudent(), HttpMethod.GET, null, SimpleResponse.class);
        SimpleResponse responseStudent = response.getBody();

        StudentClasses studentJoinClass = new StudentClasses();
        StClassCustomResponse customResponse = new StClassCustomResponse();

        if (responseStudent != null) {
            studentJoinClass.setClassId(req.getIdClass());
            studentJoinClass.setEmail(responseStudent.getEmail());
            studentJoinClass.setStudentId(req.getIdStudent());
            studentJoinClass.setCreatedDate(new Date().getTime());
            StudentClasses studentInClass = stStudentClassesRepository.save(studentJoinClass);

            if (studentInClass.getStudentId().equals(req.getIdStudent())) {
                Class classOfStudentWantJoin = findClass.get();
                classOfStudentWantJoin.setClassSize(classOfStudentWantJoin.getClassSize() + 1);
                Class updatedClass = stClassRepository.save(classOfStudentWantJoin);

                customResponse.setClassCode(updatedClass.getCode());
                customResponse.setClassSize(updatedClass.getClassSize());
            } else {
                stStudentClassesRepository.delete(studentInClass);
                throw new RestApiException("Thất bại");
            }
        }

        return customResponse;
    }


}

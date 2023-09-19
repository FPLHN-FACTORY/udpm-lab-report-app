package com.labreportapp.labreport.core.student.service.impl;


import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.student.model.request.StClassRequest;
import com.labreportapp.labreport.core.student.model.request.StFindClassRequest;
import com.labreportapp.labreport.core.student.model.response.StClassCustomResponse;
import com.labreportapp.labreport.core.student.model.response.StClassResponse;
import com.labreportapp.labreport.core.student.repository.StClassConfigurationRepository;
import com.labreportapp.labreport.core.student.repository.StClassRepository;
import com.labreportapp.labreport.core.student.repository.StStudentClassesRepository;
import com.labreportapp.labreport.core.student.service.StClassService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.infrastructure.apiconstant.ActorConstants;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Override
    public List<StClassCustomResponse> getAllClassByCriteriaAndIsActive(final StFindClassRequest req) {
        List<StClassResponse> getAllClassByCriteria = stClassRepository.getAllClassByCriteriaAndIsActive(req);
        List<StClassCustomResponse> responseClassCustom = new ArrayList<>();

        List<SimpleResponse> simplesResponse = convertRequestCallApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_TEACHER);
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
                        equals(simpleResponse.getId()) ? simpleResponse.getUserName() : null);
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
        if (!findClass.isPresent()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        Optional<StudentClasses> findStudentClasses = stStudentClassesRepository.
                findStudentClassesByClassIdAndStudentId(req.getIdClass(), req.getIdStudent());
        if (findStudentClasses.isPresent()) {
            throw new RestApiException(Message.YOU_HAD_IN_CLASS);
        }
        Integer configurationSizeMax = stClassConfigurationRepository.
                getClassConfiguration().getClassSizeMax();
        if (findClass.get().getClassSize() == configurationSizeMax) {
            throw new RestApiException(Message.CLASS_DID_FULL_CLASS_SIZE);
        }

        SimpleResponse responseStudent = convertRequestCallApiIdentity.handleCallApiGetUserById(req.getIdStudent());

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
                throw new RestApiException(Message.ERROR_UNKNOWN);
            }
        }

        return customResponse;
    }


}

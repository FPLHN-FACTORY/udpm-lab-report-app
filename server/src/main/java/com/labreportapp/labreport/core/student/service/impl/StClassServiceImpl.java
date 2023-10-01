package com.labreportapp.labreport.core.student.service.impl;


import com.labreportapp.labreport.core.common.base.PageableObject;
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
import com.labreportapp.labreport.infrastructure.constant.StatusStudentFeedBack;
import com.labreportapp.labreport.infrastructure.constant.StatusTeam;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public PageableObject<StClassCustomResponse> getAllClassByCriteriaAndIsActive(final StFindClassRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<StClassResponse> getAllClassByCriteria = stClassRepository.getAllClassByCriteriaAndIsActive(req, pageable);

        List<StClassCustomResponse> responseClassCustom = new ArrayList<>();
        PageableObject<StClassCustomResponse> pageableResponse = new PageableObject<>();

        for (StClassResponse stClassResponse : getAllClassByCriteria.getContent()) {
            StClassCustomResponse stClassCustomResponse = new StClassCustomResponse();
            stClassCustomResponse.setId(stClassResponse.getId());
            stClassCustomResponse.setStt(stClassResponse.getStt());
            stClassCustomResponse.setClassCode(stClassResponse.getCode());
            stClassCustomResponse.setClassSize(stClassResponse.getClassSize());
            stClassCustomResponse.setClassPeriod(stClassResponse.getClassPeriod());
            stClassCustomResponse.setStartTime(stClassResponse.getStartTime());
            stClassCustomResponse.setLevel(stClassResponse.getLevel());
            stClassCustomResponse.setActivityName(stClassResponse.getActivityName());
            stClassCustomResponse.setDescriptions(stClassResponse.getDescriptions());
            stClassCustomResponse.setStartTimeStudent(stClassResponse.getStartTimeStudent());
            stClassCustomResponse.setEndTimeStudent(stClassResponse.getEndTimeStudent());
            responseClassCustom.add(stClassCustomResponse);
        }

        pageableResponse.setData(responseClassCustom);
        pageableResponse.setCurrentPage(getAllClassByCriteria.getNumber());
        pageableResponse.setTotalPages(getAllClassByCriteria.getTotalPages());
        return pageableResponse;
    }

    @Override
    @Synchronized
    public StClassCustomResponse joinClass(final StClassRequest req) {
        //Note: Check student exists and conditions for entering class then =>

        Optional<Class> findClass = stClassRepository.findById(req.getIdClass());
        if (!findClass.isPresent()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        Optional<StClassResponse> conditionClass = stClassRepository.checkConditionCouldJoinOrLeaveClass(req);
        if (!conditionClass.isPresent()) {
            throw new RestApiException("Bạn chưa thể vào lớp");
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
            studentJoinClass.setStatusStudentFeedBack(StatusStudentFeedBack.CHUA_FEEDBACK);
            studentJoinClass.setStatus(StatusTeam.INACTIVE);
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

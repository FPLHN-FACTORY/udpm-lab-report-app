package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassSentStudentRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindUpdateStatusClassRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeClassResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeClassSentStudentRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailClassResponse;
import com.labreportapp.labreport.core.teacher.repository.TeClassConfigurationRepository;
import com.labreportapp.labreport.core.teacher.repository.TeClassRepository;
import com.labreportapp.labreport.core.teacher.service.TeClassService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.ClassConfiguration;
import com.labreportapp.labreport.infrastructure.constant.StatusClass;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author hieundph25894
 */
@Service
public class TeClassServiceImpl implements TeClassService {

    @Autowired
    private TeClassRepository teClassRepository;

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Autowired
    private TeClassConfigurationRepository teClassConfigurationRepository;

    @Override
    public PageableObject<TeClassResponse> searchTeacherClass(final TeFindClassRequest teFindClass) {
        Pageable pageable = PageRequest.of(teFindClass.getPage() - 1, teFindClass.getSize());
        Page<TeClassResponse> pageList = teClassRepository.findClassBySemesterAndActivity(teFindClass, pageable);
        return new PageableObject<>(pageList);
    }

    @Override
    public PageableObject<TeClassSentStudentRespone> findClassBySentStudent(TeFindClassSentStudentRequest request) {
        ClassConfiguration classConfiguration = teClassConfigurationRepository.findAll().get(0);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        Page<TeClassResponse> pageList = teClassRepository.findClassBySentStudent(request, pageable, classConfiguration.getClassSizeMax());
        List<TeClassResponse> listResponse = pageList.getContent();
        List<String> idUsers = listResponse.stream()
                .map(TeClassResponse::getTeacherId)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listTeacher = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idUsers);
        Page<TeClassSentStudentRespone> pageNew = pageList.map(item -> {
            TeClassSentStudentRespone objNew = new TeClassSentStudentRespone();
            objNew.setStt(item.getStt());
            objNew.setId(item.getId());
            objNew.setCode(item.getCode());
            objNew.setIdTeacher(item.getTeacherId());
            if (item.getTeacherId() != null) {
                listTeacher.forEach(teacher -> {
                    if (teacher.getId().equals(item.getTeacherId())) {
                        objNew.setUsernameTeacher(teacher.getUserName());
                    }
                });
            } else {
                objNew.setUsernameTeacher("");
            }
            objNew.setClassSize(item.getClassSize());
            objNew.setClassPeriod(item.getClassPeriod());
            return objNew;
        });
        return new PageableObject<>(pageNew);
    }

    @Override
    public TeDetailClassResponse findClassById(final String id) {
        Optional<TeDetailClassResponse> classCheck = teClassRepository.findClassById(id);
        if (!classCheck.isPresent()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        return classCheck.get();
    }

    @Override
    public List<TeClassResponse> getClassClosestToTheDateToSemester(String idTeacher) {
        List<TeClassResponse> list = teClassRepository.getClassClosestToTheDateToSemester(idTeacher);
        if (list.size() < 0) {
            throw new RestApiException(Message.CLASS_IS_EMPTY);
        }
        return list;
    }

    @Override
    public Class updateStatusClass(TeFindUpdateStatusClassRequest request) {
        Optional<Class> classFind = teClassRepository.findById(request.getIdClass());
        if (!classFind.isPresent()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        Class classUp = classFind.get();
        classUp.setStatusClass(request.getStatus() == 0 ? StatusClass.OPEN : StatusClass.LOCK);
        return teClassRepository.save(classUp);
    }

    @Override
    public Class randomPassword(String idClass) {
        Optional<Class> classFind = teClassRepository.findById(idClass);
        if (!classFind.isPresent()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        Class classUp = classFind.get();
        classUp.setPassword(generateRandomPassword());
        return teClassRepository.save(classUp);
    }

    public String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            password.append(randomChar);
        }
        return password.toString();
    }

}

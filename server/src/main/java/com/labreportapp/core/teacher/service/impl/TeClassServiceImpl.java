package com.labreportapp.core.teacher.service.impl;

import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.core.teacher.model.response.TeClassResponse;
import com.labreportapp.core.teacher.model.response.TeDetailClassRespone;
import com.labreportapp.core.teacher.model.response.TeFindUpdateStatusClassRequest;
import com.labreportapp.core.teacher.repository.TeClassRepository;
import com.labreportapp.core.teacher.service.TeClassService;
import com.labreportapp.entity.Class;
import com.labreportapp.infrastructure.constant.Message;
import com.labreportapp.infrastructure.constant.StatusClass;
import com.labreportapp.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

/**
 * @author hieundph25894
 */
@Service
public class TeClassServiceImpl implements TeClassService {

    @Autowired
    private TeClassRepository teClassRepository;

    @Override
    @CacheEvict(value = {"teacherClass"}, allEntries = true)
    public PageableObject<TeClassResponse> searchTeacherClass(final TeFindClassRequest teFindClass) {
        Pageable pageable = PageRequest.of(teFindClass.getPage() - 1, teFindClass.getSize());
        Page<TeClassResponse> pageList = teClassRepository.findClassBySemesterAndActivity(teFindClass, pageable);
        return new PageableObject<>(pageList);
    }

    @Override
    public TeDetailClassRespone findClassById(final String id) {
        Optional<TeDetailClassRespone> classCheck = teClassRepository.findClassById(id);
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

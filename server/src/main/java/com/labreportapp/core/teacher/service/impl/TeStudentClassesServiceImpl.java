package com.labreportapp.core.teacher.service.impl;

import com.labreportapp.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.core.teacher.model.response.TeStudentClassesRespone;
import com.labreportapp.core.teacher.repository.TeStudentClassesRepository;
import com.labreportapp.core.teacher.service.TeStudentClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hieundph25894
 */
@Service
public class TeStudentClassesServiceImpl implements TeStudentClassesService {

    @Autowired
    private TeStudentClassesRepository teStudentClassesRepository;

    @Override
    // @CacheEvict(value = {"studentClasses"}, allEntries = true)
    public List<TeStudentClassesRespone> searchStudentClassesByIdClass(TeFindStudentClasses teFindStudentClasses) {
        List<TeStudentClassesRespone> pageList = teStudentClassesRepository
                .findStudentClassByIdClass(teFindStudentClasses);
        return pageList;
    }

    @Override
    public List<TeStudentClassesRespone> searchStudentClassesByIdClassAndIdTeam(TeFindStudentClasses teFindStudentClasses) {
        List<TeStudentClassesRespone> pageList = teStudentClassesRepository
                .findStudentClassByIdClassAndIdTeam(teFindStudentClasses);
        return pageList;
    }
}

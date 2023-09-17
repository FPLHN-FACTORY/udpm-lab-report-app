package com.labreportapp.labreport.core.student.service.impl;

import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.core.student.service.StSemesterService;
import com.labreportapp.labreport.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author thangncph26123
 */
@Validated
@Service
public class StSemesterServiceImpl implements StSemesterService {

    @Autowired
    @Qualifier(SemesterRepository.NAME)
    private SemesterRepository semesterRepository;

    @Override
    public List<SimpleEntityProjection> getAllSemester() {
        return semesterRepository.getAllSimpleEntityProjection();
    }
}

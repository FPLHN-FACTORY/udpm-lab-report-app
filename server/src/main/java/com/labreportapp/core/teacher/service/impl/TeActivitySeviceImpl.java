package com.labreportapp.core.teacher.service.impl;

import com.labreportapp.core.teacher.model.request.TeFindClass;
import com.labreportapp.core.teacher.model.response.TeActivityRespone;
import com.labreportapp.core.teacher.repository.TeActivityRepository;
import com.labreportapp.core.teacher.service.TeActivitySevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hieundph25894
 */
@Service
public class TeActivitySeviceImpl implements TeActivitySevice {

    @Autowired
    private TeActivityRepository teActivityRepository;

    @Override
    public List<TeActivityRespone> getAllByIdSemester(TeFindClass teFindClass) {
        return teActivityRepository.getAllByIdSemester(teFindClass);
    }

}

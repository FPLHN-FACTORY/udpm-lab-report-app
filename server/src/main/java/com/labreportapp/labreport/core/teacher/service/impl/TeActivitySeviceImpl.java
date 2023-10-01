package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeActivityResponse;
import com.labreportapp.labreport.core.teacher.repository.TeActivityRepository;
import com.labreportapp.labreport.core.teacher.service.TeActivitySevice;
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
    public List<TeActivityResponse> getAllByIdSemester(TeFindClassRequest teFindClass) {
        return teActivityRepository.getAllByIdSemester(teFindClass);
    }

}

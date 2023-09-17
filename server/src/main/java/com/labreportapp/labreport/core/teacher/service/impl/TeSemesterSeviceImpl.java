package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.teacher.model.response.TeSemesterRespone;
import com.labreportapp.labreport.core.teacher.repository.TeSemesterRepository;
import com.labreportapp.labreport.core.teacher.service.TeSemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hieundph25894
 */
@Service
public class TeSemesterSeviceImpl implements TeSemesterService {

    @Autowired
    private TeSemesterRepository teSemesterRepository;

    @Override
    public List<TeSemesterRespone> getAllSemester() {
        return teSemesterRepository.getAllSemesters();
    }

}

package com.labreportapp.core.teacher.service.impl;

import com.labreportapp.core.teacher.model.response.TePointRespone;
import com.labreportapp.core.teacher.repository.TePointRepository;
import com.labreportapp.core.teacher.service.TePointSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hieundph25894
 */
@Service
public class TePointSeviceImpl implements TePointSevice {

    @Autowired
    private TePointRepository tePointRepository;

    @Override
    public List<TePointRespone> getPointStudentById(String idClass) {
        List<TePointRespone> list = tePointRepository.getAllPointByIdClass(idClass);
        if (list == null) {
            return null;
        }
        return list;
    }

}

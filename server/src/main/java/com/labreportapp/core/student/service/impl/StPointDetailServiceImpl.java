package com.labreportapp.core.student.service.impl;

import com.labreportapp.core.student.model.response.StPointDetailRespone;
import com.labreportapp.core.student.repository.StPointDetailRepository;
import com.labreportapp.core.student.service.StPointDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StPointDetailServiceImpl implements StPointDetailService {
    @Autowired
    private StPointDetailRepository stMyPointClassRepository;
    @Override
    public StPointDetailRespone getMyPointClass(String idClass, String studentId) {
        return stMyPointClassRepository.getPointMyClass(idClass,studentId);
    }
}

package com.labreportapp.labreport.core.student.service.impl;

import com.labreportapp.labreport.core.student.model.response.StPointCustomResponse;
import com.labreportapp.labreport.core.student.model.response.StPointDetailRespone;
import com.labreportapp.labreport.core.student.repository.StPointDetailRepository;
import com.labreportapp.labreport.core.student.service.StPointDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StPointDetailServiceImpl implements StPointDetailService {

    @Autowired
    private StPointDetailRepository stMyPointClassRepository;

    @Override
    public List<StPointCustomResponse> getMyPointClass(String idClass, String studentId) {
        StPointDetailRespone stPointDetailRespone = stMyPointClassRepository.getPointMyClass(idClass, studentId);
        List<StPointCustomResponse> customResponseList = new ArrayList<>();
        if (stPointDetailRespone != null) {
            customResponseList.add(new StPointCustomResponse(1, "Điểm giai đoạn 1", 0, stPointDetailRespone.getCheckPointPhase1(), ""));
            customResponseList.add(new StPointCustomResponse(2, "Điểm giai đoạn 2", 0, stPointDetailRespone.getCheckPointPhase2(), ""));
            customResponseList.add(new StPointCustomResponse(3, "Điểm final", 0, stPointDetailRespone.getFinalPoint(), ""));
        }
        return customResponseList;
    }

}
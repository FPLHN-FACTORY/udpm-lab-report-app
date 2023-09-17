package com.labreportapp.labreport.core.student.service.impl;

import com.labreportapp.labreport.core.student.model.request.StFindAttendanceRequest;
import com.labreportapp.labreport.core.student.model.response.StAttendanceRespone;
import com.labreportapp.labreport.core.student.repository.StAttendanceRepository;
import com.labreportapp.labreport.core.student.service.StAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StAttendanceServiceImpl implements StAttendanceService {
    @Autowired
    private StAttendanceRepository stAttendanceRepository;

    @Override
    public List<StAttendanceRespone> getAllAttendanceById(StFindAttendanceRequest req) {
        return stAttendanceRepository.getAllAttendanceById(req);
    }
}

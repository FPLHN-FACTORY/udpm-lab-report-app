package com.labreportapp.core.teacher.service.impl;

import com.labreportapp.core.teacher.model.request.TeFindAttendanceRequest;
import com.labreportapp.core.teacher.model.request.TeFindListAttendanceRequest;
import com.labreportapp.core.teacher.model.response.TeAttendanceRespone;
import com.labreportapp.core.teacher.repository.TeAttendanceRepository;
import com.labreportapp.core.teacher.service.TeAttendanceSevice;
import com.labreportapp.entity.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author hieundph25894
 */
@Service
public class TeAttendanceServiceImpl implements TeAttendanceSevice {

    @Autowired
    private TeAttendanceRepository teAttendanceRepo;

    @Override
    public List<TeAttendanceRespone> getListAttendace(TeFindListAttendanceRequest request) {
        List<TeFindAttendanceRequest> list = request.getListAttendance();
        list.forEach(item ->{
            Optional<TeAttendanceRespone> obj = teAttendanceRepo.findAttendanceByStudentIdAndMeetgId(item);
            if(obj.isPresent()){
                Attendance attendance = new Attendance();

            }
        });
        return null;
    }
}

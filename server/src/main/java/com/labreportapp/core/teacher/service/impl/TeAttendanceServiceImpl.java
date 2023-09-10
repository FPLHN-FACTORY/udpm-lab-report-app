package com.labreportapp.core.teacher.service.impl;

import com.labreportapp.core.teacher.model.request.TeFindAttendanceRequest;
import com.labreportapp.core.teacher.model.request.TeFindListAttendanceRequest;
import com.labreportapp.core.teacher.model.response.TeAttendanceRespone;
import com.labreportapp.core.teacher.repository.TeAttendanceRepository;
import com.labreportapp.core.teacher.service.TeAttendanceSevice;
import com.labreportapp.entity.Attendance;
import com.labreportapp.infrastructure.constant.StatusAttendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<TeAttendanceRespone> getListCustom(String idMeeting) {
        List<TeAttendanceRespone> list = teAttendanceRepo.findAttendanceByIdMeetgId(idMeeting);
        if (list == null) {
            return null;
        }
        return list;
    }

    @Override
    public List<Attendance> addOrUpdateAttendance(TeFindListAttendanceRequest request) {
        List<TeFindAttendanceRequest> list = request.getListAttendance();
        List<Attendance> listNew = new ArrayList<>();
        list.forEach(item -> {
            Optional<TeAttendanceRespone> obj = teAttendanceRepo.findAttendanceByStudentIdAndMeetgId(item);
            if (obj.isPresent()) {
                Attendance attendance = new Attendance();
                attendance.setId(obj.get().getIdAttendance());
                attendance.setName(item.getNameMeeting());
                attendance.setMeetingId(obj.get().getIdMeeting());
                attendance.setStudentId(obj.get().getIdStudent());
                if (item.getStatusAttendance().equals("YES") || item.getStatusAttendance() == "YES") {
                    attendance.setStatus(StatusAttendance.YES);
                } else {
                    attendance.setStatus(StatusAttendance.NO);
                }
                listNew.add(attendance);
            } else {
                Attendance attendance = new Attendance();
                attendance.setName(item.getNameMeeting());
                attendance.setMeetingId(item.getIdMeeting());
                attendance.setStudentId(item.getIdStudent());
                if (item.getStatusAttendance().equals("YES") || item.getStatusAttendance() == "YES") {
                    attendance.setStatus(StatusAttendance.YES);
                } else {
                    attendance.setStatus(StatusAttendance.NO);
                }
                attendance.setId(teAttendanceRepo.save(attendance).getId());
                listNew.add(attendance);
            }
        });
        return teAttendanceRepo.saveAll(listNew);
    }
}

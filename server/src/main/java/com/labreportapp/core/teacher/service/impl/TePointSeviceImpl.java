package com.labreportapp.core.teacher.service.impl;

import com.labreportapp.core.teacher.model.request.TeFindAttendanceRequest;
import com.labreportapp.core.teacher.model.request.TeFindListAttendanceRequest;
import com.labreportapp.core.teacher.model.request.TeFindListPointRequest;
import com.labreportapp.core.teacher.model.request.TeFindPointRequest;
import com.labreportapp.core.teacher.model.response.TeAttendanceRespone;
import com.labreportapp.core.teacher.model.response.TePointRespone;
import com.labreportapp.core.teacher.repository.TePointRepository;
import com.labreportapp.core.teacher.service.TePointSevice;
import com.labreportapp.entity.Attendance;
import com.labreportapp.entity.Point;
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

    @Override
    public List<Point> addOrUpdatePoint(TeFindListPointRequest request) {
        List<TeFindPointRequest> list = request.getListPoint();
        List<Point> listNew = new ArrayList<>();
        list.forEach(item -> {
            Optional<TePointRespone> obj = tePointRepository.getPointIdClassIdStudent(item);
            if (obj.isPresent()) {
                Point point = new Point();
                point.setId(obj.get().getId());
                point.setStudentId(obj.get().getIdStudent());
                point.setClassId(obj.get().getIdClass());
                point.setCheckPointPhase1(item.getCheckPointPhase1());
                point.setCheckPointPhase2(item.getCheckPointPhase2());
                point.setFinalPoint(item.getFinalPoint());
                listNew.add(point);
            } else {
                Point point = new Point();
                point.setStudentId(item.getIdStudent());
                point.setClassId(item.getIdClass());
                point.setCheckPointPhase1(item.getCheckPointPhase1());
                point.setCheckPointPhase2(item.getCheckPointPhase2());
                point.setFinalPoint(item.getFinalPoint());
                listNew.add(point);
            }
        });
        return tePointRepository.saveAll(listNew);
    }

}

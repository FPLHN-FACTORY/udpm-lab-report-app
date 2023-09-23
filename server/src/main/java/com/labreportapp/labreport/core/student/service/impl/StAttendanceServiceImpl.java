package com.labreportapp.labreport.core.student.service.impl;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.student.model.request.StFindAttendanceRequest;
import com.labreportapp.labreport.core.student.model.response.StAttendanceCallApiRespone;
import com.labreportapp.labreport.core.student.model.response.StAttendanceRespone;
import com.labreportapp.labreport.core.student.repository.StAttendanceRepository;
import com.labreportapp.labreport.core.student.service.StAttendanceService;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StAttendanceServiceImpl implements StAttendanceService {
    @Autowired
    private StAttendanceRepository stAttendanceRepository;

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Override
    public List<StAttendanceRespone> getAllAttendanceById(StFindAttendanceRequest req) {
        return stAttendanceRepository.getAllAttendanceById(req);
    }

    @Override
    public List<StAttendanceCallApiRespone> getAllAttendanceStudentById(StFindAttendanceRequest request) {
        List<StAttendanceRespone> stAttendanceResponeList = stAttendanceRepository.getAllAttendanceById(request);
        List<String> idTeacherList = stAttendanceResponeList.stream()
                .map(StAttendanceRespone::getTeacherId)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listRespone = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idTeacherList);
        List<StAttendanceCallApiRespone> listReturn = new ArrayList<>();
        stAttendanceResponeList.forEach(reposi -> {
            listRespone.forEach(respone -> {
                System.out.println(reposi.getTeacherId());
                System.out.println(respone.getId());
                if (reposi.getTeacherId().equals(respone.getId())) {
                    StAttendanceCallApiRespone obj = new StAttendanceCallApiRespone();
                    obj.setId(respone.getId());
                    obj.setName(respone.getName());
                    obj.setUserName(respone.getUserName());
                    obj.setEmail(respone.getEmail());
                    obj.setStt(reposi.getStt());
                    obj.setLesson(reposi.getLesson());
                    obj.setMeetingDate(reposi.getMeetingDate());
                    obj.setMeetingPeriod(reposi.getMeetingPeriod());
                    obj.setTypeMeeting(reposi.getTypeMeeting());
                    obj.setTeacherId(reposi.getTeacherId());
                    obj.setStatus(reposi.getStatus());
                    listReturn.add(obj);
                }
            });
        });
        return listReturn;
    }
}

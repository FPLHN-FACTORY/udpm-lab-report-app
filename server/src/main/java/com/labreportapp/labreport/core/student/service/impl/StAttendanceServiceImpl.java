package com.labreportapp.labreport.core.student.service.impl;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.student.model.request.StFindAttendanceRequest;
import com.labreportapp.labreport.core.student.model.response.StAttendanceCallApiRespone;
import com.labreportapp.labreport.core.student.model.response.StAttendanceRespone;
import com.labreportapp.labreport.core.student.repository.StAttendanceRepository;
import com.labreportapp.labreport.core.student.service.StAttendanceService;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.labreport.util.CallApiIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StAttendanceServiceImpl implements StAttendanceService {

    @Autowired
    private StAttendanceRepository stAttendanceRepository;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Override
    public List<StAttendanceRespone> getAllAttendanceById(StFindAttendanceRequest req) {
        req.setIdStudent(labReportAppSession.getUserId());
        return stAttendanceRepository.getAllAttendanceById(req);
    }

    @Override
    public PageableObject<StAttendanceCallApiRespone> getAllAttendanceStudentById(StFindAttendanceRequest request) {
        request.setIdStudent(labReportAppSession.getUserId());
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        Page<StAttendanceRespone> stAttendanceResponeList = stAttendanceRepository.getPageAllAttendanceById(request, pageable);
        List<String> idTeacherList = stAttendanceResponeList.getContent().stream()
                .map(StAttendanceRespone::getTeacherId)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listRespone = callApiIdentity.handleCallApiGetListUserByListId(idTeacherList);
        List<StAttendanceCallApiRespone> listReturn = new ArrayList<>();
        Page<StAttendanceCallApiRespone> pageReturn = stAttendanceResponeList.map(reposi -> {
            StAttendanceCallApiRespone obj = new StAttendanceCallApiRespone();
            obj.setStt(reposi.getStt());
            obj.setLesson(reposi.getLesson());
            obj.setMeetingDate(reposi.getMeetingDate());
            obj.setMeetingPeriod(reposi.getMeetingPeriod());
            obj.setStartHour(reposi.getStartHour());
            obj.setStartMinute(reposi.getStartMinute());
            obj.setEndHour(reposi.getEndHour());
            obj.setEndMinute(reposi.getEndMinute());
            obj.setTypeMeeting(reposi.getTypeMeeting());
            obj.setTeacherId(reposi.getTeacherId());
            obj.setStatus(reposi.getStatus());
            obj.setNotes(reposi.getNotes());
            if (reposi.getTeacherId() != null && listRespone.size() != 0) {
                listRespone.forEach(user -> {
                    if (user.getId().equals(reposi.getTeacherId())) {
                        obj.setId(user.getId());
                        obj.setName(user.getName());
                        obj.setUserName(user.getUserName());
                        obj.setEmail(user.getEmail());
                    }
                });
            }
            return obj;
        });
        return new PageableObject<>(pageReturn);
    }
}

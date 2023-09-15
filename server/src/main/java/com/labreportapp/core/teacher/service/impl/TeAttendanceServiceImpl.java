package com.labreportapp.core.teacher.service.impl;

import com.labreportapp.core.common.response.SimpleResponse;
import com.labreportapp.core.teacher.model.request.TeFindAttendanceRequest;
import com.labreportapp.core.teacher.model.request.TeFindListAttendanceRequest;
import com.labreportapp.core.teacher.model.response.Base.TeAttendanceStudentMeetingRespone;
import com.labreportapp.core.teacher.model.response.TeAttendanceRespone;
import com.labreportapp.core.teacher.model.response.TeAttendanceStudentAllRespone;
import com.labreportapp.core.teacher.model.response.TeMeetingCustomToAttendanceRespone;
import com.labreportapp.core.teacher.repository.TeAttendanceRepository;
import com.labreportapp.core.teacher.repository.TeMeetingRepository;
import com.labreportapp.core.teacher.service.TeAttendanceSevice;
import com.labreportapp.core.teacher.service.TeStudentClassesService;
import com.labreportapp.entity.Attendance;
import com.labreportapp.infrastructure.constant.Message;
import com.labreportapp.infrastructure.constant.StatusAttendance;
import com.labreportapp.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author hieundph25894
 */
@Service
public class TeAttendanceServiceImpl implements TeAttendanceSevice {

    @Autowired
    private TeAttendanceRepository teAttendanceRepo;

    @Autowired
    private TeStudentClassesService teStudentClassesService;

    @Autowired
    private TeMeetingRepository teMeetingRepository;

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

    @Override
    public List<TeAttendanceStudentAllRespone> getListAttendanceStudentAllMeeting(String idClass) {
        List<TeAttendanceStudentAllRespone> listMeger = new ArrayList<>();
        List<TeMeetingCustomToAttendanceRespone> listMeeting = teMeetingRepository.findMeetingCustomToAttendanceByIdClass(idClass);
        if (listMeeting.size() == 0) {
            throw new RestApiException(Message.MEETING_NOT_EXISTS);
        }
        List<SimpleResponse> listStudent = teStudentClassesService.searchAllStudentByIdClass(idClass);
        if (listStudent.size() == 0) {
            throw new RestApiException(Message.CLASS_STUDENT_IS_EMPTY);
        }
        List<TeAttendanceRespone> listAttendance = teAttendanceRepo.findAttendanceByIdClass(idClass);
        if (listAttendance.size() == 0) {
            throw new RestApiException(Message.CLASS_STUDENT_IS_EMPTY);
        }
        listStudent.forEach(student -> {
            TeAttendanceStudentAllRespone obj = new TeAttendanceStudentAllRespone();
            obj.setIdStudent(student.getId());
            obj.setName(student.getName());
            obj.setEmail(student.getEmail());
            List<TeAttendanceStudentMeetingRespone> listAttendanceObj = new ArrayList<>();
            listMeeting.forEach(meeting -> {
                TeAttendanceStudentMeetingRespone attendanceObj = new TeAttendanceStudentMeetingRespone();
                attendanceObj.setIdAttendance(null);
                attendanceObj.setMeetingDate(meeting.getMeetingDate());
                attendanceObj.setStatusAttendance(null);
                attendanceObj.setMeetingPeriod(attendanceObj.getMeetingPeriod());
                listAttendance.forEach(attendance -> {
                            if (student.getId().equals(attendance.getIdStudent()) && meeting.getIdMeeting().equals(attendance.getIdMeeting())) {
                                attendanceObj.setIdAttendance(attendance.getIdAttendance());
                                attendanceObj.setStatusAttendance(attendance.getStatusAttendance());
                            }
                        }
                );
                listAttendanceObj.add(attendanceObj);
            });
            obj.setListAttendance(sortASCListAttendanceObj(listAttendanceObj));
            listMeger.add(obj);
        });
        return listMeger;
    }

    private List<TeAttendanceStudentMeetingRespone> sortASCListAttendanceObj(List<TeAttendanceStudentMeetingRespone> list) {
        List<TeAttendanceStudentMeetingRespone> sortedList = list.stream()
                .sorted(Comparator.comparing(TeAttendanceStudentMeetingRespone::getMeetingDate,
                        Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(TeAttendanceStudentMeetingRespone::getMeetingPeriod))
                .collect(Collectors.toList());
        return sortedList;
    }

}

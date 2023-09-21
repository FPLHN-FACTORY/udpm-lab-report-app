package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.model.request.TeFindAttendanceRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindListAttendanceRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.labreport.core.teacher.model.response.Base.TeAttendanceStudentMeetingRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceMessageRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceStudentAllRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceStudentRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomToAttendanceRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentCallApiResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeTeamsRespone;
import com.labreportapp.labreport.core.teacher.repository.TeAttendanceRepository;
import com.labreportapp.labreport.core.teacher.repository.TeMeetingRepository;
import com.labreportapp.labreport.core.teacher.repository.TeStudentClassesRepository;
import com.labreportapp.labreport.core.teacher.service.TeAttendanceSevice;
import com.labreportapp.labreport.core.teacher.service.TeStudentClassesService;
import com.labreportapp.labreport.entity.Attendance;
import com.labreportapp.labreport.entity.Meeting;
import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.infrastructure.constant.RoleTeam;
import com.labreportapp.labreport.infrastructure.constant.StatusAttendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author hieundph25894
 */
@Service
public class TeAttendanceServiceImpl implements TeAttendanceSevice {

    @Autowired
    private TeAttendanceRepository teAttendanceRepo;

    @Autowired
    private TeMeetingRepository teMeetingRepository;

    @Autowired
    private TeStudentClassesRepository teStudentClassesRepository;

    @Autowired
    private TeStudentClassesService teStudentClassesService;

    @Autowired
    private TeTeamsServiceImpl teTeamsService;

    @Override
    public List<TeAttendanceRespone> getListCustom(String idMeeting) {
        List<TeAttendanceRespone> list = teAttendanceRepo.findAttendanceByIdMeetgId(idMeeting);
        if (list == null) {
            return null;
        }
        return list;
    }

    @Override
    public TeAttendanceMessageRespone addOrUpdateAttendance(TeFindListAttendanceRequest request) {
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
        List<Attendance> listReturn = teAttendanceRepo.saveAll(listNew);
        TeAttendanceMessageRespone teAttendanceMessageRespone = randomSetLeadToMember(listReturn, request.getIdMeeting());
        return teAttendanceMessageRespone;
    }

    private TeAttendanceMessageRespone randomSetLeadToMember(List<Attendance> listAttendance, String idMeeting) {
        TeAttendanceMessageRespone objReturn = new TeAttendanceMessageRespone();
        Optional<Meeting> meeting = teMeetingRepository.findMeetingById(idMeeting);
        if (meeting.isPresent()) {
            TeFindStudentClasses requestIdClass = new TeFindStudentClasses();
            requestIdClass.setIdClass(meeting.get().getClassId());
            List<StudentClasses> listStudentClasses = teStudentClassesRepository.findStudentClassesByIdClass(meeting.get().getClassId());
            List<TeStudentCallApiResponse> listStudent = teStudentClassesService.searchApiStudentClassesByIdClass(meeting.get().getClassId());
            List<TeAttendanceStudentRespone> listStudentAttendance = new ArrayList<>();
            listStudent.forEach(student -> {
                listAttendance.forEach(attendance -> {
                    if (attendance.getStudentId().equals(student.getIdStudent())) {
                        TeAttendanceStudentRespone objAdd = new TeAttendanceStudentRespone();
                        objAdd.setIdAttendance(attendance.getId());
                        objAdd.setIdStudentClass(student.getIdStudentClass());
                        objAdd.setIdTeam(student.getIdTeam());
                        objAdd.setStatusAttendance(attendance.getStatus());
                        objAdd.setIdMeeting(idMeeting);
                        objAdd.setIdStudent(student.getIdStudent());
                        objAdd.setEmail(student.getEmail());
                        objAdd.setUsername(student.getUsername());
                        objAdd.setIdTeam(student.getIdTeam());
                        objAdd.setRole(student.getRole().equals("0") ? RoleTeam.LEADER : RoleTeam.MEMBER);
                        listStudentAttendance.add(objAdd);
                    }
                });
            });
            List<TeTeamsRespone> listTeam = teTeamsService.getAllTeams(requestIdClass);
            AtomicInteger countLeaderAbsent = new AtomicInteger();
            if (listTeam != null) {
                listTeam.forEach(team -> {
                    listStudentAttendance.forEach(student -> {
                        if (student.getIdTeam() != null && student.getIdTeam().equalsIgnoreCase(team.getId()) && student.getRole().equals(RoleTeam.LEADER) && student.getStatusAttendance().equals(StatusAttendance.NO)) {
                            Optional<StudentClasses> studentClassesFind = teStudentClassesRepository.findById(student.getIdStudentClass());
                            if (studentClassesFind.isPresent() && checkAtLeastStudentOnTeam(listStudentAttendance, team.getId())) {
                                StudentClasses studentClassesLead = new StudentClasses();
                                studentClassesLead.setId(student.getIdStudentClass());
                                studentClassesLead.setTeamId(studentClassesFind.get().getTeamId());
                                studentClassesLead.setStudentId(student.getIdStudent());
                                studentClassesLead.setEmail(student.getEmail());
                                studentClassesLead.setClassId(meeting.get().getClassId());
                                studentClassesLead.setRole(RoleTeam.MEMBER);
                                studentClassesLead.setStatus(studentClassesFind.get().getStatus());
                                studentClassesLead.setStatusStudentFeedBack(studentClassesFind.get().getStatusStudentFeedBack());
                                teStudentClassesRepository.save(studentClassesLead);

                                randomLead(listStudentAttendance, listTeam, listStudentClasses);
                                countLeaderAbsent.getAndIncrement();
                            }
                        }
                    });
                });
            }
            if (countLeaderAbsent.get() >= 2) {
                objReturn.setMessage("Một số trưởng nhóm nghỉ nên quyền trưởng nhóm đã được thay đổi ngẫu nhiên cho thành viên của nhóm. Lưu điểm danh thành công !");
            } else if (countLeaderAbsent.get() >= 1) {
                objReturn.setMessage("Một trưởng nhóm nghỉ nên quyền trưởng nhóm đã được thay đổi ngẫu nhiên cho thành viên của nhóm. Lưu điểm danh thành công !");
            } else {
                objReturn.setMessage("Lưu điểm danh thành công !");
            }
        }
        objReturn.setListAttendance(listAttendance);
        return objReturn;
    }

    public boolean checkAtLeastStudentOnTeam(List<TeAttendanceStudentRespone> listStudentAttendance, String idTeam) {
        long count = listStudentAttendance.stream()
                .filter(a -> {
                    String teamId = a.getIdTeam();
                    return teamId != null && teamId.equals(idTeam);
                })
                .count();
        return count >= 2;
    }

    private void randomLead(List<TeAttendanceStudentRespone> listStudent, List<TeTeamsRespone> listTeam, List<StudentClasses> listStudentClasses) {
        AtomicBoolean shouldContinue = new AtomicBoolean(true);
        if (listTeam != null && listStudent != null && listStudentClasses != null) {
            listTeam.forEach(team -> {
                shouldContinue.set(true);
                listStudent.forEach(student -> {
                    if (shouldContinue.get() && student.getIdTeam() != null && student.getIdTeam().equalsIgnoreCase(team.getId()) && student.getStatusAttendance().equals(StatusAttendance.YES) && student.getRole().equals(RoleTeam.MEMBER)) {
                        StudentClasses find = listStudentClasses.stream()
                                .filter(a -> a.getStudentId().equals(student.getIdStudent()))
                                .findFirst()
                                .orElse(null);
                        if (find != null) {
                            find.setRole(RoleTeam.LEADER);
                            teStudentClassesRepository.save(find);
                            shouldContinue.set(false);
                        }
                    }
                });
            });
        }
    }

    @Override
    public List<TeAttendanceStudentAllRespone> getListAttendanceStudentAllMeeting(String idClass) {
        List<TeAttendanceStudentAllRespone> listMeger = new ArrayList<>();
        List<TeMeetingCustomToAttendanceRespone> listMeeting = teMeetingRepository.findMeetingCustomToAttendanceByIdClass(idClass);
        if (listMeeting.size() == 0) {
            return null;
        }
        List<SimpleResponse> listStudent = teStudentClassesService.searchAllStudentByIdClass(idClass);
        if (listStudent.size() == 0) {
            return null;
        }
        List<TeAttendanceRespone> listAttendance = teAttendanceRepo.findAttendanceByIdClass(idClass);
        if (listAttendance.size() == 0) {
            return null;
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

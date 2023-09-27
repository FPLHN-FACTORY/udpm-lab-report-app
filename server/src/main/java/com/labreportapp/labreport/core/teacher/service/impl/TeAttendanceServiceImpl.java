package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.email.TeEmailSender;
import com.labreportapp.labreport.core.teacher.model.request.TeFindAttendanceRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindListAttendanceRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.labreport.core.teacher.model.response.Base.TeAttendanceStudentMeetingRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceMessageResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceStudentAllResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceStudentResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomToAttendanceResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentCallApiResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeTeamsRespone;
import com.labreportapp.labreport.core.teacher.repository.TeAttendanceRepository;
import com.labreportapp.labreport.core.teacher.repository.TeMeetingRepository;
import com.labreportapp.labreport.core.teacher.repository.TeStudentClassesRepository;
import com.labreportapp.labreport.core.teacher.service.TeAttendanceSevice;
import com.labreportapp.labreport.core.teacher.service.TeStudentClassesService;
import com.labreportapp.labreport.core.teacher.service.TeTeamsService;
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
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author hieundph25894
 */
@Service
public class TeAttendanceServiceImpl implements TeAttendanceSevice {

    @Autowired
    private TeAttendanceRepository teAttendanceRepository;

    @Autowired
    private TeMeetingRepository teMeetingRepository;

    @Autowired
    private TeStudentClassesRepository teStudentClassesRepository;

    @Autowired
    private TeStudentClassesService teStudentClassesService;

    @Autowired
    private TeTeamsService teTeamsService;

    @Autowired
    private TeEmailSender teEmailSender;

    @Override
    public List<TeAttendanceResponse> getListCustom(String idMeeting) {
        List<TeAttendanceResponse> list = teAttendanceRepository.findListAttendanceByIdMeeting(idMeeting);
        if (list == null) {
            return null;
        }
        return list;
    }

    @Override
    public TeAttendanceMessageResponse addOrUpdateAttendance(TeFindListAttendanceRequest request) {
        List<TeFindAttendanceRequest> list = request.getListAttendance();
        List<TeAttendanceResponse> listAttendance = teAttendanceRepository.findListAttendanceByIdMeeting(request.getIdMeeting());
        List<Attendance> listNew = new ArrayList<>();
        if (list.size() == 0 && listAttendance.size() == 0) {
            return null;
        }
        list.forEach(item -> {
            TeAttendanceResponse obj = findAttendanceByStudentId(listAttendance, item.getIdStudent());
            if (obj != null) {
                Attendance attendance = new Attendance();
                attendance.setId(obj.getIdAttendance());
                attendance.setName(obj.getNameMeeting());
                attendance.setMeetingId(obj.getIdMeeting());
                attendance.setStudentId(obj.getIdStudent());
                attendance.setNotes(item.getNotes());
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
                attendance.setNotes(item.getNotes());
                if (item.getStatusAttendance().equals("YES") || item.getStatusAttendance() == "YES") {
                    attendance.setStatus(StatusAttendance.YES);
                } else {
                    attendance.setStatus(StatusAttendance.NO);
                }
                attendance.setId(teAttendanceRepository.save(attendance).getId());
                listNew.add(attendance);
            }
        });
        List<Attendance> listReturn = teAttendanceRepository.saveAll(listNew);
        TeAttendanceMessageResponse teAttendanceMessageResponse = randomSetLeadToMember(listReturn, request.getIdMeeting());
        return teAttendanceMessageResponse;
    }

    private TeAttendanceResponse findAttendanceByStudentId(List<TeAttendanceResponse> listAttendance, String idStudent) {
        for (TeAttendanceResponse attendance : listAttendance) {
            if (attendance.getIdStudent().equals(idStudent)) {
                return attendance;
            }
        }
        return null;
    }

    private TeAttendanceMessageResponse randomSetLeadToMember(List<Attendance> listAttendance, String idMeeting) {
        TeAttendanceMessageResponse objReturn = new TeAttendanceMessageResponse();
        Optional<Meeting> meeting = teMeetingRepository.findMeetingById(idMeeting);
        if (meeting.isPresent()) {
            List<StudentClasses> listStudentClasses = teStudentClassesRepository.findStudentClassesByIdClass(meeting.get().getClassId());
            List<TeStudentCallApiResponse> listStudent = teStudentClassesService.searchApiStudentClassesByIdClass(meeting.get().getClassId());
            List<TeAttendanceStudentResponse> listStudentAttendance = new ArrayList<>();
            listStudent.forEach(student -> {
                listAttendance.forEach(attendance -> {
                    if (attendance.getStudentId().equals(student.getIdStudent())) {
                        TeAttendanceStudentResponse objAdd = new TeAttendanceStudentResponse();
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
            TeFindStudentClasses requestIdClass = new TeFindStudentClasses();
            requestIdClass.setIdClass(meeting.get().getClassId());
            List<TeTeamsRespone> listTeam = teTeamsService.getAllTeams(requestIdClass);
            AtomicInteger countLeaderAbsent = new AtomicInteger();
            if (listTeam != null) {
                listTeam.forEach(team -> {
                    listStudentAttendance.forEach(student -> {
                        if (checkAtLeastStudentOnTeam(listStudentAttendance, team.getId()) && student.getIdTeam() != null && student.getIdTeam().equalsIgnoreCase(team.getId()) && student.getRole().equals(RoleTeam.LEADER) && student.getStatusAttendance().equals(StatusAttendance.NO)) {
                            StudentClasses studentClassesFind = listStudentClasses.stream()
                                    .filter(a -> a.getStudentId().equals(student.getIdStudent()) && a.getId().equals(student.getIdStudentClass()))
                                    .findFirst()
                                    .orElse(null);
                            if (studentClassesFind != null) {
                                studentClassesFind.setRole(RoleTeam.MEMBER);
                                teStudentClassesRepository.save(studentClassesFind);
                                randomLead(listStudentAttendance, listTeam, listStudentClasses, team.getId());
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

    public boolean checkAtLeastStudentOnTeam(List<TeAttendanceStudentResponse> listStudentAttendance, String idTeam) {
        long count = listStudentAttendance.stream()
                .filter(a -> {
                    String teamId = a.getIdTeam();
                    return teamId != null && teamId.equals(idTeam) && a.getStatusAttendance().equals(StatusAttendance.YES);
                })
                .count();
        return count >= 1;
    }

    private void randomLead(List<TeAttendanceStudentResponse> listStudent, List<TeTeamsRespone> listTeam, List<StudentClasses> listStudentClasses, String idTeam) {
        if (listTeam != null && listStudent != null && listStudentClasses != null) {
            String[] listEmail;
            List<TeAttendanceStudentResponse> members = listStudent.stream()
                    .filter(student -> student.getIdTeam() != null && student.getIdTeam().equals(idTeam) && student.getStatusAttendance().equals(StatusAttendance.YES) && student.getRole().equals(RoleTeam.MEMBER))
                    .collect(Collectors.toList());
            if (members.size() >= 2) {
                Random random = new Random();
                int randomIndex = random.nextInt(members.size());
                TeAttendanceStudentResponse selectedMember = members.get(randomIndex);
                StudentClasses find = listStudentClasses.stream()
                        .filter(a -> a.getId().equals(selectedMember.getIdStudentClass()))
                        .findFirst()
                        .orElse(null);
                if (find != null) {
                    find.setRole(RoleTeam.LEADER);
                    teStudentClassesRepository.save(find);
                }
            } else if (members.size() == 1) {
                StudentClasses find = listStudentClasses.stream()
                        .filter(a -> a.getId().equals(members.get(0).getIdStudentClass()))
                        .findFirst()
                        .orElse(null);
                if (find != null) {
                   // listEmail = find.getEmail();
                    find.setRole(RoleTeam.LEADER);
                    teStudentClassesRepository.save(find);
                }
            }
           // teEmailSender.sendEmail(listEmail, "ĐIỂM DANH", "Chuyển trưởng nhóm", "Do trưởng nhóm của bạn đã nghỉ lên bạn sẽ được lên làm trưởng nhóm !");
        }
    }

    @Override
    public List<TeAttendanceStudentAllResponse> getListAttendanceStudentAllMeeting(String idClass) {
        List<TeAttendanceStudentAllResponse> listMeger = new ArrayList<>();
        List<TeMeetingCustomToAttendanceResponse> listMeeting = teMeetingRepository.findMeetingCustomToAttendanceByIdClass(idClass);
        if (listMeeting.size() == 0) {
            return null;
        }
        List<SimpleResponse> listStudent = teStudentClassesService.searchAllStudentByIdClass(idClass);
        if (listStudent.size() == 0) {
            return null;
        }
        List<TeAttendanceResponse> listAttendance = teAttendanceRepository.findAttendanceByIdClass(idClass);
        if (listAttendance.size() == 0) {
            return null;
        }
        listStudent.forEach(student -> {
            TeAttendanceStudentAllResponse obj = new TeAttendanceStudentAllResponse();
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

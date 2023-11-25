package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.email.TeEmailSender;
import com.labreportapp.labreport.core.teacher.model.request.TeFindAttendanceRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindListAttendanceRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentAttendanceRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.labreport.core.teacher.model.response.Base.TeAttendanceStudentMeetingRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceMessageResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceStudentAllResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeAttendanceStudentResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomToAttendanceResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentAttendanceRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentAttendedDetailRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentCallApiResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeTeamsRespone;
import com.labreportapp.labreport.core.teacher.repository.TeAttendanceRepository;
import com.labreportapp.labreport.core.teacher.repository.TeClassRepository;
import com.labreportapp.labreport.core.teacher.repository.TeMeetingRepository;
import com.labreportapp.labreport.core.teacher.repository.TeStudentClassesRepository;
import com.labreportapp.labreport.core.teacher.service.TeAttendanceSevice;
import com.labreportapp.labreport.core.teacher.service.TeStudentClassesService;
import com.labreportapp.labreport.core.teacher.service.TeTeamsService;
import com.labreportapp.labreport.entity.Attendance;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.Meeting;
import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.infrastructure.constant.RoleTeam;
import com.labreportapp.labreport.infrastructure.constant.StatusAttendance;
import com.labreportapp.labreport.infrastructure.constant.StatusMeeting;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.labreport.util.DateConverter;
import com.labreportapp.labreport.util.LoggerUtil;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
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
    private TeClassRepository teClassRepository;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private TeEmailSender teEmailSender;

    @Autowired
    private LoggerUtil loggerUtil;

    @Override
    public List<TeAttendanceResponse> getListCustom(String idMeeting) {
        List<TeAttendanceResponse> list = teAttendanceRepository.findListAttendanceByIdMeeting(idMeeting);
        if (list == null) {
            return null;
        }
        return list;
    }

    @Override
    @Transactional
    @Synchronized
    public TeAttendanceMessageResponse addOrUpdateAttendance(TeFindListAttendanceRequest request) {
        List<TeFindAttendanceRequest> list = request.getListAttendance();
        List<TeAttendanceResponse> listAttendance = teAttendanceRepository.findListAttendanceByIdMeeting(request.getIdMeeting());
        List<Attendance> listNew = new ArrayList<>();
        if (list.size() == 0 && listAttendance.size() == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        List<SimpleResponse> listInforStudent = teStudentClassesService.searchAllStudentByIdClass(request.getIdClass());
        list.forEach(item -> {
            TeAttendanceResponse obj = findAttendanceByStudentId(listAttendance, item.getIdStudent());
            if (obj != null) {
                Attendance attendance = new Attendance();
                attendance.setId(obj.getIdAttendance());
                attendance.setName(obj.getNameMeeting());
                attendance.setMeetingId(obj.getIdMeeting());
                attendance.setStudentId(obj.getIdStudent());
                attendance.setNotes(item.getNotes() == null ? "" : item.getNotes().trim());
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
                attendance.setNotes(item.getNotes() == null ? "" : item.getNotes().trim());
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
        Optional<Meeting> meeting = teMeetingRepository.findMeetingById(request.getIdMeeting());
        stringBuffer.append("Đã cập nhật điểm danh (" + meeting.get().getName() + " - " + request.getCodeClass() + ") ");
        if (meeting.isPresent()) {
            if (request.getNotes() != null) {
                if (!request.getNotes().equals(meeting.get().getNotes()) && !request.getNotes().equals("")) {
                    stringBuffer.append(" và cập nhật ghi chú là \"" + request.getNotes().trim() + "\". ");
                } else if (request.getNotes().equals("")) {
                    stringBuffer.append(" và xóa ghi chú buổi học.");
                }
            } else {
                if (!request.getNotes().equals("")) {
                    stringBuffer.append(" và thêm ghi chú buổi học là \"" + request.getNotes().trim() + ". ");
                }
            }
            Meeting meetingUp = meeting.get();
            meetingUp.setNotes(request.getNotes() == null ? "" : request.getNotes().trim());
            meetingUp.setStatusMeeting(StatusMeeting.BUOI_HOC);
            teMeetingRepository.save(meetingUp);
        }
        AtomicInteger countAbsent = new AtomicInteger();
        countAbsent.set(0);
        StringBuffer stringBufferCheckAbsent = new StringBuffer();
        listReturn.forEach(st -> {
            listInforStudent.forEach(infor -> {
                if (st.getStudentId().equals(infor.getId()) && st.getStatus() == StatusAttendance.NO) {
                    countAbsent.set(countAbsent.get() + 1);
                    stringBufferCheckAbsent.append(" ").append(infor.getName()).append(" - ").append(infor.getUserName()).append(",");
                }
            });
        });
        if (stringBufferCheckAbsent.length() > 0 && stringBufferCheckAbsent.charAt(stringBufferCheckAbsent.length() - 1) == ',') {
            stringBufferCheckAbsent.deleteCharAt(stringBufferCheckAbsent.length() - 1);
        }
        if (countAbsent.get() > 0) {
            stringBuffer.append(" Với số sinh viên nghỉ là ").append(countAbsent.get()).append(" - Gồm: ").append(stringBufferCheckAbsent.toString()).append(". ");
        } else {
            stringBuffer.append(" Với số sinh viên nghỉ là 0. ");
        }
        TeAttendanceMessageResponse teAttendanceMessageResponse = randomSetLeadToMember(listReturn, request.getIdMeeting(), stringBuffer);
        String nameSemester = loggerUtil.getNameSemesterByIdClass(request.getIdClass());
        loggerUtil.sendLogStreamClass(stringBuffer.toString(), request.getCodeClass(), nameSemester);
        listInforStudent.clear();
        listAttendance.clear();
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

    private TeAttendanceMessageResponse randomSetLeadToMember(List<Attendance> listAttendance, String idMeeting, StringBuffer message) {
        TeAttendanceMessageResponse objReturn = new TeAttendanceMessageResponse();
        Optional<Meeting> meetingDetail = teMeetingRepository.findMeetingById(idMeeting);
        Optional<Class> classDetail = teClassRepository.findById(meetingDetail.get().getClassId());
        if (meetingDetail.isPresent() && classDetail.isPresent()) {
            List<StudentClasses> listStudentClasses = teStudentClassesRepository
                    .findStudentClassesByIdClass(meetingDetail.get().getClassId());
            List<TeStudentCallApiResponse> listStudent = teStudentClassesService
                    .searchApiStudentClassesByIdClass(meetingDetail.get().getClassId());
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
                        objAdd.setName(student.getName());
                        objAdd.setUsername(student.getUsername());
                        objAdd.setIdTeam(student.getIdTeam());
                        if (student.getRole() == null) {
                            objAdd.setRole(RoleTeam.MEMBER);
                        } else {
                            objAdd.setRole(student.getRole().equals("0") ? RoleTeam.LEADER : RoleTeam.MEMBER);
                        }
                        listStudentAttendance.add(objAdd);
                    }
                });
            });
            TeFindStudentClasses requestIdClass = new TeFindStudentClasses();
            requestIdClass.setIdClass(meetingDetail.get().getClassId());
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
                                List<String> listMail = randomLead(listStudentAttendance, listTeam, listStudentClasses, team.getId(), message);
                                countLeaderAbsent.getAndIncrement();
                                sentEmail(meetingDetail.get(), classDetail.get(), listMail);
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

    private List<String> randomLead(List<TeAttendanceStudentResponse> listStudent, List<TeTeamsRespone> listTeam,
                                    List<StudentClasses> listStudentClasses, String idTeam, StringBuffer message) {
        List<String> listSentMail = new ArrayList<>();
        if (listTeam != null && listStudent != null && listStudentClasses != null) {
            List<TeAttendanceStudentResponse> members = listStudent.stream()
                    .filter(student -> student.getIdTeam() != null && student.getIdTeam().equals(idTeam)
                            && student.getStatusAttendance().equals(StatusAttendance.YES) && student.getRole().equals(RoleTeam.MEMBER))
                            .filter(Objects::nonNull)
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
                    listStudent.forEach(st -> {
                        if (st.getIdStudent().equals(find.getStudentId())) {
                            listTeam.forEach(tem -> {
                                if (tem.getId().equals(find.getTeamId())) {
                                    message.append("Chuyển sinh viên " + st.getName() + " - " + st.getUsername() +
                                            " làm trưởng nhóm của " + tem.getName() + ". ");
                                }
                            });
                        }
                    });
                }
            } else if (members.size() == 1) {
                StudentClasses find = listStudentClasses.stream()
                        .filter(a -> a.getId().equals(members.get(0).getIdStudentClass()))
                        .findFirst()
                        .orElse(null);
                if (find != null) {
                    listSentMail.add(find.getEmail());
                    find.setRole(RoleTeam.LEADER);
                    teStudentClassesRepository.save(find);
                    listStudent.forEach(st -> {
                        if (st.getIdStudent().equals(find.getStudentId())) {
                            listTeam.forEach(tem -> {
                                if (tem.getId().equals(find.getTeamId())) {
                                    message.append("Chuyển sinh viên " + st.getName() + " - " + st.getUsername() +
                                            " làm trưởng nhóm của " + tem.getName() + ". ");
                                }
                            });
                        }
                    });
                }
            }
        }
        return listSentMail;
    }

    private void sentEmail(Meeting meetingDetail, Class classDetail, List<String> listSentMail) {
        CompletableFuture.runAsync(() -> {
            String[] recipients = listSentMail.toArray(new String[listSentMail.size()]);
            teEmailSender.convertHtmlSendEmail(recipients, "[LƯU Ý]",
                    "Thay đổi vai trò nhóm lớp " + classDetail.getCode(),
                    "Do buổi học <strong>" + meetingDetail.getName() + "</strong> -" + " ngày <strong>"
                            + DateConverter.convertDateToStringNotTime(meetingDetail.getMeetingDate())
                            + "</strong> - <strong>" + meetingDetail.getMeetingPeriod() + "</strong> "
                            + " trưởng nhóm của bạn đã nghỉ nên bạn sẽ được chuyển vai trò thành trưởng nhóm !");
        });
    }

    private List<TeAttendanceStudentMeetingRespone> sortASCListAttendanceObj(List<TeAttendanceStudentMeetingRespone> list) {
        List<TeAttendanceStudentMeetingRespone> sortedList = list.stream()
                .sorted(Comparator.comparing(TeAttendanceStudentMeetingRespone::getMeetingDate,
                        Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(TeAttendanceStudentMeetingRespone::getMeetingPeriod))
                        .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return sortedList;
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
                attendanceObj.setMeetingPeriod(meeting.getMeetingPeriod());
                attendanceObj.setStatusAttendance(null);
                if (meeting.getStatusMeeting() == 1) {
                    attendanceObj.setStatusAttendance("N");
                }
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

    @Override
    public PageableObject<TeStudentAttendedDetailRespone> getAllAttendanceStudentById(TeFindStudentAttendanceRequest req) {
        Pageable pageable = PageRequest.of(req.getPage() - 1, req.getSize());
        Page<TeStudentAttendanceRespone> pageList = teAttendanceRepository.getAllStudentAttendanceById(req, pageable);
        List<TeStudentAttendanceRespone> listRespone = pageList.getContent();
        List<String> idUsers = listRespone.stream()
                .map(TeStudentAttendanceRespone::getTeacherId)
                .distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<SimpleResponse> listTeacher = callApiIdentity.handleCallApiGetListUserByListId(idUsers);
        Page<TeStudentAttendedDetailRespone> pageNew = pageList.map(item -> {
            TeStudentAttendedDetailRespone objNew = new TeStudentAttendedDetailRespone();
            objNew.setStt(item.getStt());
            objNew.setIdStudent(item.getId());
            objNew.setMeetingDate(item.getMeetingDate());
            objNew.setTypeMeeting(item.getTypeMeeting());
            objNew.setStatusMeeting(item.getStatusMeeting());
            objNew.setMeetingPeriod(item.getMeetingPeriod());
            objNew.setStartHour(item.getStartHour());
            objNew.setStartMinute(item.getStartMinute());
            objNew.setEndHour(item.getEndHour());
            objNew.setEndMinute(item.getEndMinute());
            objNew.setNameMeeting(item.getLesson());
            objNew.setStatus(item.getStatus());
            objNew.setNotes(item.getNotes());
            objNew.setIdTeacher(item.getTeacherId());
            if (item.getTeacherId() != null && listTeacher.size() != 0) {
                listTeacher.forEach(user -> {
                    if (user.getId().equals(item.getTeacherId())) {
                        objNew.setUsernameTeacher(user.getUserName());
                    }
                });
            }
            return objNew;
        });
        return new PageableObject<>(pageNew);
    }

}

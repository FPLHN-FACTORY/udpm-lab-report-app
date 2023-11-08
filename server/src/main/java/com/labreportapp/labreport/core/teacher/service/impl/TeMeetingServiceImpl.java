package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindScheduleMeetingClassRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindScheduleNowToTime;
import com.labreportapp.labreport.core.teacher.model.request.TeScheduleUpdateMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdateHomeWorkAndNoteInMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdateMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailMeetingTeamReportRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailTeamReportRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeHomeWorkAndNoteMeetingResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomToAttendanceResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeScheduleMeetingClassResponse;
import com.labreportapp.labreport.core.teacher.repository.TeHomeWorkRepository;
import com.labreportapp.labreport.core.teacher.repository.TeMeetingPeriodRepository;
import com.labreportapp.labreport.core.teacher.repository.TeMeetingRepository;
import com.labreportapp.labreport.core.teacher.repository.TeNoteRepository;
import com.labreportapp.labreport.core.teacher.repository.TeReportRepository;
import com.labreportapp.labreport.core.teacher.service.TeMeetingService;
import com.labreportapp.labreport.core.teacher.service.TeStudentClassesService;
import com.labreportapp.labreport.entity.HomeWork;
import com.labreportapp.labreport.entity.Meeting;
import com.labreportapp.labreport.entity.MeetingPeriod;
import com.labreportapp.labreport.entity.Note;
import com.labreportapp.labreport.entity.Report;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
public class TeMeetingServiceImpl implements TeMeetingService {

    @Autowired
    private TeMeetingRepository teMeetingRepository;

    @Autowired
    private TeHomeWorkRepository teHomeWorkRepository;

    @Autowired
    private TeNoteRepository teNoteRepository;

    @Autowired
    private TeStudentClassesService teStudentClassesService;

    @Autowired
    private TeReportRepository teReportRepository;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private TeMeetingPeriodRepository teMeetingPeriodRepository;

    @Override
    public TeMeetingResponse searchMeetingAndCheckAttendanceByIdMeeting(TeFindMeetingRequest request) {
        Optional<TeMeetingResponse> meeting = teMeetingRepository.searchMeetingByIdMeeting(request);
        if (!meeting.isPresent()) {
            throw new RestApiException(Message.MEETING_NOT_EXISTS);
        }
        TeMeetingResponse meetingFind = meeting.get();
        LocalDate dateNow = LocalDate.now();
        LocalDate dateMeeting = Instant.ofEpochMilli(meetingFind.getMeetingDate()).atZone(ZoneId.systemDefault()).toLocalDate();
        if (dateNow.isBefore(dateMeeting)) {
            throw new RestApiException(Message.MEETING_HAS_NOT_COME);
        } else if (dateNow.isAfter(dateMeeting)) {
            throw new RestApiException(Message.MEETING_IS_OVER);
        } else {
            Optional<MeetingPeriod> period = teMeetingPeriodRepository.findById(meetingFind.getIdMeetingPeriod());
            if (!period.isPresent()) {
                throw new RestApiException(Message.MEETING_PERIOD_NOT_EXITS);
            }
            Date currentDate = new Date();
            Date caHocStartTime = new Date(
                    currentDate.getYear(),
                    currentDate.getMonth(),
                    currentDate.getDate(),
                    period.get().getStartHour(),
                    period.get().getStartMinute()
            );
            Date caHocEndTime = new Date(
                    currentDate.getYear(),
                    currentDate.getMonth(),
                    currentDate.getDate(),
                    period.get().getEndHour(),
                    period.get().getEndMinute()
            );
            if (caHocStartTime.before(currentDate) && currentDate.before(caHocEndTime)) {
                return meetingFind;
            } else {
                throw new RestApiException(Message.MEETING_EDIT_ATTENDANCE_FAILD);
            }
        }
    }

    @Override
    public List<TeMeetingCustomResponse> searchMeetingByIdClass(TeFindMeetingRequest request) {
        List<TeMeetingResponse> list = teMeetingRepository.findMeetingByIdClass(request);
        List<String> idTeacherList = list.stream()
                .map(TeMeetingResponse::getIdTeacher)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listRespone = callApiIdentity.handleCallApiGetListUserByListId(idTeacherList);
        List<TeMeetingCustomResponse> listReturn = new ArrayList<>();
        list.forEach(reposi -> {
            listRespone.forEach(respone -> {
                if (reposi.getIdTeacher().equals(respone.getId())) {
                    TeMeetingCustomResponse obj = new TeMeetingCustomResponse();
                    obj.setId(reposi.getId());
                    obj.setName(reposi.getName());
                    obj.setDescriptions(reposi.getDescriptions());
                    obj.setMeetingDate(reposi.getMeetingDate());
                    obj.setMeetingPeriod(reposi.getMeetingPeriod());
                    obj.setStartHour(reposi.getStartHour());
                    obj.setStartMinute(reposi.getStartMinute());
                    obj.setEndHour(reposi.getEndHour());
                    obj.setEndMinute(reposi.getEndMinute());
                    obj.setIdClass(reposi.getIdClass());
                    obj.setTypeMeeting(reposi.getTypeMeeting());
                    obj.setIdTeacher(reposi.getIdTeacher());
                    obj.setUserNameTeacher(respone.getUserName());
                    obj.setStatusMeeting(reposi.getStatusMeeting());
                    listReturn.add(obj);
                }
            });
        });
        return listReturn;
    }

    @Override
    public TeDetailMeetingTeamReportRespone searchMeetingByIdMeeting(TeFindMeetingRequest request) {
        Optional<TeMeetingResponse> meeting = teMeetingRepository.searchMeetingByIdMeeting(request);
        if (!meeting.isPresent()) {
            throw new RestApiException(Message.MEETING_NOT_EXISTS);
        }
        TeMeetingResponse meetingResponse = meeting.get();
        TeFindMeetingRequest requestIdClass = new TeFindMeetingRequest();
        requestIdClass.setIdClass(meetingResponse.getIdClass());
        requestIdClass.setIdMeeting(meetingResponse.getId());
        List<TeDetailTeamReportRespone> listTeam = teMeetingRepository.findTeamReportByIdMeetingIdClass(requestIdClass);
        TeDetailMeetingTeamReportRespone objReturn = new TeDetailMeetingTeamReportRespone();
        objReturn.setId(meetingResponse.getId());
        objReturn.setIdClass(meetingResponse.getIdClass());
        objReturn.setIdTeacher(meetingResponse.getIdTeacher());
        objReturn.setName(meetingResponse.getName());
        objReturn.setDescriptions(meetingResponse.getDescriptions());
        objReturn.setMeetingDate(meetingResponse.getMeetingDate());
        objReturn.setTypeMeeting(meetingResponse.getTypeMeeting());
        objReturn.setMeetingPeriod(meetingResponse.getMeetingPeriod());
        LocalDate dateNow = LocalDate.now();
        LocalDate dateMeeting = Instant.ofEpochMilli(meetingResponse.getMeetingDate()).atZone(ZoneId.systemDefault()).toLocalDate();
        if (dateNow.isBefore(dateMeeting)) {
            objReturn.setListTeamReport(new ArrayList<>());
        } else if (dateNow.isAfter(dateMeeting)) {
            objReturn.setListTeamReport(listTeam);
        } else {
            Optional<MeetingPeriod> period = teMeetingPeriodRepository.findById(meetingResponse.getIdMeetingPeriod());
            if (!period.isPresent()) {
                throw new RestApiException(Message.MEETING_PERIOD_NOT_EXITS);
            }
            Date currentDate = new Date();
            Date caHocStartTime = new Date(
                    currentDate.getYear(),
                    currentDate.getMonth(),
                    currentDate.getDate(),
                    period.get().getStartHour(),
                    period.get().getStartMinute()
            );
            if (caHocStartTime.before(currentDate)) {
                objReturn.setListTeamReport(new ArrayList<>());
            } else {
                objReturn.setListTeamReport(listTeam);
            }
        }
        return objReturn;
    }

    @Override
    public TeHomeWorkAndNoteMeetingResponse searchDetailMeetingTeamByIdMeIdTeam(TeFindMeetingRequest request) {
        Optional<TeHomeWorkAndNoteMeetingResponse> object = teMeetingRepository.searchDetailMeetingTeamByIdMeIdTeam(request);
        if (!object.isPresent()) {
            return null;
        }
        return object.get();
    }

    @Override
    @Synchronized
    @Transactional
    public TeHomeWorkAndNoteMeetingResponse updateDetailMeetingTeamByIdMeIdTeam(TeUpdateHomeWorkAndNoteInMeetingRequest request) {
        HomeWork homeWorkNew = new HomeWork();
        homeWorkNew.setMeetingId(request.getIdMeeting());
        homeWorkNew.setTeamId(request.getIdTeam());
        homeWorkNew.setName("");
        homeWorkNew.setDescriptions(request.getDescriptionsHomeWork());
        if (request.getIdHomeWork() != null) {
            Optional<HomeWork> objectHW = teHomeWorkRepository.findById(request.getIdHomeWork());
            if (objectHW.isPresent()) {
                homeWorkNew.setId(objectHW.get().getId());
            }
        }
        teHomeWorkRepository.save(homeWorkNew);
        Note noteNew = new Note();
        noteNew.setMeetingId(request.getIdMeeting());
        noteNew.setTeamId(request.getIdTeam());
        noteNew.setName("");
        noteNew.setDescriptions(request.getDescriptionsNote());
        if (request.getIdNote() != null) {
            Optional<Note> objectNote = teNoteRepository.findById(request.getIdNote());
            if (objectNote.isPresent()) {
                noteNew.setId(objectNote.get().getId());
            }
        }
        teNoteRepository.save(noteNew);
        Report reportNew = new Report();
        reportNew.setMeetingId(request.getIdMeeting());
        reportNew.setTeamId(request.getIdTeam());
        reportNew.setDescriptions(request.getDescriptionsReport());
        if (request.getIdReport() != null) {
            Optional<Report> objectReport = teReportRepository.findById(request.getIdReport());
            if (objectReport.isPresent()) {
                reportNew.setId(objectReport.get().getId());
            }
        }
        teReportRepository.save(reportNew);
        TeFindMeetingRequest teFind = new TeFindMeetingRequest();
        teFind.setIdTeam(request.getIdTeam());
        teFind.setIdMeeting(request.getIdMeeting());
        Optional<TeHomeWorkAndNoteMeetingResponse> objectFind = teMeetingRepository.searchDetailMeetingTeamByIdMeIdTeam(teFind);
        if (!objectFind.isPresent()) {
            return null;
        }
        return objectFind.get();
    }

    @Override
    public List<TeScheduleMeetingClassResponse> searchScheduleToDayByIdTeacherAndMeetingDate(TeFindScheduleMeetingClassRequest request) {
        request.setIdTeacher(labReportAppSession.getUserId());
        List<TeScheduleMeetingClassResponse> list = teMeetingRepository.searchScheduleToDayByIdTeacherAndMeetingDate(request);
        if (list.size() == 0) {
            return null;
        }
        return list;
    }

    @Override
    public PageableObject<TeScheduleMeetingClassResponse> searchScheduleNowToByIdTeacher(TeFindScheduleNowToTime request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        Page<TeScheduleMeetingClassResponse> list = teMeetingRepository.searchScheduleNowToTimeByIdTeacher(request, pageable);
        if (list == null) {
            return null;
        }
        return new PageableObject<>(list);
    }

    @Override
    @Transactional
    public List<TeScheduleMeetingClassResponse> updateAddressMeeting(TeScheduleUpdateMeetingRequest request) {
        List<TeUpdateMeetingRequest> list = request.getListMeeting();
        if (list.size() == 0) {
            throw new RestApiException(Message.SCHEDULE_TODAY_IS_EMPTY);
        }
        List<Meeting> listNew = new ArrayList<>();
        list.forEach(item -> {
            Meeting meeting = teMeetingRepository.findById(item.getIdMeeting()).get();
            meeting.setAddress(item.getMeetingAddress() == null ? "" : item.getMeetingAddress().trim());
            listNew.add(meeting);
        });
        teMeetingRepository.saveAll(listNew);
        TeFindScheduleMeetingClassRequest find = new TeFindScheduleMeetingClassRequest();
        find.setIdTeacher(labReportAppSession.getUserId());
        return teMeetingRepository.searchScheduleToDayByIdTeacherAndMeetingDate(find);
    }

    private List<TeMeetingCustomToAttendanceResponse> sortASCListAttendanceObj(List<TeMeetingCustomToAttendanceResponse> list) {
        List<TeMeetingCustomToAttendanceResponse> sortedList = list.stream()
                .sorted(Comparator.comparing(TeMeetingCustomToAttendanceResponse::getMeetingDate,
                        Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(TeMeetingCustomToAttendanceResponse::getMeetingPeriod))
                .collect(Collectors.toList());
        return sortedList;
    }

    @Override
    public List<TeMeetingCustomToAttendanceResponse> listMeetingAttendanceAllByIdClass(String idClass) {
        List<TeMeetingCustomToAttendanceResponse> listMeeting = teMeetingRepository.findMeetingCustomToAttendanceByIdClass(idClass);
        if (listMeeting.size() == 0) {
            return null;
        }
        List<TeMeetingCustomToAttendanceResponse> listReturn = sortASCListAttendanceObj(listMeeting);
        return listReturn;
    }

}

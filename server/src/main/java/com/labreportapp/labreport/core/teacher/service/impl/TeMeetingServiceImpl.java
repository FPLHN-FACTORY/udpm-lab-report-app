package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindScheduleMeetingClassRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindScheduleNowToTime;
import com.labreportapp.labreport.core.teacher.model.request.TeScheduleUpdateMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdateHomeWorkAndNoteInMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdateMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeHomeWorkAndNoteMeetingRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomToAttendanceRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeScheduleMeetingClassRespone;
import com.labreportapp.labreport.core.teacher.repository.TeHomeWorkRepository;
import com.labreportapp.labreport.core.teacher.repository.TeMeetingRepository;
import com.labreportapp.labreport.core.teacher.repository.TeNoteRepository;
import com.labreportapp.labreport.core.teacher.service.TeMeetingService;
import com.labreportapp.labreport.core.teacher.service.TeStudentClassesService;
import com.labreportapp.labreport.entity.HomeWork;
import com.labreportapp.labreport.entity.Meeting;
import com.labreportapp.labreport.entity.Note;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Override
    public List<TeMeetingCustomRespone> searchMeetingByIdClass(TeFindMeetingRequest request) {
        List<TeMeetingRespone> list = teMeetingRepository.findMeetingByIdClass(request);
        List<String> idTeacherList = list.stream()
                .map(TeMeetingRespone::getIdTeacher)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listRespone = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idTeacherList);
        List<TeMeetingCustomRespone> listReturn = new ArrayList<>();
        list.forEach(reposi -> {
            listRespone.forEach(respone -> {
                if (reposi.getIdTeacher().equals(respone.getId())) {
                    TeMeetingCustomRespone obj = new TeMeetingCustomRespone();
                    obj.setId(reposi.getId());
                    obj.setName(reposi.getName());
                    obj.setDescriptions(reposi.getDescriptions());
                    obj.setMeetingDate(reposi.getMeetingDate());
                    obj.setMeetingPeriod(reposi.getMeetingPeriod());
                    obj.setIdClass(reposi.getIdClass());
                    obj.setTypeMeeting(reposi.getTypeMeeting());
                    obj.setIdTeacher(reposi.getIdTeacher());
                    obj.setUserNameTeacher(respone.getUserName());
                    listReturn.add(obj);
                }
            });
        });
        return listReturn;
    }

    @Override
    public Integer countMeetingByClassId(String idClass) {
        return teMeetingRepository.countMeetingByClassId(idClass);
    }

    @Override
    public TeMeetingRespone searchMeetingByIdMeeting(TeFindMeetingRequest request) {
        Optional<TeMeetingRespone> meeting = teMeetingRepository.searchMeetingByIdMeeting(request);
        if (!meeting.isPresent()) {
            throw new RestApiException(Message.MEETING_NOT_EXISTS);
        }
        return meeting.get();
    }

    private Integer checkPeriod(Integer meetingPeriod) {
        LocalDateTime currentTime = LocalDateTime.now();
        String meetingPeriodStr = "";
        LocalDateTime startTime;
        LocalDateTime endTime;
        switch (meetingPeriod) {
            case 0:
                meetingPeriodStr = "7:15 - 9:15";
                startTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 7, 15);
                endTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 9, 15);
                if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
                    return 0;
                } else {
                    return 1;
                }
            case 1:
                meetingPeriodStr = "9:25 - 11:25";
                startTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 9, 25);
                endTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 11, 25);
                if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
                    return 0;
                } else {
                    return 1;
                }
            case 2:
                meetingPeriodStr = "12:00 - 14:00";
                startTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 12, 00);
                endTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 14, 00);
                if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
                    return 0;
                } else {
                    return 1;
                }
            case 3:
                meetingPeriodStr = "14:10 - 16:10";
                startTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 14, 10);
                endTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 16, 10);
                if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
                    return 0;
                } else {
                    return 1;
                }
            case 4:
                meetingPeriodStr = "16:20 - 18:20";
                startTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 16, 20);
                endTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 18, 20);
                if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
                    return 0;
                } else {
                    return 1;
                }
            case 5:
                meetingPeriodStr = "18:30 - 20:30";
                startTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 18, 30);
                endTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 20, 30);
                if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
                    return 0;
                } else {
                    return 1;
                }
            case 6:
                meetingPeriodStr = "20:40 - 22:40";
                startTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 20, 40);
                endTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 22, 40);
                if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
                    return 0;
                } else {
                    return 1;
                }
            case 7:
                meetingPeriodStr = "22:50 - 00:50";
                startTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 22, 50);
                endTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 0, 50);
                if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
                    return 0;
                } else {
                    return 1;
                }
            case 8:
                meetingPeriodStr = "01:00 - 3:00";
                startTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 1, 00);
                endTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 3, 00);
                if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
                    return 0;
                } else {
                    return 1;
                }
            case 9:
                meetingPeriodStr = "03:10 - 05:10";
                startTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 3, 10);
                endTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 5, 10);
                if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
                    return 0;
                } else {
                    return 1;
                }
            default:
                return 1;
        }
    }

    @Override
    public TeMeetingRespone searchMeetingAndCheckAttendanceByIdMeeting(TeFindMeetingRequest request) {
        Optional<TeMeetingRespone> meeting = teMeetingRepository.searchMeetingByIdMeeting(request);
        if (!meeting.isPresent()) {
            throw new RestApiException(Message.MEETING_NOT_EXISTS);
        }
        TeMeetingRespone meetingFind = meeting.get();
        LocalDate dateNow = LocalDate.now();
        LocalDate dateMeeting = Instant.ofEpochMilli(meetingFind.getMeetingDate()).atZone(ZoneId.systemDefault()).toLocalDate();
        if (dateNow.isBefore(dateMeeting)) {
            throw new RestApiException(Message.MEETING_HAS_NOT_COME);
        } else if (dateNow.isAfter(dateMeeting)) {
            throw new RestApiException(Message.MEETING_IS_OVER);
        } else {
            int checkPeriord = checkPeriod(meetingFind.getMeetingPeriod());
            if (checkPeriord != 0) {
                throw new RestApiException(Message.MEETING_EDIT_ATTENDANCE_FAILD);
            } else {
                return meetingFind;
            }
        }
    }

    @Override
    public TeHomeWorkAndNoteMeetingRespone searchDetailMeetingTeamByIdMeIdTeam(TeFindMeetingRequest request) {
        Optional<TeHomeWorkAndNoteMeetingRespone> object = teMeetingRepository.searchDetailMeetingTeamByIdMeIdTeam(request);
        if (!object.isPresent()) {
            return null;
        }
        return object.get();
    }

    @Override
    public TeHomeWorkAndNoteMeetingRespone updateDetailMeetingTeamByIdMeIdTeam(TeUpdateHomeWorkAndNoteInMeetingRequest request) {
        Optional<HomeWork> objectHW = teHomeWorkRepository.findById(request.getIdHomeWork());
        if (!objectHW.isPresent()) {
            HomeWork homeWorkNew = new HomeWork();
            homeWorkNew.setMeetingId(request.getIdMeeting());
            homeWorkNew.setTeamId(request.getIdTeam());
            homeWorkNew.setName("");
            homeWorkNew.setDescriptions(request.getDescriptionsHomeWork());
            homeWorkNew.setId(teHomeWorkRepository.save(homeWorkNew).getId());
        } else {
            HomeWork homeWork = objectHW.get();
            homeWork.setId(request.getIdHomeWork());
            homeWork.setDescriptions(request.getDescriptionsHomeWork());
            teHomeWorkRepository.save(homeWork);
        }
        Optional<Note> objectNote = teNoteRepository.findById(request.getIdNote());
        if (!objectNote.isPresent()) {
            Note noteNew = new Note();
            noteNew.setMeetingId(request.getIdMeeting());
            noteNew.setTeamId(request.getIdTeam());
            noteNew.setName("");
            noteNew.setDescriptions(request.getDescriptionsNote());
            noteNew.setId(teNoteRepository.save(noteNew).getId());
        } else {
            Note note = objectNote.get();
            note.setId(request.getIdNote());
            note.setDescriptions(request.getDescriptionsNote());
            teNoteRepository.save(note);
            TeFindMeetingRequest te = new TeFindMeetingRequest();
            te.setIdMeeting(note.getMeetingId());
            te.setIdTeam(note.getTeamId());
        }
        TeFindMeetingRequest teFind = new TeFindMeetingRequest();
        teFind.setIdTeam(request.getIdTeam());
        teFind.setIdMeeting(request.getIdMeeting());
        Optional<TeHomeWorkAndNoteMeetingRespone> objectFind = teMeetingRepository.searchDetailMeetingTeamByIdMeIdTeam(teFind);
        if (!objectFind.isPresent()) {
            throw new RestApiException(Message.MEETING_HOMEWORK_NOTE_NOT_EXISTS);
        }
        return objectFind.get();
    }

    @Override
    public List<TeScheduleMeetingClassRespone> searchScheduleToDayByIdTeacherAndMeetingDate(TeFindScheduleMeetingClassRequest request) {
        List<TeScheduleMeetingClassRespone> list = teMeetingRepository.searchScheduleToDayByIdTeacherAndMeetingDate(request);
        if (list.size() == 0) {
            return null;
        }
        return list;
    }

    @Override
    public List<TeScheduleMeetingClassRespone> searchScheduleNowToByIdTeacher(TeFindScheduleNowToTime request) {
        List<TeScheduleMeetingClassRespone> list = teMeetingRepository.searchScheduleNowToTimeByIdTeacher(request);
        if (list.size() == 0) {
            return null;
        }
        return list;
    }

    @Override
    public List<TeScheduleMeetingClassRespone> updateAddressMeeting(TeScheduleUpdateMeetingRequest request) {
        List<TeUpdateMeetingRequest> list = request.getListMeeting();
        if (list.size() == 0) {
            throw new RestApiException(Message.SCHEDULE_TODAY_IS_EMPTY);
        }
        List<Meeting> listNew = new ArrayList<>();
        list.forEach(item -> {
            Meeting meeting = teMeetingRepository.findById(item.getIdMeeting()).get();
            meeting.setAddress(item.getMeetingAddress());
            listNew.add(meeting);
        });
        teMeetingRepository.saveAll(listNew);
        TeFindScheduleMeetingClassRequest find = new TeFindScheduleMeetingClassRequest();
        find.setIdTeacher(request.getIdTeacher());
        return teMeetingRepository.searchScheduleToDayByIdTeacherAndMeetingDate(find);
    }

    @Override
    public List<TeMeetingCustomToAttendanceRespone> listMeetingAttendanceAllByIdClass(String idClass) {
        List<TeMeetingCustomToAttendanceRespone> listMeeting = teMeetingRepository.findMeetingCustomToAttendanceByIdClass(idClass);
        return listMeeting;
    }

}

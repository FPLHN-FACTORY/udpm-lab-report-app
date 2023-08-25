package com.labreportapp.core.teacher.service.impl;

import com.labreportapp.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.core.teacher.model.request.TeFindScheduleMeetingClassRequest;
import com.labreportapp.core.teacher.model.request.TeScheduleUpdateMeetingRequest;
import com.labreportapp.core.teacher.model.request.TeUpdateHomeWorkAndNoteInMeetingRequest;
import com.labreportapp.core.teacher.model.request.TeUpdateMeetingRequest;
import com.labreportapp.core.teacher.model.response.TeHomeWorkAndNoteMeetingRespone;
import com.labreportapp.core.teacher.model.response.TeMeetingRespone;
import com.labreportapp.core.teacher.model.response.TeScheduleMeetingClassRespone;
import com.labreportapp.core.teacher.repository.TeHomeWorkRepository;
import com.labreportapp.core.teacher.repository.TeMeetingRepository;
import com.labreportapp.core.teacher.repository.TeNoteRepository;
import com.labreportapp.core.teacher.service.TeMeetingService;
import com.labreportapp.entity.HomeWork;
import com.labreportapp.entity.Meeting;
import com.labreportapp.entity.Note;
import com.labreportapp.infrastructure.constant.Message;
import com.labreportapp.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<TeMeetingRespone> searchMeetingByIdClass(TeFindMeetingRequest request) {
        List<TeMeetingRespone> list = teMeetingRepository.findMeetingByIdClass(request);
        return list;
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

    @Override
    public TeMeetingRespone searchMeetingAndCheckAttendanceByIdMeeting(TeFindMeetingRequest request) {
        Optional<TeMeetingRespone> meeting = teMeetingRepository.searchMeetingByIdMeeting(request);
        if (!meeting.isPresent()) {
            throw new RestApiException(Message.MEETING_NOT_EXISTS);
        }
        TeMeetingRespone meetingFind = meeting.get();
        Long dateNow = new Date().getTime();
        Long dateMeeting = meetingFind.getMeetingDate();
        Long check = dateNow - dateMeeting;
        if (check < 0) {
            throw new RestApiException(Message.MEETING_HAS_NOT_COME);
        }
        if (check > 86400000) {
            throw new RestApiException(Message.MEETING_IS_OVER);
        }
        return meeting.get();
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
        if (list.size() < 0) {
            throw new RestApiException(Message.SCHEDULE_TODAY_IS_EMPTY);
        }
        return list;
    }

    @Override
    public List<TeScheduleMeetingClassRespone> updateDescriptionMeeting(TeScheduleUpdateMeetingRequest request) {
        List<TeUpdateMeetingRequest> list = request.getListMeeting();
        if (list.size() < 0) {
            throw new RestApiException(Message.SCHEDULE_TODAY_IS_EMPTY);
        }
        List<Meeting> listNew = new ArrayList<>();
        list.forEach(item -> {
            Meeting meeting = teMeetingRepository.findById(item.getIdMeeting()).get();
            meeting.setDescriptions(item.getDescriptionsMeeting());
            listNew.add(meeting);
        });
        teMeetingRepository.saveAll(listNew);
        TeFindScheduleMeetingClassRequest find = new TeFindScheduleMeetingClassRequest();
        find.setIdTeacher(request.getIdTeacher());
        return teMeetingRepository.searchScheduleToDayByIdTeacherAndMeetingDate(find);
    }


//    @Override
//    public List<TeHomeWorkAndNoteMeetingRespone> searchMeetingHomeWNoteByIdMeetingAndIdClass(TeFindMeetingRequest request) {
//        List<TeHomeWorkAndNoteMeetingRespone> list = teMeetingRepository.findTeamAndHomeWorkAndNoteByIdClassAndIdMeeting(request);
//        return list;
//    }

}

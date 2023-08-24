package com.labreportapp.core.teacher.service.impl;

import com.labreportapp.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.core.teacher.model.request.TeUpdateHomeWorkAndNoteInMeetingRequest;
import com.labreportapp.core.teacher.model.response.TeHomeWorkAndNoteMeetingRespone;
import com.labreportapp.core.teacher.model.response.TeMeetingRespone;
import com.labreportapp.core.teacher.repository.TeHomeWorkRepository;
import com.labreportapp.core.teacher.repository.TeMeetingRepository;
import com.labreportapp.core.teacher.repository.TeNoteRepository;
import com.labreportapp.core.teacher.service.TeMeetingService;
import com.labreportapp.entity.HomeWork;
import com.labreportapp.entity.Note;
import com.labreportapp.infrastructure.constant.Message;
import com.labreportapp.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            homeWorkNew.setId(request.getIdHomeWork());
           // homeWorkNew.setMeetingId(request.);
            return null;
        }
        HomeWork homeWork = objectHW.get();
        homeWork.setId(request.getIdHomeWork());
        homeWork.setDescriptions(request.getDescriptionsHomeWork());
        teHomeWorkRepository.save(homeWork);
        Optional<Note> objectNote = teNoteRepository.findById(request.getIdNote());
        if (!objectNote.isPresent()) {
            return null;
        }
        Note note = objectNote.get();
        note.setId(request.getIdNote());
        note.setDescriptions(request.getDescriptionsNote());
        teNoteRepository.save(note);
        TeFindMeetingRequest te = new TeFindMeetingRequest();
        te.setIdMeeting(note.getMeetingId());
        te.setIdTeam(note.getTeamId());
        Optional<TeHomeWorkAndNoteMeetingRespone> objectFind = teMeetingRepository.searchDetailMeetingTeamByIdMeIdTeam(te);
        if (!objectFind.isPresent()) {
            return null;
        }
        return objectFind.get();
    }

//    @Override
//    public List<TeHomeWorkAndNoteMeetingRespone> searchMeetingHomeWNoteByIdMeetingAndIdClass(TeFindMeetingRequest request) {
//        List<TeHomeWorkAndNoteMeetingRespone> list = teMeetingRepository.findTeamAndHomeWorkAndNoteByIdClassAndIdMeeting(request);
//        return list;
//    }

}

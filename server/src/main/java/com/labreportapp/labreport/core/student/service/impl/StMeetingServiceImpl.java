package com.labreportapp.labreport.core.student.service.impl;

import com.labreportapp.labreport.core.student.model.request.StFindMeetingRequest;
import com.labreportapp.labreport.core.student.model.request.StUpdateHomeWorkAndNotebyLeadTeamRequest;
import com.labreportapp.labreport.core.student.model.response.StHomeWordAndNoteResponse;
import com.labreportapp.labreport.core.student.model.response.StMeetingResponse;
import com.labreportapp.labreport.core.student.model.response.StMyTeamInClassResponse;
import com.labreportapp.labreport.core.student.repository.*;
import com.labreportapp.labreport.core.student.service.StMeetingService;
import com.labreportapp.labreport.entity.HomeWork;
import com.labreportapp.labreport.entity.Note;
import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.infrastructure.constant.RoleTeam;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author quynhncph26201
 */
@Service
public class StMeetingServiceImpl implements StMeetingService {

    @Autowired
    private StMeetingRepository stMeetingrepository;

    @Autowired
    private StReportRepository stReportRepository;

    @Autowired
    private StHomeWorkRepository stHomeWorkRepository;

    @Autowired
    private StNoteRepository stNoteRepository;

    @Autowired
    private StLeadTeamRepository stLeadTeamRepository;

    @Override
    public List<StMeetingResponse> searchMeetingByIdClass(StFindMeetingRequest request) {
        return stMeetingrepository.findMeetingByIdClass(request);
    }

    @Override
    public Integer countMeetingByClassId(String idClass) {
        return stMeetingrepository.countMeetingByClassId(idClass);
    }

    @Override
    public StMeetingResponse searchMeetingByIdMeeting(StFindMeetingRequest request) {
        Optional<StMeetingResponse> meeting = stMeetingrepository.searchMeetingByIdMeeting(request);
        if (!meeting.isPresent()) {
            throw new RestApiException(Message.MEETING_NOT_EXISTS);
        }
        if (meeting.get().getMeetingDate() > new Date().getTime()) {
            throw new RestApiException(Message.CHUA_DEN_THOI_GIAN_CUA_BUOI_HOC);
        }
        return meeting.get();
    }

    @Override
    public StHomeWordAndNoteResponse searchDetailMeetingTeamById(StFindMeetingRequest request) {
        Optional<StHomeWordAndNoteResponse> object = stMeetingrepository.searchDetailMeetingTeamByIdMeIdTeam(request);
        if (!object.isPresent()) {
            return null;
        }
        return object.get();
    }

    @Override
    public List<StMyTeamInClassResponse> getAllTeams(StFindMeetingRequest stFindStudentClasses) {
        return stMeetingrepository.getTeamInClass(stFindStudentClasses);
    }

    @Override
    public StHomeWordAndNoteResponse updateDetailMeetingTeamByLeadTeam(StUpdateHomeWorkAndNotebyLeadTeamRequest request) {
        Optional<StudentClasses> optionalStudentClasses = stLeadTeamRepository.findStudentClassesByStudentId(request.getIdStudent());

        if (optionalStudentClasses.isPresent()) {
            StudentClasses studentClasses = optionalStudentClasses.get();
            if (studentClasses.getRole().equals(RoleTeam.LEADER)) {
                Optional<HomeWork> objectHW = stHomeWorkRepository.findById(request.getIdHomeWork());
                if (!objectHW.isPresent()) {
                    HomeWork homeWorkNew = new HomeWork();
                    homeWorkNew.setMeetingId(request.getIdMeeting());
                    homeWorkNew.setTeamId(request.getIdTeam());
                    homeWorkNew.setName("");
                    homeWorkNew.setDescriptions(request.getDescriptionsHomeWork());
                    homeWorkNew.setId(stHomeWorkRepository.save(homeWorkNew).getId());
                } else {
                    HomeWork homeWork = objectHW.get();
                    homeWork.setId(request.getIdHomeWork());
                    homeWork.setDescriptions(request.getDescriptionsHomeWork());
                    stHomeWorkRepository.save(homeWork);
                }
                Optional<Note> objectNote = stNoteRepository.findById(request.getIdNote());
                if (!objectNote.isPresent()) {
                    Note noteNew = new Note();
                    noteNew.setMeetingId(request.getIdMeeting());
                    noteNew.setTeamId(request.getIdTeam());
                    noteNew.setName("");
                    noteNew.setDescriptions(request.getDescriptionsNote());
                    noteNew.setId(stNoteRepository.save(noteNew).getId());
                } else {
                    Note note = objectNote.get();
                    note.setId(request.getIdNote());
                    note.setDescriptions(request.getDescriptionsNote());
                    stNoteRepository.save(note);
                    StFindMeetingRequest st = new StFindMeetingRequest();
                    st.setIdMeeting(note.getMeetingId());
                    st.setIdTeam(note.getTeamId());
                }
            } else {
                throw new RestApiException(Message.YOU_MUST_LEADER);
            }
        }
        StFindMeetingRequest stFind = new StFindMeetingRequest();
        stFind.setIdTeam(request.getIdTeam());
        stFind.setIdMeeting(request.getIdMeeting());
        Optional<StHomeWordAndNoteResponse> objectFind = stMeetingrepository.searchDetailMeetingTeamByIdMeIdTeam(stFind);
        if (!objectFind.isPresent()) {
            throw new RestApiException(Message.MEETING_HOMEWORK_NOTE_NOT_EXISTS);
        }

        return objectFind.get();
    }

    @Override
    public Integer getRoleByIdStudent(final StFindMeetingRequest request) {
        return stMeetingrepository.getRoleByIdStudent(request);
    }



}

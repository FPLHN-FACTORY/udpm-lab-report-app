package com.labreportapp.core.student.service.impl;

import com.labreportapp.core.student.model.request.StFindMeetingRequest;
import com.labreportapp.core.student.model.response.StHomeWordAndNoteResponse;
import com.labreportapp.core.student.model.response.StMeetingResponse;
import com.labreportapp.core.student.model.response.StMyTeamInClassResponse;
import com.labreportapp.core.student.repository.StMeetingRepository;
import com.labreportapp.core.student.service.StMeetingService;
import com.labreportapp.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.infrastructure.constant.Message;
import com.labreportapp.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
        if(meeting.get().getMeetingDate() > new Date().getTime()) {
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
}

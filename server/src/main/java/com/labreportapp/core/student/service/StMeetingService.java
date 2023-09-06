package com.labreportapp.core.student.service;

import com.labreportapp.core.student.model.request.StFindMeetingRequest;
import com.labreportapp.core.student.model.response.StHomeWordAndNoteResponse;
import com.labreportapp.core.student.model.response.StMeetingResponse;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface StMeetingService {
    List<StMeetingResponse> searchMeetingByIdClass(final StFindMeetingRequest request);

    Integer countMeetingByClassId(String idClass);

    StMeetingResponse searchMeetingByIdMeeting(final StFindMeetingRequest request);

    StHomeWordAndNoteResponse searchDetailMeetingTeamById(final StFindMeetingRequest request);

}

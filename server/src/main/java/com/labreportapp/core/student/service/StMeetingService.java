package com.labreportapp.core.student.service;

import com.labreportapp.core.student.model.request.StFindMeetingRequest;
import com.labreportapp.core.student.model.request.StUpdateHomeWorkAndNotebyLeadTeamRequest;
import com.labreportapp.core.student.model.response.StHomeWordAndNoteResponse;
import com.labreportapp.core.student.model.response.StMeetingResponse;
import com.labreportapp.core.student.model.response.StMyStudentTeamResponse;
import com.labreportapp.core.student.model.response.StMyTeamInClassResponse;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface StMeetingService {

    List<StMeetingResponse> searchMeetingByIdClass(final StFindMeetingRequest request);

    Integer countMeetingByClassId(String idClass);

    StMeetingResponse searchMeetingByIdMeeting(final StFindMeetingRequest request);

    StHomeWordAndNoteResponse searchDetailMeetingTeamById(final StFindMeetingRequest request);

    List<StMyTeamInClassResponse> getAllTeams(final StFindMeetingRequest stFindStudentClasses);

    StHomeWordAndNoteResponse updateDetailMeetingTeamByLeadTeam(@Valid StUpdateHomeWorkAndNotebyLeadTeamRequest request);

    Integer getRoleByIdStudent(final StFindMeetingRequest stFindStudentClasses);

}

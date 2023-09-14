package com.labreportapp.core.teacher.service;

import com.labreportapp.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.core.teacher.model.request.TeFindScheduleMeetingClassRequest;
import com.labreportapp.core.teacher.model.request.TeScheduleUpdateMeetingRequest;
import com.labreportapp.core.teacher.model.request.TeUpdateHomeWorkAndNoteInMeetingRequest;
import com.labreportapp.core.teacher.model.response.TeHomeWorkAndNoteMeetingRespone;
import com.labreportapp.core.teacher.model.response.TeMeetingCustomToAttendanceRespone;
import com.labreportapp.core.teacher.model.response.TeMeetingRespone;
import com.labreportapp.core.teacher.model.response.TeScheduleMeetingClassRespone;
import com.labreportapp.infrastructure.constant.Message;
import com.labreportapp.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeMeetingService {

    List<TeMeetingRespone> searchMeetingByIdClass(final TeFindMeetingRequest request);

    Integer countMeetingByClassId(String idClass);

    TeMeetingRespone searchMeetingByIdMeeting(final TeFindMeetingRequest request);

    TeMeetingRespone searchMeetingAndCheckAttendanceByIdMeeting(final TeFindMeetingRequest request);

    TeHomeWorkAndNoteMeetingRespone searchDetailMeetingTeamByIdMeIdTeam(final TeFindMeetingRequest request);

    TeHomeWorkAndNoteMeetingRespone updateDetailMeetingTeamByIdMeIdTeam(@Valid TeUpdateHomeWorkAndNoteInMeetingRequest request);

    List<TeScheduleMeetingClassRespone> searchScheduleToDayByIdTeacherAndMeetingDate(final TeFindScheduleMeetingClassRequest request);

    List<TeScheduleMeetingClassRespone> updateAddressMeeting(@RequestBody TeScheduleUpdateMeetingRequest request);

    List<TeMeetingCustomToAttendanceRespone> listMeetingAttendanceAllByIdClass(String idClass);

}

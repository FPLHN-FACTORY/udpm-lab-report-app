package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindScheduleMeetingClassRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindScheduleNowToTime;
import com.labreportapp.labreport.core.teacher.model.request.TeScheduleUpdateMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdateHomeWorkAndNoteInMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeClassResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeHomeWorkAndNoteMeetingRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomToAttendanceRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeScheduleMeetingClassRespone;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeMeetingService {

    List<TeMeetingCustomRespone> searchMeetingByIdClass(final TeFindMeetingRequest request);

    TeMeetingRespone searchMeetingByIdMeeting(final TeFindMeetingRequest request);

    TeMeetingRespone searchMeetingAndCheckAttendanceByIdMeeting(final TeFindMeetingRequest request);

    TeHomeWorkAndNoteMeetingRespone searchDetailMeetingTeamByIdMeIdTeam(final TeFindMeetingRequest request);

    TeHomeWorkAndNoteMeetingRespone updateDetailMeetingTeamByIdMeIdTeam(@Valid TeUpdateHomeWorkAndNoteInMeetingRequest request);

    List<TeScheduleMeetingClassRespone> searchScheduleToDayByIdTeacherAndMeetingDate(final TeFindScheduleMeetingClassRequest request);

    PageableObject<TeScheduleMeetingClassRespone> searchScheduleNowToByIdTeacher(final TeFindScheduleNowToTime request);

    List<TeScheduleMeetingClassRespone> updateAddressMeeting(@RequestBody TeScheduleUpdateMeetingRequest request);

    List<TeMeetingCustomToAttendanceRespone> listMeetingAttendanceAllByIdClass(String idClass);

}

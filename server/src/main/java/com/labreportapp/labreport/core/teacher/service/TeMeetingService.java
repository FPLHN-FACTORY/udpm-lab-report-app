package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindScheduleNowToTime;
import com.labreportapp.labreport.core.teacher.model.request.TeScheduleUpdateMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdateHomeWorkAndNoteInMeetingRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailMeetingTeamReportRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeExcelResponseMessage;
import com.labreportapp.labreport.core.teacher.model.response.TeHomeWorkAndNoteMeetingResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingCustomToAttendanceResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeScheduleMeetingClassResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeMeetingService {

    List<TeMeetingCustomResponse> searchMeetingByIdClass(final TeFindMeetingRequest request);

    TeDetailMeetingTeamReportRespone searchMeetingByIdMeeting(final TeFindMeetingRequest request);

    TeMeetingResponse searchMeetingAndCheckAttendanceByIdMeeting(final TeFindMeetingRequest request);

    TeHomeWorkAndNoteMeetingResponse searchDetailMeetingTeamByIdMeIdTeam(final TeFindMeetingRequest request);

    TeHomeWorkAndNoteMeetingResponse updateDetailMeetingTeamByIdMeIdTeam(@Valid TeUpdateHomeWorkAndNoteInMeetingRequest request);

    List<TeScheduleMeetingClassResponse> searchScheduleToDayByIdTeacherAndMeetingDate();

    PageableObject<TeScheduleMeetingClassResponse> searchScheduleNowToByIdTeacher(final TeFindScheduleNowToTime request);

    List<TeScheduleMeetingClassResponse> updateAddressMeeting(@RequestBody TeScheduleUpdateMeetingRequest request);

    List<TeMeetingCustomToAttendanceResponse> listMeetingAttendanceAllByIdClass(String idClass);

    ByteArrayOutputStream exportExcelMeeting(HttpServletResponse response, String idClass);

    TeExcelResponseMessage importExcelMeeting(MultipartFile file, String idClass);

}

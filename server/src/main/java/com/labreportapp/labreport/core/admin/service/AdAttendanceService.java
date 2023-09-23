package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.AdUpdateListAttendanceRequest;
import com.labreportapp.labreport.core.admin.model.response.AdAttendanceMeetingCustom;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface AdAttendanceService {

    List<AdAttendanceMeetingCustom> getAttendanceByIdMeeting(String idMeeting, String idClass);

    Boolean updateAttendance(@Valid AdUpdateListAttendanceRequest request);
}

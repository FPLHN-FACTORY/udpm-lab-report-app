package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdUpdateAttendanceRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateListAttendanceRequest;
import com.labreportapp.labreport.core.admin.model.response.AdAttendanceMeetingCustom;
import com.labreportapp.labreport.core.admin.model.response.AdAttendanceMeetingResponse;
import com.labreportapp.labreport.core.admin.model.response.AdExportExcelClassResponse;
import com.labreportapp.labreport.core.admin.repository.AdAttendanceRepository;
import com.labreportapp.labreport.core.admin.service.AdAttendanceService;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.entity.Attendance;
import com.labreportapp.labreport.infrastructure.constant.StatusAttendance;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class AdAttendanceServiceImpl implements AdAttendanceService {

    @Autowired
    private AdAttendanceRepository adAttendanceRepository;

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Override
    public List<AdAttendanceMeetingCustom> getAttendanceByIdMeeting(String idMeeting, String idClass) {
        List<AdAttendanceMeetingResponse> listAttendance = adAttendanceRepository.getAttendanceByIdMeetingAndIdClass(idMeeting, idClass);
        if (listAttendance.isEmpty()) {
            return null;
        }
        List<String> distinctStudentIds = listAttendance.stream()
                .map(AdAttendanceMeetingResponse::getStudentId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listSimple = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(distinctStudentIds);
        if (listSimple.isEmpty()) {
            return null;
        }
        List<AdAttendanceMeetingCustom> listCustom = new ArrayList<>();
        listAttendance.forEach(atten -> {
            listSimple.forEach(simple -> {
                if (atten.getStudentId().equals(simple.getId())) {
                    AdAttendanceMeetingCustom adAttendanceMeetingCustom = new AdAttendanceMeetingCustom();
                    adAttendanceMeetingCustom.setId(atten.getId());
                    adAttendanceMeetingCustom.setMeetingId(atten.getMeetingId());
                    adAttendanceMeetingCustom.setStatus(atten.getStatus());
                    adAttendanceMeetingCustom.setStudentId(atten.getStudentId());
                    adAttendanceMeetingCustom.setName(simple.getName());
                    adAttendanceMeetingCustom.setEmail(simple.getEmail());
                    listCustom.add(adAttendanceMeetingCustom);
                }
            });
        });
        return listCustom;
    }

    @Override
    public Boolean updateAttendance(@Valid AdUpdateListAttendanceRequest request) {
        try {
            List<AdUpdateAttendanceRequest> listAttendance = request.getListAttendance();
            List<String> listIdAttendance = listAttendance.stream()
                    .map(AdUpdateAttendanceRequest::getId)
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());
            List<Attendance> listAttendanceUpdate = adAttendanceRepository.findAllById(listIdAttendance);
            if (listAttendance.isEmpty()) {
                throw new RestApiException(Message.ATTENDANCE_NOT_EXISTS);
            }
            listAttendance.forEach(attendance -> {
                listAttendanceUpdate.forEach(attenUpdate -> {
                    if (attendance.getId().equals(attenUpdate.getId())) {
                        attenUpdate.setStatus(StatusAttendance.values()[attendance.getStatus()]);
                    }
                });
            });
            adAttendanceRepository.saveAll(listAttendanceUpdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}

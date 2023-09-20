package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdCreateMeetingRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateMeetingRequest;
import com.labreportapp.labreport.core.admin.model.response.AdExportExcelClassCustom;
import com.labreportapp.labreport.core.admin.model.response.AdExportExcelClassResponse;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingCustom;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingResponse;
import com.labreportapp.labreport.core.admin.repository.AdMeetingRepository;
import com.labreportapp.labreport.core.admin.service.AdMeetingService;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.entity.Meeting;
import com.labreportapp.labreport.infrastructure.constant.MeetingPeriod;
import com.labreportapp.labreport.infrastructure.constant.TypeMeeting;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class AdMeetingServiceImpl implements AdMeetingService {

    @Autowired
    private AdMeetingRepository adMeetingRepository;

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Override
    public List<AdMeetingCustom> getAllMeetingByIdClass(String idClass) {
        List<AdMeetingResponse> listMeeting = adMeetingRepository.getAllMeetingByIdClass(idClass);
        List<String> distinctTeacherIds = listMeeting.stream()
                .map(AdMeetingResponse::getTeacherId)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listSimpleResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(distinctTeacherIds);
        List<AdMeetingCustom> listCustom = new ArrayList<>();
        listMeeting.forEach(res -> {
            listSimpleResponse.forEach(simple -> {
                if (res.getTeacherId().equals(simple.getId())) {
                    AdMeetingCustom adMeetingCustom = new AdMeetingCustom();
                    adMeetingCustom.setId(res.getId());
                    adMeetingCustom.setMeetingDate(res.getMeetingDate());
                    adMeetingCustom.setMeetingPeriod(res.getMeetingPeriod());
                    adMeetingCustom.setTypeMeeting(res.getTypeMeeting());
                    adMeetingCustom.setTeacherId(res.getTeacherId());
                    adMeetingCustom.setUserNameTeacher(simple.getUserName());
                    adMeetingCustom.setAddress(res.getAddress());
                    adMeetingCustom.setDescriptions(res.getDescriptions());
                    adMeetingCustom.setName(res.getName());
                    listCustom.add(adMeetingCustom);
                }
            });
        });
        return listCustom;
    }

    @Override
    public Meeting create(@Valid AdCreateMeetingRequest request) {
        Meeting meeting = new Meeting();
        meeting.setName(request.getName());
        meeting.setMeetingDate(request.getMeetingDate());
        meeting.setTypeMeeting(TypeMeeting.values()[request.getTypeMeeting()]);
        meeting.setMeetingPeriod(MeetingPeriod.values()[request.getMeetingPeriod()]);
        meeting.setAddress(request.getAddress());
        meeting.setClassId(request.getClassId());
        meeting.setDescriptions(request.getDescriptions());
        return adMeetingRepository.save(meeting);
    }

    @Override
    public Meeting update(@Valid AdUpdateMeetingRequest request) {
        Optional<Meeting> meetingFind = adMeetingRepository.findById(request.getId());
        if (!meetingFind.isPresent()) {
            throw new RestApiException(Message.MEETING_NOT_EXISTS);
        }
        meetingFind.get().setName(request.getName());
        meetingFind.get().setMeetingDate(request.getMeetingDate());
        meetingFind.get().setTypeMeeting(TypeMeeting.values()[request.getTypeMeeting()]);
        meetingFind.get().setMeetingPeriod(MeetingPeriod.values()[request.getMeetingPeriod()]);
        meetingFind.get().setAddress(request.getAddress());
        meetingFind.get().setDescriptions(request.getDescriptions());
        return adMeetingRepository.save(meetingFind.get());
    }

    @Override
    public String delete(String id) {
        List<String> listAttendance = adMeetingRepository.findAttendanceByIdMeeting(id);
        List<String> listNote = adMeetingRepository.findNoteByIdMeeting(id);
        List<String> listHomeWork = adMeetingRepository.findHomeWorkByIdMeeting(id);

        if (listAttendance.isEmpty() && listNote.isEmpty() && listHomeWork.isEmpty()) {
            Optional<Meeting> meetingFind = adMeetingRepository.findById(id);
            if (!meetingFind.isPresent()) {
                throw new RestApiException(Message.MEETING_NOT_EXISTS);
            }
            adMeetingRepository.deleteById(id);
            return id;
        } else {
            throw new RestApiException(Message.DANG_CO_DU_LIEU_LIEN_QUAN_KHONG_THE_XOA_BUOI_HOC);
        }
    }
}

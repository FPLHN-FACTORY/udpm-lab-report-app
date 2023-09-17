package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdCreateMeetingRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateMeetingRequest;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingResponse;
import com.labreportapp.labreport.core.admin.repository.AdMeetingRepository;
import com.labreportapp.labreport.core.admin.service.AdMeetingService;
import com.labreportapp.labreport.entity.Meeting;
import com.labreportapp.labreport.infrastructure.constant.MeetingPeriod;
import com.labreportapp.labreport.infrastructure.constant.TypeMeeting;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class AdMeetingServiceImpl implements AdMeetingService {

    @Autowired
    private AdMeetingRepository adMeetingRepository;

    @Override
    public List<AdMeetingResponse> getAllMeetingByIdClass(String idClass) {
        return adMeetingRepository.getAllMeetingByIdClass(idClass);
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

package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdChangeTeacherRequest;
import com.labreportapp.labreport.core.admin.model.request.AdCreateMeetingAutoRequest;
import com.labreportapp.labreport.core.admin.model.request.AdCreateMeetingRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateMeetingRequest;
import com.labreportapp.labreport.core.admin.model.response.AdDetailMeetingResponse;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingCustom;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingResponse;
import com.labreportapp.labreport.core.admin.repository.AdActivityRepository;
import com.labreportapp.labreport.core.admin.repository.AdClassRepository;
import com.labreportapp.labreport.core.admin.repository.AdMeetingRepository;
import com.labreportapp.labreport.core.admin.service.AdMeetingService;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.entity.Activity;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.Meeting;
import com.labreportapp.labreport.infrastructure.constant.MeetingPeriod;
import com.labreportapp.labreport.infrastructure.constant.StatusMeeting;
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

    @Autowired
    private AdClassRepository adClassRepository;

    @Autowired
    private AdActivityRepository adActivityRepository;

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
                    adMeetingCustom.setSoDiemDanh(res.getSoDiemDanh());
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
    public AdMeetingCustom create(@Valid AdCreateMeetingRequest request) {
        Optional<Class> classFind = adClassRepository.findById(request.getClassId());
        if (!classFind.isPresent()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        Meeting meeting = new Meeting();
        meeting.setName("Buổi học");
        meeting.setMeetingDate(request.getMeetingDate());
        meeting.setTypeMeeting(TypeMeeting.values()[request.getTypeMeeting()]);
        meeting.setMeetingPeriod(MeetingPeriod.values()[request.getMeetingPeriod()]);
        meeting.setAddress(request.getAddress());
        meeting.setClassId(request.getClassId());
        meeting.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting.setDescriptions(request.getDescriptions());
        SimpleResponse simple = null;
        if (!request.getTeacherId().equals("") && request.getTeacherId() != null) {
            meeting.setTeacherId(request.getTeacherId());
            simple = convertRequestCallApiIdentity.handleCallApiGetUserById(request.getTeacherId());
        }
        Meeting meetingNew = adMeetingRepository.save(meeting);
        adMeetingRepository.updateNameMeeting(request.getClassId());
        AdMeetingCustom adMeetingCustom = new AdMeetingCustom();
        adMeetingCustom.setId(meetingNew.getId());
        adMeetingCustom.setMeetingDate(meetingNew.getMeetingDate());
        adMeetingCustom.setMeetingPeriod(request.getMeetingPeriod());
        adMeetingCustom.setTypeMeeting(meetingNew.getTypeMeeting() == TypeMeeting.ONLINE ? 0 : 1);
        adMeetingCustom.setDescriptions(meetingNew.getDescriptions());
        adMeetingCustom.setName(meetingNew.getName());
        adMeetingCustom.setSoDiemDanh(null);
        if (simple != null) {
            adMeetingCustom.setUserNameTeacher(simple.getUserName());
            adMeetingCustom.setTeacherId(request.getTeacherId());
        }
        adMeetingCustom.setAddress(meetingNew.getAddress());
        return adMeetingCustom;
    }

    @Override
    public AdMeetingCustom update(@Valid AdUpdateMeetingRequest request) {
        Optional<Meeting> meetingFind = adMeetingRepository.findById(request.getId());
        if (!meetingFind.isPresent()) {
            throw new RestApiException(Message.MEETING_NOT_EXISTS);
        }
        meetingFind.get().setMeetingDate(request.getMeetingDate());
        meetingFind.get().setTypeMeeting(TypeMeeting.values()[request.getTypeMeeting()]);
        meetingFind.get().setMeetingPeriod(MeetingPeriod.values()[request.getMeetingPeriod()]);
        meetingFind.get().setAddress(request.getAddress());
        meetingFind.get().setDescriptions(request.getDescriptions());
        SimpleResponse simple = null;
        if (!request.getTeacherId().equals("") && request.getTeacherId() != null) {
            meetingFind.get().setTeacherId(request.getTeacherId());
            simple = convertRequestCallApiIdentity.handleCallApiGetUserById(request.getTeacherId());
        }
        Meeting meetingNew = adMeetingRepository.save(meetingFind.get());
        adMeetingRepository.updateNameMeeting(meetingFind.get().getClassId());
        AdMeetingCustom adMeetingCustom = new AdMeetingCustom();
        adMeetingCustom.setId(meetingNew.getId());
        adMeetingCustom.setMeetingDate(meetingNew.getMeetingDate());
        adMeetingCustom.setMeetingPeriod(request.getMeetingPeriod());
        adMeetingCustom.setTypeMeeting(meetingNew.getTypeMeeting() == TypeMeeting.ONLINE ? 0 : 1);
        adMeetingCustom.setDescriptions(meetingNew.getDescriptions());
        adMeetingCustom.setName(meetingNew.getName());
        adMeetingCustom.setSoDiemDanh(null);
        if (simple != null) {
            adMeetingCustom.setUserNameTeacher(simple.getUserName());
            adMeetingCustom.setTeacherId(request.getTeacherId());
        }
        adMeetingCustom.setAddress(meetingNew.getAddress());
        return adMeetingCustom;
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
            adMeetingRepository.updateNameMeeting(meetingFind.get().getClassId());
            return id;
        } else {
            throw new RestApiException(Message.DANG_CO_DU_LIEU_LIEN_QUAN_KHONG_THE_XOA_BUOI_HOC);
        }
    }

    @Override
    public Boolean changeTeacher(@Valid AdChangeTeacherRequest request) {
        List<Meeting> listMeeting = adMeetingRepository.findAllById(request.getListMeeting());
        if (listMeeting.isEmpty()) {
            throw new RestApiException(Message.MEETING_NOT_EXISTS);
        }
        for (Meeting meeting : listMeeting) {
            meeting.setTeacherId(request.getTeacherId());
        }
        adMeetingRepository.saveAll(listMeeting);
        return null;
    }

    @Override
    public Boolean createMeetingAuto(@Valid AdCreateMeetingAutoRequest request) {
        try {
            Optional<Class> classFind = adClassRepository.findById(request.getClassId());
            if (!classFind.isPresent()) {
                throw new RestApiException(Message.CLASS_NOT_EXISTS);
            }
            Optional<Activity> activityFind = adActivityRepository.findById(classFind.get().getActivityId());
            if (!activityFind.isPresent()) {
                throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
            }
            List<Meeting> listMeeting = new ArrayList<>();
            Long meetingDateInMillis = request.getMeetingDate();
            for (int i = 0; i < request.getNumberMeeting(); i++) {
                Meeting meeting = new Meeting();
                meeting.setStatusMeeting(StatusMeeting.BUOI_HOC);
                meeting.setMeetingPeriod(MeetingPeriod.values()[request.getMeetingPeriod()]);
                meeting.setMeetingDate(meetingDateInMillis);
                meeting.setTypeMeeting(TypeMeeting.values()[request.getTypeMeeting()]);
                if (request.getTeacherId() != null && !request.getTeacherId().equals("")) {
                    meeting.setTeacherId(request.getTeacherId());
                }
                meeting.setClassId(request.getClassId());
                meeting.setName("Buổi học");
                listMeeting.add(meeting);
                meetingDateInMillis += 7 * 24 * 60 * 60 * 1000;
            }
            adMeetingRepository.saveAll(listMeeting);
            adMeetingRepository.updateNameMeeting(request.getClassId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public AdDetailMeetingResponse detailMeeting(String idMeeting) {
        Meeting meeting = adMeetingRepository.findById(idMeeting).get();
        if (meeting == null) {
            throw new RestApiException(Message.MEETING_NOT_EXISTS);
        }
        Class classFind = adClassRepository.findById(meeting.getClassId()).get();
        AdDetailMeetingResponse adDetailMeetingResponse = new AdDetailMeetingResponse();
        adDetailMeetingResponse.setId(meeting.getId());
        adDetailMeetingResponse.setName(meeting.getName());
        adDetailMeetingResponse.setMeetingDate(meeting.getMeetingDate());
        adDetailMeetingResponse.setStatusMeeting(meeting.getStatusMeeting());
        adDetailMeetingResponse.setMeetingPeriod(meeting.getMeetingPeriod());
        adDetailMeetingResponse.setTypeMeeting(meeting.getTypeMeeting());
        adDetailMeetingResponse.setAddress(meeting.getAddress());
        adDetailMeetingResponse.setDescriptions(meeting.getDescriptions());
        adDetailMeetingResponse.setClassId(meeting.getClassId());
        adDetailMeetingResponse.setCodeClass(classFind.getCode());
        if (meeting.getTeacherId() != null) {
            SimpleResponse simpleResponse = convertRequestCallApiIdentity.handleCallApiGetUserById(meeting.getTeacherId());
            adDetailMeetingResponse.setTeacherId(simpleResponse.getId());
            adDetailMeetingResponse.setUserNameTeacher(simpleResponse.getUserName());
        }
        return adDetailMeetingResponse;
    }
}

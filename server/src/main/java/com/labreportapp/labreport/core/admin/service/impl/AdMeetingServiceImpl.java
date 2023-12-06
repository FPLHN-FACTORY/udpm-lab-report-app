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
import com.labreportapp.labreport.core.admin.repository.AdMeetingPeriodRepository;
import com.labreportapp.labreport.core.admin.repository.AdMeetingRepository;
import com.labreportapp.labreport.core.admin.service.AdMeetingService;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.entity.Activity;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.Meeting;
import com.labreportapp.labreport.entity.MeetingPeriod;
import com.labreportapp.labreport.infrastructure.constant.StatusMeeting;
import com.labreportapp.labreport.infrastructure.constant.TypeMeeting;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.labreport.util.CompareUtil;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;
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
    private AdMeetingPeriodRepository adMeetingPeriodRepository;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private AdClassRepository adClassRepository;

    @Autowired
    private AdActivityRepository adActivityRepository;

    @Autowired
    private LoggerUtil loggerUtil;

    @Override
    public List<AdMeetingCustom> getAllMeetingByIdClass(String idClass) {
        List<AdMeetingResponse> listMeeting = adMeetingRepository.getAllMeetingByIdClass(idClass);
        List<String> distinctTeacherIds = listMeeting.stream()
                .map(AdMeetingResponse::getTeacherId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listSimpleResponse = callApiIdentity.handleCallApiGetListUserByListId(distinctTeacherIds);
        List<AdMeetingCustom> listCustom = new ArrayList<>();
        listMeeting.forEach(res -> {
            AdMeetingCustom adMeetingCustom = new AdMeetingCustom();
            adMeetingCustom.setId(res.getId());
            adMeetingCustom.setMeetingDate(res.getMeetingDate());
            adMeetingCustom.setMeetingPeriodId(res.getMeetingPeriodId());
            adMeetingCustom.setNameMeetingPeriod(res.getNameMeetingPeriod());
            adMeetingCustom.setStartHour(res.getStartHour());
            adMeetingCustom.setStartMinute(res.getStartMinute());
            adMeetingCustom.setEndHour(res.getEndHour());
            adMeetingCustom.setEndMinute(res.getEndMinute());
            adMeetingCustom.setTypeMeeting(res.getTypeMeeting());
            adMeetingCustom.setSoDiemDanh(res.getSoDiemDanh());
            adMeetingCustom.setAddress(res.getAddress());
            adMeetingCustom.setDescriptions(res.getDescriptions());
            adMeetingCustom.setName(res.getName());
            adMeetingCustom.setTeacherId(res.getTeacherId());
            for (SimpleResponse simpleResponse : listSimpleResponse) {
                if (adMeetingCustom.getTeacherId() != null) {
                    if (adMeetingCustom.getTeacherId().equals(simpleResponse.getId())) {
                        adMeetingCustom.setUserNameTeacher(simpleResponse.getUserName());
                        break;
                    }
                }
            }
            listCustom.add(adMeetingCustom);
        });
        return listCustom;
    }

    @Override
    public AdMeetingCustom create(@Valid AdCreateMeetingRequest request) {
        Optional<Class> classFind = adClassRepository.findById(request.getClassId());
        if (classFind.isEmpty()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        Optional<Activity> activityFind = adActivityRepository.findById(classFind.get().getActivityId());
        if (activityFind.isEmpty()) {
            throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
        }
        Meeting meeting = new Meeting();
        meeting.setName("Buổi học");
        meeting.setMeetingDate(request.getMeetingDate());
        meeting.setTypeMeeting(TypeMeeting.values()[request.getTypeMeeting()]);
        MeetingPeriod meetingPeriodFind = null;
        if (request.getMeetingPeriod() != null) {
            meetingPeriodFind = adMeetingPeriodRepository.findById(request.getMeetingPeriod()).get();
            meeting.setMeetingPeriod(meetingPeriodFind.getId());
        }
        meeting.setAddress(request.getAddress());
        meeting.setClassId(request.getClassId());
        meeting.setStatusMeeting(StatusMeeting.BUOI_HOC);
        meeting.setDescriptions(activityFind.get().getDescriptions());
        SimpleResponse simple = null;
        if (!request.getTeacherId().equals("") && request.getTeacherId() != null) {
            meeting.setTeacherId(request.getTeacherId());
            simple = callApiIdentity.handleCallApiGetUserById(request.getTeacherId());
        }
        Meeting meetingNew = adMeetingRepository.save(meeting);
        Optional<Class> classOptional = adClassRepository.findById(meetingNew.getClassId());
        if (!classOptional.isPresent()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        StringBuilder stringBuilder = new StringBuilder();
        String nameSemester = loggerUtil.getNameSemesterByIdClass(classOptional.get().getId());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        stringBuilder.append("Đã tạo buổi học với thời gian buổi học là: " + sdf.format(request.getMeetingDate()));
        stringBuilder.append(", với ca học là: " + meetingPeriodFind.getName());
        stringBuilder.append(", với hình thức của buổi học là: " + (TypeMeeting.values()[request.getTypeMeeting()] == TypeMeeting.ONLINE ? "Online" : "Offline"));
        loggerUtil.sendLogStreamClass(stringBuilder.toString(), classOptional.get().getCode(), nameSemester);
        adMeetingRepository.updateNameMeeting(request.getClassId());
        AdMeetingCustom adMeetingCustom = new AdMeetingCustom();
        adMeetingCustom.setId(meetingNew.getId());
        adMeetingCustom.setMeetingDate(meetingNew.getMeetingDate());
        if (meetingPeriodFind != null) {
            adMeetingCustom.setMeetingPeriodId(meetingPeriodFind.getId());
            adMeetingCustom.setNameMeetingPeriod(meetingPeriodFind.getName());
            adMeetingCustom.setStartHour(meetingPeriodFind.getStartHour());
            adMeetingCustom.setStartMinute(meetingPeriodFind.getStartMinute());
            adMeetingCustom.setEndHour(meetingPeriodFind.getEndHour());
            adMeetingCustom.setEndMinute(meetingPeriodFind.getEndMinute());
        }
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
    @Transactional
    public AdMeetingCustom update(@Valid AdUpdateMeetingRequest request) {
        Optional<Meeting> meetingFind = adMeetingRepository.findById(request.getId());
        if (meetingFind.isEmpty()) {
            throw new RestApiException(Message.MEETING_NOT_EXISTS);
        }
        Optional<Class> classOptional = adClassRepository.findById(meetingFind.get().getClassId());
        if (classOptional.isEmpty()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        StringBuilder stringBuilder = new StringBuilder();
        String nameSemester = loggerUtil.getNameSemesterByIdClass(classOptional.get().getId());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String meetingDateOld = sdf.format(meetingFind.get().getMeetingDate());
        String meetingDateNew = sdf.format(request.getMeetingDate());
        String messageMeetingDate = CompareUtil.compareAndConvertMessage("ngày học của buổi học " + meetingFind.get().getName(), meetingDateOld, meetingDateNew, "");
        stringBuilder.append(messageMeetingDate);

        if (TypeMeeting.values()[request.getTypeMeeting()] != meetingFind.get().getTypeMeeting()) {
            if (request.getTypeMeeting() == 0) {
                stringBuilder.append(". Đã cập nhật hình thức học của buổi học " + meetingFind.get().getName() + " từ Offline thành Online");
            } else {
                stringBuilder.append(". Đã cập nhật hình thức học của buổi học " + meetingFind.get().getName() + " từ Online thành Offline");
            }
        }
        MeetingPeriod meetingPeriodNew = adMeetingPeriodRepository.findById(request.getMeetingPeriod()).get();
        MeetingPeriod meetingPeriodOld = adMeetingPeriodRepository.findById(meetingFind.get().getMeetingPeriod()).get();
        String messageMeetingPeriod = CompareUtil.compareAndConvertMessage("ca học của buổi học " + meetingFind.get().getName(), meetingPeriodOld.getName(), meetingPeriodNew.getName(), "");
        stringBuilder.append(messageMeetingPeriod);

        String messageAddressNew = request.getAddress();
        String messageAddressOld = meetingFind.get().getAddress();
        String messageAddress = CompareUtil.compareAndConvertMessage("địa chỉ của buổi học " + meetingFind.get().getName(), messageAddressOld, messageAddressNew, "");
        stringBuilder.append(messageAddress);
        loggerUtil.sendLogStreamClass(stringBuilder.toString(), classOptional.get().getCode(), nameSemester);

        meetingFind.get().setMeetingDate(request.getMeetingDate());
        meetingFind.get().setTypeMeeting(TypeMeeting.values()[request.getTypeMeeting()]);

        meetingFind.get().setMeetingPeriod(meetingPeriodNew.getId());
        meetingFind.get().setAddress(request.getAddress());
        meetingFind.get().setDescriptions(request.getDescriptions());
        meetingFind.get().setStatusMeeting(meetingFind.get().getStatusMeeting());
        SimpleResponse simple = null;
        if (!request.getTeacherId().equals("") && request.getTeacherId() != null) {
            meetingFind.get().setTeacherId(request.getTeacherId());
            simple = callApiIdentity.handleCallApiGetUserById(request.getTeacherId());
        }
        Meeting meetingNew = adMeetingRepository.save(meetingFind.get());
        adMeetingRepository.updateNameMeeting(meetingFind.get().getClassId());

        AdMeetingCustom adMeetingCustom = new AdMeetingCustom();
        adMeetingCustom.setId(meetingNew.getId());
        adMeetingCustom.setMeetingDate(meetingNew.getMeetingDate());
        adMeetingCustom.setMeetingPeriodId(request.getMeetingPeriod());
        adMeetingCustom.setNameMeetingPeriod(meetingPeriodNew.getName());
        adMeetingCustom.setStartHour(meetingPeriodNew.getStartHour());
        adMeetingCustom.setStartMinute(meetingPeriodNew.getStartMinute());
        adMeetingCustom.setEndHour(meetingPeriodNew.getEndHour());
        adMeetingCustom.setEndMinute(meetingPeriodNew.getEndMinute());
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
    @Transactional
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
            Optional<Class> classOptional = adClassRepository.findById(meetingFind.get().getClassId());
            if (!classOptional.isPresent()) {
                throw new RestApiException(Message.CLASS_NOT_EXISTS);
            }
            StringBuilder stringBuilder = new StringBuilder();
            String nameSemester = loggerUtil.getNameSemesterByIdClass(classOptional.get().getId());
            stringBuilder.append("Đã xóa buổi học " + meetingFind.get().getName() + ".");
            loggerUtil.sendLogStreamClass(stringBuilder.toString(), classOptional.get().getCode(), nameSemester);
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
        SimpleResponse simpleResponse = callApiIdentity.handleCallApiGetUserById(request.getTeacherId());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Đã cập nhật giảng viên của những buổi học: ");
        for (Meeting meeting : listMeeting) {
            meeting.setTeacherId(request.getTeacherId());
            stringBuilder.append(meeting.getName()).append(", ");
        }
        stringBuilder.append(" thành: ").append(simpleResponse.getName()).append(" - ").append(simpleResponse.getUserName());
        Optional<Class> classOptional = adClassRepository.findById(listMeeting.get(0).getClassId());
        if (!classOptional.isPresent()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        String nameSemester = loggerUtil.getNameSemesterByIdClass(classOptional.get().getId());
        loggerUtil.sendLogStreamClass(stringBuilder.toString(), classOptional.get().getCode(), nameSemester);
        adMeetingRepository.saveAll(listMeeting);
        return null;
    }

    @Override
    public Boolean createMeetingAuto(@Valid AdCreateMeetingAutoRequest request) {
        try {
            Optional<Class> classFind = adClassRepository.findById(request.getClassId());
            if (classFind.isEmpty()) {
                throw new RestApiException(Message.CLASS_NOT_EXISTS);
            }
            Optional<Activity> activityFind = adActivityRepository.findById(classFind.get().getActivityId());
            if (activityFind.isEmpty()) {
                throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
            }
            List<Meeting> listMeeting = new ArrayList<>();
            Long meetingDateInMillis = DateUtils.truncate(new Date(request.getMeetingDate()), Calendar.DATE).getTime();
            MeetingPeriod meetingPeriodFind = null;
            if (request.getMeetingPeriod() != null) {
                meetingPeriodFind = adMeetingPeriodRepository.findById(request.getMeetingPeriod()).get();
            }
            for (int i = 0; i < request.getNumberMeeting(); i++) {
                Meeting meeting = new Meeting();
                meeting.setStatusMeeting(StatusMeeting.BUOI_HOC);
                meeting.setMeetingPeriod(meetingPeriodFind.getId());
                meeting.setMeetingDate(meetingDateInMillis);
                meeting.setTypeMeeting(TypeMeeting.values()[request.getTypeMeeting()]);
                if (request.getTeacherId() != null && !request.getTeacherId().equals("")) {
                    meeting.setTeacherId(request.getTeacherId());
                }
                meeting.setClassId(request.getClassId());
                meeting.setDescriptions(activityFind.get().getDescriptions());
                meeting.setName("Buổi học");
                listMeeting.add(meeting);
                meetingDateInMillis += request.getNumberDay() * 24 * 60 * 60 * 1000;
            }
            List<Meeting> listMeetingNew = adMeetingRepository.saveAll(listMeeting);
            StringBuilder stringBuilder = new StringBuilder();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            listMeetingNew.forEach(meeting -> {
                stringBuilder.append("Đã tạo buổi học với thời gian buổi học là: ").append(sdf.format(meeting.getMeetingDate()));
                stringBuilder.append(", với hình thức của buổi học là: ").append(meeting.getTypeMeeting() == TypeMeeting.ONLINE ? "Online" : "Offline").append(". ");
            });
            Optional<Class> classOptional = adClassRepository.findById(listMeeting.get(0).getClassId());
            if (classOptional.isEmpty()) {
                throw new RestApiException(Message.CLASS_NOT_EXISTS);
            }
            String nameSemester = loggerUtil.getNameSemesterByIdClass(classOptional.get().getId());
            loggerUtil.sendLogStreamClass(stringBuilder.toString(), classOptional.get().getCode(), nameSemester);
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
        MeetingPeriod meetingPeriodFind = null;
        if (meeting.getMeetingPeriod() != null) {
            meetingPeriodFind = adMeetingPeriodRepository.findById(meeting.getMeetingPeriod()).get();
            adDetailMeetingResponse.setNameMeetingPeriod(meetingPeriodFind.getName());
            adDetailMeetingResponse.setStartHour(meetingPeriodFind.getStartHour());
            adDetailMeetingResponse.setStartMinute(meetingPeriodFind.getStartMinute());
            adDetailMeetingResponse.setEndHour(meetingPeriodFind.getEndHour());
            adDetailMeetingResponse.setEndMinute(meetingPeriodFind.getEndMinute());
        }
        adDetailMeetingResponse.setMeetingPeriodId(meeting.getMeetingPeriod());
        adDetailMeetingResponse.setTypeMeeting(meeting.getTypeMeeting());
        adDetailMeetingResponse.setAddress(meeting.getAddress());
        adDetailMeetingResponse.setDescriptions(meeting.getDescriptions());
        adDetailMeetingResponse.setClassId(meeting.getClassId());
        adDetailMeetingResponse.setCodeClass(classFind.getCode());
        if (meeting.getTeacherId() != null) {
            SimpleResponse simpleResponse = callApiIdentity.handleCallApiGetUserById(meeting.getTeacherId());
            if (Objects.nonNull(simpleResponse)) {
                adDetailMeetingResponse.setTeacherId(simpleResponse.getId());
                adDetailMeetingResponse.setUserNameTeacher(simpleResponse.getUserName() + " - " + simpleResponse.getName());
            }
        }
        return adDetailMeetingResponse;
    }
}

package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdFindClassRequest;
import com.labreportapp.labreport.core.admin.model.request.AdReasonsNoApproveRequest;
import com.labreportapp.labreport.core.admin.model.response.AdClassHaveMeetingRequestResponse;
import com.labreportapp.labreport.core.admin.model.response.AdListClassCustomResponse;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingRequestCustom;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingRequestResponse;
import com.labreportapp.labreport.core.admin.repository.AdClassRepository;
import com.labreportapp.labreport.core.admin.repository.AdMeetingRepository;
import com.labreportapp.labreport.core.admin.repository.AdMeetingRequestRepository;
import com.labreportapp.labreport.core.admin.service.AdMeetingRequestService;
import com.labreportapp.labreport.core.common.base.LoggerResponse;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.Meeting;
import com.labreportapp.labreport.entity.MeetingRequest;
import com.labreportapp.labreport.infrastructure.constant.StatusMeeting;
import com.labreportapp.labreport.infrastructure.constant.StatusMeetingRequest;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.labreport.util.SemesterHelper;
import com.labreportapp.portalprojects.infrastructure.configemail.EmailSender;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author todo thangncph26123
 */
@Service
@Validated
public class AdMeetingRequestServiceImpl implements AdMeetingRequestService {

    @Autowired
    private AdMeetingRequestRepository adMeetingRequestRepository;

    @Autowired
    private SemesterHelper semesterHelper;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private AdMeetingRepository adMeetingRepository;

    @Autowired
    private AdClassRepository adClassRepository;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private EmailSender emailSender;

    @Override
    public PageableObject<AdListClassCustomResponse> searchClassHaveMeetingRequest(AdFindClassRequest adFindClass) {
        String idSemesterCurrent = semesterHelper.getSemesterCurrent();
        if (adFindClass.getIdSemester() == null) {
            if (idSemesterCurrent != null) {
                adFindClass.setIdSemester(idSemesterCurrent);
                adFindClass.setIdActivity("");
            } else {
                adFindClass.setIdSemester("");
            }
        } else if (adFindClass.getIdSemester().equalsIgnoreCase("")) {
            if (idSemesterCurrent != null) {
                adFindClass.setIdSemester(idSemesterCurrent);
                adFindClass.setIdActivity("");
            } else {
                adFindClass.setIdSemester("");
            }
        }
        Pageable pageable = PageRequest.of(adFindClass.getPage() - 1, adFindClass.getSize());
        PageableObject<AdListClassCustomResponse> pageableObject = new PageableObject<>();
        Page<AdClassHaveMeetingRequestResponse> pageList = adMeetingRequestRepository.findClassHaveMeetingRequest(adFindClass, pageable);
        List<AdClassHaveMeetingRequestResponse> listResponse = pageList.getContent();
        List<String> idList = listResponse.stream()
                .map(AdClassHaveMeetingRequestResponse::getTeacherId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> response = callApiIdentity.handleCallApiGetListUserByListId(idList);
        List<AdListClassCustomResponse> listClassCustomResponses = new ArrayList<>();
        for (AdClassHaveMeetingRequestResponse adClassResponse : listResponse) {
            AdListClassCustomResponse adListClassCustomResponse = new AdListClassCustomResponse();
            adListClassCustomResponse.setId(adClassResponse.getId());
            adListClassCustomResponse.setClassSize(adClassResponse.getClassSize());
            adListClassCustomResponse.setNameClassPeriod(adClassResponse.getNameClassPeriod());
            adListClassCustomResponse.setStartHour(adClassResponse.getStartHour());
            adListClassCustomResponse.setStartMinute(adClassResponse.getStartMinute());
            adListClassCustomResponse.setEndHour(adClassResponse.getEndHour());
            adListClassCustomResponse.setEndMinute(adClassResponse.getEndMinute());
            adListClassCustomResponse.setStartTime(adClassResponse.getStartTime());
            adListClassCustomResponse.setCode(adClassResponse.getCode());
            adListClassCustomResponse.setTeacherId(adClassResponse.getTeacherId());
            adListClassCustomResponse.setStt(adClassResponse.getStt());
            adListClassCustomResponse.setNameLevel(adClassResponse.getNameLevel());
            adListClassCustomResponse.setNumberMeetingRequest(adClassResponse.getNumberMeetingRequest());
            adListClassCustomResponse.setActivityName(adClassResponse.getActivityName());
            adListClassCustomResponse.setStatusTeacherEdit(adClassResponse.getStatusTeacherEdit());
            for (SimpleResponse simpleResponse : response) {
                if (adClassResponse.getTeacherId() != null) {
                    if (adClassResponse.getTeacherId().equals(simpleResponse.getId())) {
                        adListClassCustomResponse.setUserNameTeacher(simpleResponse.getUserName());
                        break;
                    }
                }
            }
            listClassCustomResponses.add(adListClassCustomResponse);
        }
        pageableObject.setData(listClassCustomResponses);
        pageableObject.setCurrentPage(pageList.getNumber());
        pageableObject.setTotalPages(pageList.getTotalPages());
        return pageableObject;
    }

    @Override
    public Integer countClassHaveMeetingRequest(AdFindClassRequest adFindClass) {
        String idSemesterCurrent = semesterHelper.getSemesterCurrent();
        if (idSemesterCurrent == null) {
            return 0;
        }
        if (adFindClass.getIdSemester() == null) {
            if (idSemesterCurrent != null) {
                adFindClass.setIdSemester(idSemesterCurrent);
                adFindClass.setIdActivity("");
            } else {
                adFindClass.setIdSemester("");
            }
        } else if (adFindClass.getIdSemester().equalsIgnoreCase("")) {
            if (idSemesterCurrent != null) {
                adFindClass.setIdSemester(idSemesterCurrent);
                adFindClass.setIdActivity("");
            } else {
                adFindClass.setIdSemester("");
            }
        }
        return adMeetingRequestRepository.countClassHaveMeetingRequest(adFindClass);
    }

    @Override
    public List<AdMeetingRequestCustom> getAllMeetingRequestByIdClass(String idClass) {
        List<AdMeetingRequestResponse> listMeeting = adMeetingRequestRepository.getAllMeetingRequestByIdClass(idClass);
        List<String> distinctTeacherIds = listMeeting.stream()
                .map(AdMeetingRequestResponse::getTeacherId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listSimpleResponse = callApiIdentity.handleCallApiGetListUserByListId(distinctTeacherIds);
        List<AdMeetingRequestCustom> listCustom = new ArrayList<>();
        listMeeting.forEach(res -> {
            AdMeetingRequestCustom adMeetingCustom = new AdMeetingRequestCustom();
            adMeetingCustom.setId(res.getId());
            adMeetingCustom.setMeetingDate(res.getMeetingDate());
            adMeetingCustom.setMeetingPeriodId(res.getMeetingPeriodId());
            adMeetingCustom.setNameMeetingPeriod(res.getNameMeetingPeriod());
            adMeetingCustom.setStartHour(res.getStartHour());
            adMeetingCustom.setStartMinute(res.getStartMinute());
            adMeetingCustom.setEndHour(res.getEndHour());
            adMeetingCustom.setEndMinute(res.getEndMinute());
            adMeetingCustom.setTypeMeeting(res.getTypeMeeting());
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
    public Boolean approveMeetingRequest(List<String> listIdMeetingRequest) {
        return handleApproveMeetingRequest(listIdMeetingRequest);
    }

    @Override
    public Boolean approveClass(List<String> listIdClass) {
        List<Class> listClass = adClassRepository.findAllById(listIdClass);
        if (listClass.isEmpty()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        listIdClass.forEach(xx -> {
            List<String> listId = adMeetingRequestRepository.getListIdMeetingRequestByIdClass(xx);
            approveMeetingRequest(listId);
        });
        listClass.forEach(xx -> {
            xx.setReasons(null);
        });
        adClassRepository.saveAll(listClass);
        return true;
    }

    @Override
    public Boolean noApproveMeetingRequest(List<String> listIdMeetingRequest) {
        return handleNoApproveMeetingRequest(listIdMeetingRequest);
    }

    @Override
    public Boolean noApproveClass(List<String> listIdClass) {
        List<Class> listClass = adClassRepository.findAllById(listIdClass);
        if (listClass.isEmpty()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        listIdClass.forEach(xx -> {
            List<String> listId = adMeetingRequestRepository.getListIdMeetingRequestByIdClass(xx);
            noApproveMeetingRequest(listId);
        });
        return true;
    }

    @Override
    public Boolean addReason(String idClass, String reasons) {
        Optional<Class> classFind = adClassRepository.findById(idClass);
        if (!classFind.isPresent()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        classFind.get().setReasons(reasons);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Đã cập nhật lí do từ chối là: " + reasons);
        adClassRepository.save(classFind.get());
        String nameSemester = loggerUtil.getNameSemesterByIdClass(idClass);
        loggerUtil.sendLogStreamClass(stringBuilder.toString(), classFind.get().getCode(), nameSemester);
        loggerUtil.sendLogScreen(stringBuilder.toString(), nameSemester);
        return true;
    }

    @Override
    public Boolean addReasonClass(@Valid AdReasonsNoApproveRequest request) {
        List<Class> listClass = adClassRepository.findAllById(request.getListIdClass());
        if (listClass.isEmpty()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        List<LoggerResponse> loggerResponseList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Đã cập nhật lí do từ chối là: " + request.getReasons() + " của các lớp học là: ");
        listClass.forEach(xx -> {
            xx.setReasons(request.getReasons());
            LoggerResponse loggerResponse = new LoggerResponse();
            loggerResponse.setContent("Đã cập nhật lí do từ chối là: " + request.getReasons());
            loggerResponseList.add(loggerResponse);
            stringBuilder.append(xx.getCode() + ", ");
        });
        String nameSemester = loggerUtil.getNameSemesterByIdClass(listClass.get(0).getId());
        adClassRepository.saveAll(listClass);
        for (LoggerResponse loggerResponse : loggerResponseList) {
            loggerUtil.sendLogStreamClass(loggerResponse.getContent(), loggerResponse.getCodeClass(), nameSemester);
        }
        loggerUtil.sendLogScreen(stringBuilder.toString(), nameSemester);
        return true;
    }

    private Boolean handleNoApproveMeetingRequest(List<String> listIdMeetingRequest) {
        List<MeetingRequest> listMeetingRequestFind = adMeetingRequestRepository.findAllById(listIdMeetingRequest);
        if (listMeetingRequestFind.isEmpty()) {
            throw new RestApiException(Message.MEETING_REQUEST_NOT_EXISTS);
        }
        String idClass = listMeetingRequestFind.get(0).getClassId();
        Optional<Class> classOptional = adClassRepository.findById(idClass);
        if (!classOptional.isPresent()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        SimpleResponse simpleResponse = callApiIdentity.handleCallApiGetUserById(classOptional.get().getTeacherId());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Đã từ chối phê duyệt các buổi học: ");
        listMeetingRequestFind.forEach(meetingRequest -> {
            if (meetingRequest.getStatusMeetingRequest() != StatusMeetingRequest.CHO_PHE_DUYET) {
                throw new RestApiException(Message.STATUS_NOT_VALID);
            }
            stringBuilder.append(meetingRequest.getName() + ", ");
            meetingRequest.setStatusMeetingRequest(StatusMeetingRequest.TU_CHOI);
        });
        stringBuilder.append(" thuộc lớp học " + classOptional.get().getCode());
        adMeetingRequestRepository.saveAll(listMeetingRequestFind);
        String nameSemester = loggerUtil.getNameSemesterByIdClass(idClass);
        loggerUtil.sendLogStreamClass(stringBuilder.toString(), classOptional.get().getCode(), nameSemester);
        loggerUtil.sendLogScreen(stringBuilder.toString(), nameSemester);

        if (simpleResponse != null) {
            Runnable emailTask = () -> {
                String htmlBody = stringBuilder.toString();
                emailSender.sendEmail(new String[]{simpleResponse.getEmail()}, "[LAB-REPORT-APP] Thông báo từ chối phê duyệt buổi học", "Thông báo từ chối phê duyệt buổi học", htmlBody);
            };
            new Thread(emailTask).start();
        }

        return true;
    }

    private Boolean handleApproveMeetingRequest(List<String> listIdMeetingRequest) {
        List<MeetingRequest> listMeetingRequestFind = adMeetingRequestRepository.findAllById(listIdMeetingRequest);
        if (listMeetingRequestFind.isEmpty()) {
            throw new RestApiException(Message.MEETING_REQUEST_NOT_EXISTS);
        }
        String idClass = listMeetingRequestFind.get(0).getClassId();
        Optional<Class> classOptional = adClassRepository.findById(idClass);
        if (!classOptional.isPresent()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        List<Meeting> listMeetingNew = new ArrayList<>();
        List<Meeting> listCurrent = adMeetingRepository.getAllByClassId(idClass);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        listMeetingRequestFind.forEach(meetingRequest -> {
            AtomicBoolean shouldAddMeeting = new AtomicBoolean(true);
            listCurrent.forEach(meetingCurrent -> {
                if (sdf.format(meetingCurrent.getMeetingDate()).equals(sdf.format(meetingRequest.getMeetingDate()))
                        && meetingRequest.getMeetingPeriod().equals(meetingCurrent.getMeetingPeriod())) {
                    shouldAddMeeting.set(false);
                }
            });
            if (shouldAddMeeting.get()) {
                Meeting meeting = new Meeting();
                meeting.setName(meetingRequest.getName());
                meeting.setMeetingPeriod(meetingRequest.getMeetingPeriod());
                meeting.setTypeMeeting(meetingRequest.getTypeMeeting());
                meeting.setMeetingDate(meetingRequest.getMeetingDate());
                meeting.setClassId(idClass);
                meeting.setTeacherId(meetingRequest.getTeacherId());
                meeting.setStatusMeeting(StatusMeeting.BUOI_HOC);
                listMeetingNew.add(meeting);
            }
        });
        adMeetingRepository.saveAll(listMeetingNew);
        adMeetingRepository.updateNameMeeting(idClass);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Đã phê duyệt các buổi học sau: ");
        listMeetingRequestFind.forEach(meetingRequest -> {
            if (meetingRequest.getStatusMeetingRequest() != StatusMeetingRequest.CHO_PHE_DUYET) {
                throw new RestApiException(Message.STATUS_NOT_VALID);
            }
            stringBuilder.append(meetingRequest.getName() + ", ");
            meetingRequest.setStatusMeetingRequest(StatusMeetingRequest.DA_PHE_DUYET);
        });
        stringBuilder.append(" thuộc lớp học: " + classOptional.get().getCode());
        adMeetingRequestRepository.saveAll(listMeetingRequestFind);
        String nameSemester = loggerUtil.getNameSemesterByIdClass(idClass);
        loggerUtil.sendLogStreamClass(stringBuilder.toString(), classOptional.get().getCode(), nameSemester);
        loggerUtil.sendLogScreen(stringBuilder.toString(), nameSemester);
        SimpleResponse simpleResponse = callApiIdentity.handleCallApiGetUserById(classOptional.get().getTeacherId());

        if (simpleResponse != null) {
            Runnable emailTask = () -> {
                String htmlBody = stringBuilder.toString();
                emailSender.sendEmail(new String[]{simpleResponse.getEmail()}, "[LAB-REPORT-APP] Thông báo từ phê duyệt buổi học", "Thông báo phê duyệt buổi học", htmlBody);
            };
            new Thread(emailTask).start();
        }
        return true;
    }
}

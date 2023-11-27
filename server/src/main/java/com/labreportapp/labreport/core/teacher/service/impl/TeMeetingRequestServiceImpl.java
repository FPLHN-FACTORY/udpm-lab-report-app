package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.model.request.TeFindMeetingRequestRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeMeetingRequestAgainRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingRequestCustomResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingRequestResponse;
import com.labreportapp.labreport.core.teacher.repository.TeClassRepository;
import com.labreportapp.labreport.core.teacher.repository.TeMeetingRequestRepository;
import com.labreportapp.labreport.core.teacher.service.TeMeetingRequestService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.MeetingPeriod;
import com.labreportapp.labreport.entity.MeetingRequest;
import com.labreportapp.labreport.infrastructure.constant.StatusClass;
import com.labreportapp.labreport.infrastructure.constant.StatusMeetingRequest;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.labreport.util.DateConverter;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author hieundph25894 - duchieu212
 */
@Service
public class TeMeetingRequestServiceImpl implements TeMeetingRequestService {

    @Autowired
    private TeMeetingRequestRepository teMeetingRequestRepository;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private TeClassRepository teClassRepository;

    @Autowired
    private LoggerUtil loggerUtil;

    @Override
    public Page<TeMeetingRequestCustomResponse> getAll(TeFindMeetingRequestRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        Page<TeMeetingRequestResponse> listPage = teMeetingRequestRepository.getAllByClassIdAndStatus(request, pageable);
        List<String> idStudentList = listPage.getContent().stream()
                .map(TeMeetingRequestResponse::getIdTeacher)
                .distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<SimpleResponse> listGiangVien = callApiIdentity.handleCallApiGetListUserByListId(idStudentList);
        ConcurrentHashMap<String, SimpleResponse> mapTeacher = new ConcurrentHashMap<>();
        addDataInMapGiangVien(mapTeacher, listGiangVien);
        Page<TeMeetingRequestCustomResponse> pageNew = listPage.map(item -> {
            TeMeetingRequestCustomResponse obj = new TeMeetingRequestCustomResponse();
            obj.setId(item.getId());
            obj.setName(item.getName());
            obj.setMeetingDate(item.getMeetingDate());
            obj.setTypeMeeting(item.getTypeMeeting());
            obj.setMeetingPeriod(item.getMeetingPeriod());
            obj.setStartHour(item.getStartHour());
            obj.setStartMinute(item.getStartMinute());
            obj.setEndHour(item.getEndHour());
            obj.setEndMinute(item.getEndMinute());
            obj.setTeacher(null);
            if (item.getIdTeacher() != null) {
                SimpleResponse teacher = mapTeacher.get(item.getIdTeacher());
                if (teacher != null) {
                    obj.setTeacher(teacher.getName() + " - " + teacher.getUserName());
                } else {
                    obj.setTeacher("Không tồn tại");
                }
            }
            obj.setStatusMeetingRequest(item.getStatusMeetingRequest());
            return obj;
        });
        return pageNew;
    }

    @Override
    @Transactional
    public boolean sendMeetingRequestAgain(TeMeetingRequestAgainRequest request) {
        Optional<Class> classFind = teClassRepository.findById(request.getIdClass());
        if (classFind.isEmpty()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        if (classFind.get().getStatusClass().equals(StatusClass.LOCK)) {
            throw new RestApiException("Lớp học đã khóa, không thể gửi yêu cầu !");
        }
        List<MeetingRequest> listMeetingRequest = teMeetingRequestRepository.getAllByClassIdAndStatusReject(request.getIdClass());
        List<String> listRequest = request.getListMeetingRequestAgain();
        if (listRequest.isEmpty()) {
            throw new RestApiException("Vui lòng chọn buổi học để gửi yêu cầu !");
        }
        ConcurrentHashMap<String, SimpleResponse> mapTeacher = new ConcurrentHashMap<>();
        StringBuilder message = new StringBuilder();
        message.append("Đã gửi lại yêu cầu tạo buổi học: ");
        listMeetingRequest.forEach(i -> {
            listRequest.forEach(req -> {
                if (i.getId().equals(req)) {
                    message.append(" Ngày: ").append(DateConverter.convertDateToStringNotTime(i.getMeetingDate())).append(", ")
                            .append(i.getName())
                            .append(", ").append(" giảng viên: ");
                    if (i.getTeacherId() != null) {
                        SimpleResponse teacher = mapTeacher.get(i.getTeacherId());
                        if (teacher != null) {
                            message.append(teacher.getName() + " - " + teacher.getUserName());
                        }
                    }
                    message.append(", hình thức: ").append(i.getTypeMeeting()).append(", ");
                    i.setStatusMeetingRequest(StatusMeetingRequest.CHO_PHE_DUYET);
                }
            });
        });
        if (message.length() > 0 && message.charAt(message.length() - 1) == ',') {
            message.setCharAt(message.length() - 1, '.');
        }
        List<MeetingRequest> listSave = teMeetingRequestRepository.saveAll(listMeetingRequest);
        if (listSave.size() > 0) {
            if (!message.isEmpty()) {
                String codeClass = loggerUtil.getCodeClassByIdClass(request.getIdClass());
                String nameSemester = loggerUtil.getNameSemesterByIdClass(request.getIdClass());
                loggerUtil.sendLogStreamClass(message.toString(), codeClass, nameSemester);
            }
            return true;
        } else {
            return false;
        }

    }

    public void addDataInMapGiangVien(ConcurrentHashMap<String, SimpleResponse> mapAll, List<SimpleResponse> listGiangVien) {
        getALlPutMapGiangVien(mapAll, listGiangVien);
    }

    public void getALlPutMapGiangVien(ConcurrentHashMap<String, SimpleResponse> mapSimple, List<SimpleResponse> listGiangVien) {
        for (SimpleResponse simple : listGiangVien) {
            mapSimple.put(simple.getId(), simple);
        }
    }

    private void addDataMeetingPeriod(ConcurrentHashMap<String, MeetingPeriod> mapMeetingPeriod, List<MeetingPeriod> listMeetingPeriod) {
        getAllPutMeetingPeriod(mapMeetingPeriod, listMeetingPeriod);
    }

    private void getAllPutMeetingPeriod(ConcurrentHashMap<String, MeetingPeriod> mapMeetingPeriod, List<MeetingPeriod> listMeetingPeriod) {
        for (MeetingPeriod meeting : listMeetingPeriod) {
            mapMeetingPeriod.put(meeting.getName(), meeting);
        }
        listMeetingPeriod.clear();
    }


}

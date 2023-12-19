package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.model.TeCreateMeetingRequestRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindMeetingRequestRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeMeetingRequestAgainRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeUpdateMeetingRequestRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingRequestCustomResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeMeetingRequestResponse;
import com.labreportapp.labreport.core.teacher.repository.TeClassRepository;
import com.labreportapp.labreport.core.teacher.repository.TeMeetingPeriodRepository;
import com.labreportapp.labreport.core.teacher.repository.TeMeetingRequestRepository;
import com.labreportapp.labreport.core.teacher.service.TeMeetingRequestService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.MeetingPeriod;
import com.labreportapp.labreport.entity.MeetingRequest;
import com.labreportapp.labreport.infrastructure.apiconstant.ActorConstants;
import com.labreportapp.labreport.infrastructure.constant.StatusClass;
import com.labreportapp.labreport.infrastructure.constant.StatusMeetingRequest;
import com.labreportapp.labreport.infrastructure.constant.TypeMeeting;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.labreport.util.CompareUtil;
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

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
    private TeMeetingPeriodRepository teMeetingPeriodRepository;

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
        addDataInMapGiangVienClass(mapTeacher, listGiangVien);
        Page<TeMeetingRequestCustomResponse> pageNew = listPage.map(item -> {
            TeMeetingRequestCustomResponse obj = new TeMeetingRequestCustomResponse();
            obj.setId(item.getId());
            obj.setName(item.getName());
            obj.setMeetingDate(item.getMeetingDate());
            obj.setTypeMeeting(item.getTypeMeeting());
            obj.setIdMeetingPeriod(item.getIdMeetingPeriod());
            obj.setMeetingPeriod(item.getMeetingPeriod());
            obj.setStartHour(item.getStartHour());
            obj.setStartMinute(item.getStartMinute());
            obj.setEndHour(item.getEndHour());
            obj.setEndMinute(item.getEndMinute());
            obj.setTeacher(null);
            obj.setTeacherId(null);
            if (item.getIdTeacher() != null) {
                SimpleResponse teacher = mapTeacher.get(item.getIdTeacher().toLowerCase());
                if (teacher != null) {
                    obj.setTeacher(teacher.getName() + " - " + teacher.getUserName());
                    obj.setTeacherId(teacher.getId());
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
    public TeMeetingRequestCustomResponse update(TeUpdateMeetingRequestRequest request) {
        Optional<MeetingRequest> meetingFind = teMeetingRequestRepository.findById(request.getId());
        if (meetingFind.isEmpty()) {
            throw new RestApiException(Message.MEETING_NOT_EXISTS);
        }
        Optional<Class> classOptional = teClassRepository.findById(meetingFind.get().getClassId());
        if (classOptional.isEmpty()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        LocalDate meetingDateFind = Instant.ofEpochMilli(request.getMeetingDate())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        List<MeetingRequest> findDoubleMeeting = teMeetingRequestRepository
                .getMeetingRequestByIdClassAndMeetingDateAndMeetingPeriod(classOptional.get().getId(), meetingDateFind, request.getMeetingPeriod());
        if (findDoubleMeeting.size() > 0) {
            throw new RestApiException("Đã tồn tại buổi học, không thể sửa !");
        }
        MeetingPeriod meetingPeriodNew = teMeetingPeriodRepository.findById(request.getMeetingPeriod()).get();
        MeetingPeriod meetingPeriodOld = teMeetingPeriodRepository.findById(meetingFind.get().getMeetingPeriod()).get();

        meetingFind.get().setMeetingDate(request.getMeetingDate());
        meetingFind.get().setTypeMeeting(TypeMeeting.values()[request.getTypeMeeting()]);
        meetingFind.get().setMeetingPeriod(meetingPeriodNew.getId());

        MeetingRequest meetingNew = teMeetingRequestRepository.save(meetingFind.get());
        teMeetingRequestRepository.updateNameMeetingRequest(meetingFind.get().getClassId());
        Optional<MeetingRequest> meetingLaster = teMeetingRequestRepository.findById(meetingNew.getId());
        SimpleResponse simple = null;
        if (meetingLaster.isPresent()) {
            StringBuilder stringBuilder = new StringBuilder();
            String nameSemester = loggerUtil.getNameSemesterByIdClass(classOptional.get().getId());

            stringBuilder.append("Đã cập nhật buổi học yêu cầu: ").append(meetingFind.get().getName());
            if (!meetingFind.get().getName().equals(meetingLaster.get().getName())) {
                stringBuilder.append(" tên buổi học từ ").append(meetingFind.get().getName()).append(" thành ").append(meetingLaster.get().getName());
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String meetingDateOld = sdf.format(meetingFind.get().getMeetingDate());
            String meetingDateNew = sdf.format(request.getMeetingDate());
            String messageMeetingDate = CompareUtil.compareAndConvertMessage("ngày học của buổi học " + meetingFind.get().getName(), meetingDateOld, meetingDateNew, "");
            stringBuilder.append(messageMeetingDate);

            if (TypeMeeting.values()[request.getTypeMeeting()] != meetingFind.get().getTypeMeeting()) {
                if (request.getTypeMeeting() == 0) {
                    stringBuilder.append(". Đã cập nhật hình thức học của buổi học ").append(meetingFind.get().getName()).append(" từ Offline thành Online");
                } else {
                    stringBuilder.append(". Đã cập nhật hình thức học của buổi học ").append(meetingFind.get().getName()).append(" từ Online thành Offline");
                }
            }
            String messageMeetingPeriod = CompareUtil.compareAndConvertMessage("ca học của buổi học " + meetingFind.get().getName(), meetingPeriodOld.getName(), meetingPeriodNew.getName(), "");
            stringBuilder.append(messageMeetingPeriod);

            if (!request.getTeacherId().equals("") && request.getTeacherId() != null) {
                meetingFind.get().setTeacherId(request.getTeacherId());
                simple = callApiIdentity.handleCallApiGetUserById(request.getTeacherId());
                stringBuilder.append(" giảng viên: ").append(simple.getName()).append("-").append(simple.getUserName()).append(",");
            }
            stringBuilder.append(" trạng thái: ").append(meetingNew.getStatusMeetingRequest()).append(".");
            loggerUtil.sendLogStreamClass(stringBuilder.toString(), classOptional.get().getCode(), nameSemester);
        }

        TeMeetingRequestCustomResponse obj = new TeMeetingRequestCustomResponse();
        obj.setId(meetingNew.getId());
        obj.setName(meetingNew.getName());
        obj.setTypeMeeting(meetingNew.getTypeMeeting().ordinal());
        obj.setMeetingDate(meetingNew.getMeetingDate());
        obj.setIdMeetingPeriod(meetingPeriodNew.getId());
        obj.setMeetingPeriod(meetingPeriodNew.getName());
        obj.setStartHour(meetingPeriodNew.getStartHour());
        obj.setStartMinute(meetingPeriodNew.getStartMinute());
        obj.setEndHour(meetingPeriodNew.getEndHour());
        obj.setEndMinute(meetingPeriodNew.getEndMinute());
        obj.setTeacher(null);
        obj.setTeacherId(null);
        if (meetingNew.getTeacherId() != null) {
            if (simple != null) {
                obj.setTeacher(simple.getName() + " - " + simple.getUserName());
                obj.setTeacherId(simple.getId());
            } else {
                obj.setTeacher("Không tồn tại");
            }
        }
        obj.setStatusMeetingRequest(meetingNew.getStatusMeetingRequest().ordinal());
        return obj;
    }

    @Override
    @Transactional
    public MeetingRequest create(TeCreateMeetingRequestRequest request) {
        Optional<Class> findClass = teClassRepository.findById(request.getIdClass());
        if (findClass.isEmpty()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        MeetingPeriod meetingPeriodFind = teMeetingPeriodRepository.findById(request.getMeetingPeriod()).get();
        if (meetingPeriodFind == null) {
            throw new RestApiException(Message.MEETING_PERIOD_NOT_EXITS);
        }
        LocalDate meetingDateFind = Instant.ofEpochMilli(request.getMeetingDate())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        List<MeetingRequest> findDoubleMeeting = teMeetingRequestRepository
                .getMeetingRequestByIdClassAndMeetingDateAndMeetingPeriod(request.getIdClass(), meetingDateFind, request.getMeetingPeriod());
        if (findDoubleMeeting.size() >0) {
            throw new RestApiException("Đã tồn tại buổi học !");
        }
        MeetingRequest meetingRequest = new MeetingRequest();
        meetingRequest.setClassId(request.getIdClass());
        meetingRequest.setMeetingDate(request.getMeetingDate());
        meetingRequest.setMeetingPeriod(request.getMeetingPeriod());
        meetingRequest.setTeacherId(request.getTeacherId());
        meetingRequest.setTypeMeeting(TypeMeeting.values()[request.getTypeMeeting()]);
        meetingRequest.setStatusMeetingRequest(StatusMeetingRequest.CHO_PHE_DUYET);
        MeetingRequest meetingRequestSave = teMeetingRequestRepository.save(meetingRequest);
        teMeetingRequestRepository.updateNameMeetingRequest(request.getIdClass());
        SimpleResponse simple = null;
        StringBuilder stringBuilder = new StringBuilder();
        Optional<MeetingRequest> meetingRequestFind = teMeetingRequestRepository.findById(meetingRequestSave.getId());
        stringBuilder.append("Đã tạo buổi học yêu cầu: ");
        meetingRequestFind.ifPresent(value -> stringBuilder.append(value.getName()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String meetingDate = sdf.format(request.getMeetingDate());
        stringBuilder.append(", ngày học ").append(meetingDate);
        if (request.getTypeMeeting() == 0) {
            stringBuilder.append(", hình thức Online");
        } else {
            stringBuilder.append(", hình thức Offline");
        }
        stringBuilder.append(", ").append(meetingPeriodFind.getName());

        if (!request.getTeacherId().equals("") && request.getTeacherId() != null) {
            simple = callApiIdentity.handleCallApiGetUserById(request.getTeacherId());
            stringBuilder.append(", giảng viên: ").append(simple.getName()).append("-").append(simple.getUserName()).append(".");
        }
        String nameSemester = loggerUtil.getNameSemesterByIdClass(request.getIdClass());
        String nameClass = loggerUtil.getCodeClassByIdClass(request.getIdClass());
        loggerUtil.sendLogStreamClass(stringBuilder.toString(), nameClass, nameSemester);
        return meetingRequestSave;
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
        ConcurrentHashMap<String, MeetingPeriod> mapMeetingPeriod = new ConcurrentHashMap<>();
        List<MeetingPeriod> listMeetingPeriod = teMeetingPeriodRepository.findAll();
        addDataInMapGiangVienSend(mapTeacher);
        addDataMeetingPeriodSend(mapMeetingPeriod, listMeetingPeriod);
        StringBuilder message = new StringBuilder();
        message.append("Đã gửi lại yêu cầu tạo buổi học: ");
        listMeetingRequest.forEach(i -> {
            listRequest.forEach(req -> {
                if (i.getId().equals(req)) {
                    message.append(" Ngày: ").append(DateConverter.convertDateToStringNotTime(i.getMeetingDate())).append(", ")
                            .append(i.getName());
                    MeetingPeriod meetingPeriodFind = mapMeetingPeriod.get(i.getMeetingPeriod().toLowerCase());
                    if (meetingPeriodFind != null) {
                        message.append(", ").append(meetingPeriodFind.getName());
                    }
                    message.append(", ").append(" giảng viên: ");
                    if (i.getTeacherId() != null) {
                        SimpleResponse teacher = mapTeacher.get(i.getTeacherId().toLowerCase());
                        if (teacher != null) {
                            message.append(teacher.getName()).append(" - ").append(teacher.getUserName());
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

    @Override
    public String showReasons(String idClass) {
        Optional<Class> classOptional = teClassRepository.findById(idClass);
        if (!classOptional.isPresent()) {
            return null;
        }
        return classOptional.get().getReasons();
    }

    public void addDataInMapGiangVienSend(ConcurrentHashMap<String, SimpleResponse> mapAll) {
        List<SimpleResponse> giangVienHuongDanList = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_TEACHER);
        getALlPutMapGiangVienSend(mapAll, giangVienHuongDanList);
    }

    public void getALlPutMapGiangVienSend(ConcurrentHashMap<String, SimpleResponse> mapSimple, List<SimpleResponse> listGiangVien) {
        for (SimpleResponse simple : listGiangVien) {
            mapSimple.put(simple.getId().toLowerCase(), simple);
        }
        listGiangVien.clear();
    }

    public void addDataInMapGiangVienClass(ConcurrentHashMap<String, SimpleResponse> mapAll, List<SimpleResponse> listGiangVien) {
        getALlPutMapGiangVienClass(mapAll, listGiangVien);
    }

    public void getALlPutMapGiangVienClass(ConcurrentHashMap<String, SimpleResponse> mapSimple, List<SimpleResponse> listGiangVien) {
        for (SimpleResponse simple : listGiangVien) {
            mapSimple.put(simple.getId().toLowerCase(), simple);
        }
        listGiangVien.clear();
    }

    private void addDataMeetingPeriodSend(ConcurrentHashMap<String, MeetingPeriod> mapMeetingPeriod, List<MeetingPeriod> listMeetingPeriod) {
        getAllPutMeetingPeriodSend(mapMeetingPeriod, listMeetingPeriod);
    }

    private void getAllPutMeetingPeriodSend(ConcurrentHashMap<String, MeetingPeriod> mapMeetingPeriod, List<MeetingPeriod> listMeetingPeriod) {
        for (MeetingPeriod meeting : listMeetingPeriod) {
            mapMeetingPeriod.put(meeting.getId().toLowerCase(), meeting);
        }
        listMeetingPeriod.clear();
    }

}

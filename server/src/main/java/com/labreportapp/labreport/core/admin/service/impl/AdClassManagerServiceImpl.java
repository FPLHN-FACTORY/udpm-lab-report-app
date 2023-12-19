package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.excel.AdExportExcelClass;
import com.labreportapp.labreport.core.admin.excel.AdImportExcelClass;
import com.labreportapp.labreport.core.admin.model.request.AdCreateClassRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindClassRequest;
import com.labreportapp.labreport.core.admin.model.request.AdRandomClassRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateClassRequest;
import com.labreportapp.labreport.core.admin.model.response.AdActivityClassResponse;
import com.labreportapp.labreport.core.admin.model.response.AdClassCustomResponse;
import com.labreportapp.labreport.core.admin.model.response.AdClassResponse;
import com.labreportapp.labreport.core.admin.model.response.AdDetailClassCustomResponse;
import com.labreportapp.labreport.core.admin.model.response.AdDetailClassRespone;
import com.labreportapp.labreport.core.admin.model.response.AdExportExcelClassCustom;
import com.labreportapp.labreport.core.admin.model.response.AdExportExcelClassResponse;
import com.labreportapp.labreport.core.admin.model.response.AdFindSelectClassCustom;
import com.labreportapp.labreport.core.admin.model.response.AdFindSelectClassResponse;
import com.labreportapp.labreport.core.admin.model.response.AdImportExcelClassResponse;
import com.labreportapp.labreport.core.admin.model.response.AdListClassCustomResponse;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingPeriodResponse;
import com.labreportapp.labreport.core.admin.model.response.AdSemesterAcResponse;
import com.labreportapp.labreport.core.admin.repository.AdActivityRepository;
import com.labreportapp.labreport.core.admin.repository.AdClassConfigurationRepository;
import com.labreportapp.labreport.core.admin.repository.AdClassRepository;
import com.labreportapp.labreport.core.admin.repository.AdMeetingPeriodRepository;
import com.labreportapp.labreport.core.admin.repository.AdSemesterRepository;
import com.labreportapp.labreport.core.admin.service.AdClassService;
import com.labreportapp.labreport.core.common.base.ImportExcelResponse;
import com.labreportapp.labreport.core.common.base.LoggerResponse;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailClassResponse;
import com.labreportapp.labreport.core.teacher.repository.TeClassRepository;
import com.labreportapp.labreport.entity.Activity;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.ClassConfiguration;
import com.labreportapp.labreport.entity.MeetingPeriod;
import com.labreportapp.labreport.entity.Semester;
import com.labreportapp.labreport.infrastructure.apiconstant.ActorConstants;
import com.labreportapp.labreport.infrastructure.constant.StatusClass;
import com.labreportapp.labreport.infrastructure.constant.StatusHoneyPlus;
import com.labreportapp.labreport.infrastructure.constant.StatusTeacherEdit;
import com.labreportapp.labreport.repository.ActivityRepository;
import com.labreportapp.labreport.repository.LevelRepository;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.labreport.util.ClassHelper;
import com.labreportapp.labreport.util.CompareUtil;
import com.labreportapp.labreport.util.FormUtils;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.labreport.util.SemesterHelper;
import com.labreportapp.portalprojects.infrastructure.configemail.EmailSender;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.NotFoundException;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Synchronized;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author quynhncph26201
 */
@Service
@Validated
public class AdClassManagerServiceImpl implements AdClassService {

    @Autowired
    private AdClassRepository repository;

    @Autowired
    private AdActivityRepository adActivityRepository;

    @Autowired
    private AdSemesterRepository adSemesterRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    @Qualifier(LevelRepository.NAME)
    private LevelRepository levelRepository;

    @Autowired
    private AdExportExcelClass adExportExcelClass;

    @Autowired
    private ClassHelper classHelper;

    @Autowired
    private SemesterHelper semesterHelper;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    @Qualifier(ActivityRepository.NAME)
    private ActivityRepository activityRepository;

    @Autowired
    private AdMeetingPeriodRepository adMeetingPeriodRepository;

    @Autowired
    private AdClassConfigurationRepository adClassConfigurationRepository;

    @Autowired
    private TeClassRepository teClassRepository;

    @Autowired
    private EmailSender emailSender;

    private FormUtils formUtils = new FormUtils();

    @Override
    public List<AdClassResponse> getAllClass() {
        return repository.getAllClass();
    }

    @Override
    public List<SimpleEntityProjection> getAllLevel() {
        return levelRepository.getAllSimpleEntityProjection();
    }

    @Override
    public List<AdClassResponse> getAllClassBySemester(AdFindClassRequest adFindClass) {
        return repository.getAllClassBySemester(adFindClass);
    }

    @Override
    public List<AdClassResponse> findClassByCondition(String code, Long classPeriod, String idTeacher) {
        return repository.findClassByCondition(code, classPeriod, idTeacher);
    }

    @Override
    public List<AdSemesterAcResponse> getAllSemester() {
        return repository.getAllSemesters();
    }

    @Override
    public List<AdActivityClassResponse> getAllByIdSemester(AdFindClassRequest adFindClass) {
        return repository.getAllByIdSemester(adFindClass);
    }

    @Override
    public AdClassCustomResponse createClass(@Valid AdCreateClassRequest request) {
        Class classNew = new Class();
        classNew.setCode(classHelper.genMaLopTheoHoatDong(request.getActivityId()));
        classNew.setClassSize(0);
        classNew.setClassPeriod(request.getClassPeriod());
        classNew.setPassword(null);
        classNew.setStatusHoneyPlus(StatusHoneyPlus.CHUA_CONG);
        classNew.setStatusClass(StatusClass.OPEN);
        classNew.setStatusTeacherEdit(StatusTeacherEdit.values()[request.getStatusTeacherEdit()]);
        Optional<Activity> activityFind = adActivityRepository.findById(request.getActivityId());
        if (activityFind.isEmpty()) {
            throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
        }
        if (DateUtils.truncate(new Date(request.getStartTime()), Calendar.DATE).getTime() <
                activityFind.get().getStartTime() || DateUtils.truncate(new Date(request.getStartTime()), Calendar.DATE).getTime() > activityFind.get().getEndTime()) {
            throw new RestApiException(Message.THOI_GIAN_BAT_DAU_CUA_LOP_HOC_PHAI_NAM_TRONG_KHOANG_THOI_GIAN_CUA_HOAT_DONG);
        }
        classNew.setActivityId(request.getActivityId());
        classNew.setStartTime(DateUtils.truncate(new Date(request.getStartTime()), Calendar.DATE).getTime());
        if (!request.getTeacherId().equals("")) {
            classNew.setTeacherId(request.getTeacherId());
        }
        repository.save(classNew);

        AdClassCustomResponse adClassCustomResponse = new AdClassCustomResponse();
        adClassCustomResponse.setId(classNew.getId());
        adClassCustomResponse.setClassSize(classNew.getClassSize());
        MeetingPeriod meetingPeriodFind = adMeetingPeriodRepository.findById(request.getClassPeriod()).get();
        adClassCustomResponse.setClassPeriod(meetingPeriodFind.getName());
        adClassCustomResponse.setStartHour(meetingPeriodFind.getStartHour());
        adClassCustomResponse.setStartMinute(meetingPeriodFind.getStartMinute());
        adClassCustomResponse.setEndHour(meetingPeriodFind.getEndHour());
        adClassCustomResponse.setEndMinute(meetingPeriodFind.getEndMinute());
        adClassCustomResponse.setCode(classNew.getCode());
        adClassCustomResponse.setActivityName(activityFind.get().getName());
        adClassCustomResponse.setStartTime(classNew.getStartTime());
        adClassCustomResponse.setStatusClass("Chưa đủ điều kiện");
        adClassCustomResponse.setStatusTeacherEdit(classNew.getStatusTeacherEdit() == StatusTeacherEdit.CHO_PHEP ? 0 : 1);
        adClassCustomResponse.setNameLevel(levelRepository.findById(activityFind.get().getLevelId()).get().getName());
        StringBuilder stringBuilder = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        stringBuilder.append("Đã tạo lớp học ").append(adClassCustomResponse.getCode());
        stringBuilder.append(", sĩ số là: ").append(adClassCustomResponse.getClassSize());
        stringBuilder.append("ca học của lớp là: ").append(adClassCustomResponse.getClassPeriod());
        stringBuilder.append(", thuộc hoạt động: ").append(adClassCustomResponse.getActivityName());
        stringBuilder.append(", thời gian bắt đầu học là: ").append(sdf.format(adClassCustomResponse.getStartTime()));
        stringBuilder.append(", quyền giảng viên chỉnh sửa là: ").append(adClassCustomResponse.getStatusTeacherEdit() == 0 ? "Cho phép" : "Không cho phép");
        if (!request.getTeacherId().equals("")) {
            adClassCustomResponse.setTeacherId(request.getTeacherId());

            SimpleResponse response = callApiIdentity.handleCallApiGetUserById(request.getTeacherId());
            adClassCustomResponse.setUserNameTeacher(response.getUserName());
            stringBuilder.append(", giảng viên là: " + response.getUserName() + " - " + response.getName());
        }
        String nameSemester = loggerUtil.getNameSemesterByIdClass(adClassCustomResponse.getId());
        loggerUtil.sendLogScreen(stringBuilder.toString(), nameSemester);
        loggerUtil.sendLogStreamClass(stringBuilder.toString(), adClassCustomResponse.getCode(), nameSemester);
        return adClassCustomResponse;
    }

    @Override
    public AdClassCustomResponse updateClass(@Valid AdUpdateClassRequest request, String id) {
        Class classNew = repository.findById(id).get();
        Optional<Activity> activityFind = adActivityRepository.findById(request.getActivityId());
        if (activityFind.isEmpty()) {
            throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
        }
        if (DateUtils.truncate(new Date(request.getStartTime()), Calendar.DATE).getTime() <
                activityFind.get().getStartTime() || DateUtils.truncate(new Date(request.getStartTime()), Calendar.DATE).getTime() > activityFind.get().getEndTime()) {
            throw new RestApiException(Message.THOI_GIAN_BAT_DAU_CUA_LOP_HOC_PHAI_NAM_TRONG_KHOANG_THOI_GIAN_CUA_HOAT_DONG);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Lớp: ").append(classNew.getCode()).append(": ");
        String classPeriodOld = classNew.getClassPeriod();
        String classPeriodNew = request.getClassPeriod();
        if (classPeriodOld != null && classPeriodNew != null && !classPeriodOld.equals(classPeriodNew)) {
            MeetingPeriod meetingPeriodNew = adMeetingPeriodRepository.findById(classPeriodNew).get();
            MeetingPeriod meetingPeriodOld = adMeetingPeriodRepository.findById(classPeriodOld).get();
            String messageClassPeriod = CompareUtil.compareAndConvertMessage("ca học của lớp " +
                    classNew.getCode(), meetingPeriodOld.getName(), meetingPeriodNew.getName(), "");
            stringBuilder.append(messageClassPeriod);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String messageStartTime = CompareUtil.compareAndConvertMessage("thời gian bắt đầu của lớp " +
                classNew.getCode(), sdf.format(classNew.getStartTime()), sdf.format(request.getStartTime()), "");
        stringBuilder.append(messageStartTime);
        if (classNew.getStatusTeacherEdit().ordinal() != request.getStatusTeacherEdit()) {
            String messageStatusTeacherEdit = ". Đã cập nhật quyền giảng viên chỉnh sửa của lớp " + classNew.getCode() + " từ " +
                    (classNew.getStatusTeacherEdit().ordinal() == 0 ? "Cho phép" : "Không cho phép") + " thành " +
                    (request.getStatusTeacherEdit() == 0 ? "Cho phép" : "Không cho phép");
            stringBuilder.append(messageStatusTeacherEdit);
        }
        if (classNew.getStatusClass().ordinal() != request.getStatusClass()) {
            stringBuilder.append(". Đã cập nhật trạng thái của lớp từ " +
                    (classNew.getStatusClass().ordinal() == 0 ? "'Mở'" : "'Đóng'") + " thành " +
                    (request.getStatusClass() == 0 ? "'Mở'" : "'Đóng'"));
        }
        classNew.setStatusClass(StatusClass.values()[request.getStatusClass()]);
        classNew.setStartTime(request.getStartTime());
        MeetingPeriod meetingPeriodFind = null;
        if (request.getClassPeriod() != null) {
            meetingPeriodFind = adMeetingPeriodRepository.findById(request.getClassPeriod()).get();
            classNew.setClassPeriod(meetingPeriodFind.getId());
        }
        classNew.setStatusTeacherEdit(StatusTeacherEdit.values()[request.getStatusTeacherEdit()]);

        classNew.setActivityId(request.getActivityId());
        Boolean check = false;
        if (request.getTeacherId() != null && !request.getTeacherId().equals("")) {
            if (classNew.getTeacherId() == null || !classNew.getTeacherId().equals(request.getTeacherId())) {
                check = true;
                classNew.setTeacherId(request.getTeacherId());
            }
        } else {
            classNew.setTeacherId(null);
        }

        repository.save(classNew);
        AdClassCustomResponse adClassCustomResponse = new AdClassCustomResponse();
        adClassCustomResponse.setId(classNew.getId());
        adClassCustomResponse.setClassSize(classNew.getClassSize());
        if (meetingPeriodFind != null) {
            adClassCustomResponse.setStartHour(meetingPeriodFind.getStartHour());
            adClassCustomResponse.setStartMinute(meetingPeriodFind.getStartMinute());
            adClassCustomResponse.setEndHour(meetingPeriodFind.getEndHour());
            adClassCustomResponse.setEndMinute(meetingPeriodFind.getEndMinute());
            adClassCustomResponse.setClassPeriod(meetingPeriodFind.getName());
        }
        adClassCustomResponse.setCode(classNew.getCode());
        adClassCustomResponse.setActivityName(activityFind.get().getName());
        adClassCustomResponse.setStartTime(classNew.getStartTime());
        adClassCustomResponse.setNameLevel(levelRepository.findById(activityFind.get().getLevelId()).get().getName());
        adClassCustomResponse.setStatusTeacherEdit(classNew.getStatusTeacherEdit() == StatusTeacherEdit.CHO_PHEP ? 0 : 1);
        if (request.getTeacherId() != null && !request.getTeacherId().equals("")) {
            adClassCustomResponse.setTeacherId(request.getTeacherId());
            SimpleResponse response = callApiIdentity.handleCallApiGetUserById(request.getTeacherId());
            adClassCustomResponse.setUserNameTeacher(response.getUserName());
            if (check) {
                stringBuilder.append(". Đã cập nhật giảng viên của lớp thành " + response.getUserName() + " - " + response.getName());
            }
        }
        if (!stringBuilder.toString().endsWith(": ")) {
            String nameSemester = loggerUtil.getNameSemesterByIdClass(id);
            loggerUtil.sendLogScreen(stringBuilder.toString(), nameSemester);
            loggerUtil.sendLogStreamClass(stringBuilder.toString(), classNew.getCode(), nameSemester);
        }
        return adClassCustomResponse;
    }

    @Override
    public PageableObject<AdListClassCustomResponse> searchClass(final AdFindClassRequest adFindClass) {
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
        List<ClassConfiguration> classConfigurationList = adClassConfigurationRepository.findAll();
        ClassConfiguration classConfiguration = null;
        PageableObject<AdListClassCustomResponse> pageableObject = new PageableObject<>();
        if (!classConfigurationList.isEmpty() && classConfigurationList != null) {
            classConfiguration = adClassConfigurationRepository.findAll().get(0);
            adFindClass.setValueClassSize(classConfiguration.getClassSizeMin());
        } else {
            return null;
        }
        Page<AdClassResponse> pageList = repository.findClassBySemesterAndActivity(adFindClass, pageable);
        List<AdClassResponse> listResponse = pageList.getContent();
        List<String> idList = listResponse.stream()
                .map(AdClassResponse::getTeacherId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> response = callApiIdentity.handleCallApiGetListUserByListId(idList);
        List<AdListClassCustomResponse> listClassCustomResponses = new ArrayList<>();
        for (AdClassResponse adClassResponse : listResponse) {
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
            if (adListClassCustomResponse.getClassSize() > classConfiguration.getClassSizeMin()) {
                adListClassCustomResponse.setStatusClass("Đủ điều kiện");
            } else {
                adListClassCustomResponse.setStatusClass("Chưa đủ điều kiện");
            }
            listClassCustomResponses.add(adListClassCustomResponse);
        }
        pageableObject.setData(listClassCustomResponses);
        pageableObject.setCurrentPage(pageList.getNumber());
        pageableObject.setTotalPages(pageList.getTotalPages());
        return pageableObject;
    }

    @Override
    public ByteArrayOutputStream exportExcelClass(HttpServletResponse response, final AdFindClassRequest request) {
        String idSemesterCurrent = semesterHelper.getSemesterCurrent();
        if (request.getIdSemester() == null) {
            if (idSemesterCurrent != null) {
                request.setIdSemester(idSemesterCurrent);
                request.setIdActivity("");
            } else {
                request.setIdSemester("");
            }
        } else if (request.getIdSemester().equalsIgnoreCase("")) {
            if (idSemesterCurrent != null) {
                request.setIdSemester(idSemesterCurrent);
                request.setIdActivity("");
            } else {
                request.setIdSemester("");
            }
        }
        ClassConfiguration classConfiguration = adClassConfigurationRepository.findAll().get(0);
        request.setValueClassSize(classConfiguration.getClassSizeMin());
        List<AdExportExcelClassResponse> listClassResponse = repository.findClassExportExcel(request);

        List<String> distinctTeacherIds = listClassResponse.stream()
                .map(AdExportExcelClassResponse::getTeacherId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listSimpleResponse = callApiIdentity.handleCallApiGetListUserByListId(distinctTeacherIds);
        List<AdExportExcelClassCustom> listCustom = new ArrayList<>();
        listClassResponse.forEach(res -> {
            if (res.getTeacherId() != null) {
                listSimpleResponse.forEach(simple -> {
                    if (res.getTeacherId().equals(simple.getId())) {
                        AdExportExcelClassCustom adExportExcelClassCustom = new AdExportExcelClassCustom();
                        adExportExcelClassCustom.setStt(res.getStt());
                        adExportExcelClassCustom.setCode(res.getCode());
                        adExportExcelClassCustom.setStartHour(res.getStartHour());
                        adExportExcelClassCustom.setStartMinute(res.getStartMinute());
                        adExportExcelClassCustom.setEndHour(res.getEndHour());
                        adExportExcelClassCustom.setEndMinute(res.getEndMinute());
                        adExportExcelClassCustom.setClassPeriod(res.getClassPeriod());
                        adExportExcelClassCustom.setClassSize(res.getClassSize());
                        adExportExcelClassCustom.setNameActivity(res.getNameActivity());
                        adExportExcelClassCustom.setNameLevel(res.getNameLevel());
                        adExportExcelClassCustom.setStartTime(res.getStartTime());
                        adExportExcelClassCustom.setTeacherId(res.getTeacherId());
                        adExportExcelClassCustom.setUserNameTeacher(simple.getUserName());
                        listCustom.add(adExportExcelClassCustom);
                    }
                });
            } else {
                AdExportExcelClassCustom adExportExcelClassCustom = new AdExportExcelClassCustom();
                adExportExcelClassCustom.setStt(res.getStt());
                adExportExcelClassCustom.setCode(res.getCode());
                adExportExcelClassCustom.setStartHour(res.getStartHour());
                adExportExcelClassCustom.setStartMinute(res.getStartMinute());
                adExportExcelClassCustom.setEndHour(res.getEndHour());
                adExportExcelClassCustom.setEndMinute(res.getEndMinute());
                adExportExcelClassCustom.setClassPeriod(res.getClassPeriod());
                adExportExcelClassCustom.setClassSize(res.getClassSize());
                adExportExcelClassCustom.setNameActivity(res.getNameActivity());
                adExportExcelClassCustom.setNameLevel(res.getNameLevel());
                adExportExcelClassCustom.setStartTime(res.getStartTime());
                listCustom.add(adExportExcelClassCustom);
            }
        });
        List<SimpleResponse> giangVienHuongDanList = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_TEACHER);
        return adExportExcelClass.export(response, listCustom, giangVienHuongDanList);
    }

    @Override
    public AdDetailClassCustomResponse adFindClassById(String id) {
        AdDetailClassRespone adDetailClassRespone = repository.adfindClassById(id);
        if (adDetailClassRespone == null) {
            throw new NotFoundException(Message.CLASS_NOT_EXISTS);
        }
        AdDetailClassCustomResponse customResponse = new AdDetailClassCustomResponse();
        AdDetailClassRespone getOptional = adDetailClassRespone;
        SimpleResponse response = null;
        if (getOptional.getTeacherId() != null) {
            response = callApiIdentity.handleCallApiGetUserById(getOptional.getTeacherId());
        }
        customResponse.setId(getOptional.getId());
        customResponse.setCode(getOptional.getCode());
        customResponse.setClassPeriod(getOptional.getClassPeriod());
        customResponse.setClassPeriodId(getOptional.getClassPeriodId());
        customResponse.setStartHour(getOptional.getStartHour());
        customResponse.setStartMinute(getOptional.getStartMinute());
        customResponse.setEndHour(getOptional.getEndHour());
        customResponse.setEndMinute(getOptional.getEndMinute());
        customResponse.setStartTime(getOptional.getStartTime());
        customResponse.setPassWord(getOptional.getPassWord());
        customResponse.setClassSize(getOptional.getClassSize());
        customResponse.setDescriptions(getOptional.getDescriptions());
        customResponse.setTeacherId(getOptional.getTeacherId());
        customResponse.setActivityId(getOptional.getActivityId());
        customResponse.setActivityLevel(getOptional.getActivityLevel());
        customResponse.setActivityName(getOptional.getActivityName());
        customResponse.setSemesterId(getOptional.getSemesterId());
        customResponse.setSemesterName(getOptional.getSemesterName());
        customResponse.setLevelId(getOptional.getLevelId());
        customResponse.setStatusTeacherEdit(getOptional.getStatusTeacherEdit());
        customResponse.setStatusClass(getOptional.getStatusClass());
        if (!Objects.isNull(response)) {
            customResponse.setTeacherName(response.getName());
            customResponse.setTeacherUserName(response.getUserName());
        }
        return customResponse;
    }

    @Override
    @Synchronized
    public Boolean randomClass(@Valid AdRandomClassRequest request) {
        try {
            String maLopMax = classHelper.genMaLopTheoHoatDong(request.getActivityId());
            String codeActivity = activityRepository.getCodeActivity(request.getActivityId());
            if (maLopMax == null) {
                throw new RestApiException(Message.CREATE_CLASS_FAIL);
            }
            Optional<Activity> activityFind = activityRepository.findById(request.getActivityId());
            if (!activityFind.isPresent()) {
                throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
            }
            Optional<Semester> semesterFind = adSemesterRepository.findById(activityFind.get().getSemesterId());
            if (!semesterFind.isPresent()) {
                throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
            }
            String[] array = maLopMax.split("_");
            Integer count = Integer.parseInt(array[array.length - 1]);
            StringBuilder stringBuilder = new StringBuilder();
            List<Class> listClass = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            stringBuilder.append("Đã tạo những lớp học: ");
            List<LoggerResponse> loggerResponseList = new ArrayList<>();
            for (int i = 0; i < request.getNumberRandon(); i++) {
                Class classNew = new Class();
                classNew.setClassSize(0);
                classNew.setStatusClass(StatusClass.OPEN);
                classNew.setStartTime(DateUtils.truncate(new Date(request.getStartTime()), Calendar.DATE).getTime());
                classNew.setActivityId(request.getActivityId());
                String codeClass = codeActivity + "_" + (count++);
                classNew.setCode(codeClass);
                classNew.setPassword(null);
                classNew.setStatusHoneyPlus(StatusHoneyPlus.CHUA_CONG);
                classNew.setStatusTeacherEdit(StatusTeacherEdit.KHONG_CHO_PHEP);
                listClass.add(classNew);
                stringBuilder.append(codeClass).append(", ");
                LoggerResponse loggerResponse = new LoggerResponse();
                loggerResponse.setCodeClass(codeClass);
                loggerResponse.setContent("Đã tạo lớp học: " + codeClass + ", với thời gian bắt đầu là: " + sdf.format(request.getStartTime()));
                loggerResponseList.add(loggerResponse);
            }
            stringBuilder.append(" với thời gian bắt đầu là: ");
            stringBuilder.append(sdf.format(request.getStartTime()));

            loggerUtil.sendLogScreen(stringBuilder.toString(), semesterFind.get().getName());
            loggerResponseList.forEach(res -> {
                loggerUtil.sendLogStreamClass(res.getContent(), res.getCodeClass(), semesterFind.get().getName());
            });
            repository.saveAll(listClass);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ImportExcelResponse importExcelClass(MultipartFile multipartFile, String idSemester) {
        ImportExcelResponse response = new ImportExcelResponse();
        try {
            response.setStatus(true);
            AdImportExcelClass adImportExcelClass = new AdImportExcelClass();
            List<AdImportExcelClassResponse> listClassImport = adImportExcelClass.importData(multipartFile);
            ConcurrentHashMap<String, SimpleResponse> mapGiangVien = new ConcurrentHashMap<>();
            ConcurrentHashMap<String, SimpleResponse> mapGiangVienKeyId = new ConcurrentHashMap<>();
            ConcurrentHashMap<String, Class> mapClass = new ConcurrentHashMap<>();
            ConcurrentHashMap<String, Class> mapClassOld = new ConcurrentHashMap<>();
            ConcurrentHashMap<String, MeetingPeriod> mapMeetingPeriod = new ConcurrentHashMap<>();
            ConcurrentHashMap<String, MeetingPeriod> mapMeetingPeriodKeyId = new ConcurrentHashMap<>();
            addDataInMapGiangVien(mapGiangVien, mapGiangVienKeyId);
            addDataInMapClass(mapClass, mapClassOld, idSemester);
            addDataInMapMeetingPeriod(mapMeetingPeriod, mapMeetingPeriodKeyId);
            ConcurrentHashMap<String, Class> listClassUpdate = new ConcurrentHashMap<>();
            listClassImport.parallelStream().forEach(classExcel -> {
                if (classExcel.getCode().isEmpty()) {
                    response.setStatus(false);
                    response.setMessage("Mã lớp không được để trống");
                    return;
                }
                Class classFind = mapClass.get(classExcel.getCode());
                if (classFind == null) {
                    response.setStatus(false);
                    response.setMessage("Không tìm thấy lớp học " + classExcel.getCode());
                    return;
                }
                if (!classExcel.getClassPeriod().equals("")) {
                    MeetingPeriod meetingPeriodFind = mapMeetingPeriod.get(classExcel.getClassPeriod().toLowerCase());
                    if (meetingPeriodFind == null) {
                        response.setStatus(false);
                        response.setMessage("Ca học: " + classExcel.getClassPeriod() + " không tồn tại");
                        return;
                    }
                    classFind.setClassPeriod(meetingPeriodFind.getId());
                } else {
                    classFind.setClassPeriod(null);
                }
                SimpleResponse giangVien = null;
                if (!classExcel.getUsernameTeacher().isEmpty()) {
                    giangVien = mapGiangVien.get(classExcel.getUsernameTeacher().toLowerCase());
                    if (giangVien == null) {
                        response.setStatus(false);
                        response.setMessage("Giảng viên '" + classExcel.getUsernameTeacher() + "' không tồn tại");
                        return;
                    }
                    classFind.setTeacherId(giangVien.getId());
                } else {
                    classFind.setTeacherId(null);
                }
                listClassUpdate.put(classFind.getCode(), classFind);
            });
            List<LoggerResponse> loggerResponseList = new ArrayList<>();
            for (Map.Entry<String, Class> entry : listClassUpdate.entrySet()) {
                Class classNew = entry.getValue();
                Class classOld = mapClassOld.get(classNew.getCode());
                String teacherNew = classNew.getTeacherId();
                String teacherOld = classOld.getTeacherId();
                if (teacherOld == null && teacherNew != null) {
                    SimpleResponse teacherObj = mapGiangVienKeyId.get(teacherNew);
                    LoggerResponse loggerResponse = new LoggerResponse();
                    loggerResponse.setContent("Đã phân giảng viên cho lớp là: " + teacherObj.getUserName() + " - " + teacherObj.getName());
                    loggerResponse.setCodeClass(classNew.getCode());
                    loggerResponseList.add(loggerResponse);

                    Runnable emailTask = () -> {
                        String htmlBody = "Quản trị viên đã phân bạn dạy lớp " + classNew.getCode() + ". <br/> Vui lòng truy cập đường link sau để xác nhận thông tin: <p><a href=\"https://factory.udpm-hn.com/teacher/my-class\">Tại đây</a></p>";
                        emailSender.sendEmail(new String[]{teacherObj.getEmail()}, "[LAB-REPORT-APP] Thông báo phân công dạy học", "Thông báo phân công dạy học", htmlBody);
                    };
                    new Thread(emailTask).start();
                }
                if (teacherOld != null && teacherNew != null && !teacherOld.equals(teacherNew)) {
                    SimpleResponse teacherObjNew = mapGiangVienKeyId.get(teacherNew);
                    SimpleResponse teacherObjOld = mapGiangVienKeyId.get(teacherOld);
                    LoggerResponse loggerResponse = new LoggerResponse();
                    loggerResponse.setCodeClass(classNew.getCode());
                    loggerResponse.setContent("Đã cập nhật giảng viên của lớp từ " + teacherObjOld.getName()
                            + " - " + teacherObjOld.getUserName() + " thành "
                            + teacherObjNew.getName() + " - " + teacherObjNew.getUserName());
                    loggerResponseList.add(loggerResponse);

                    Runnable emailTask = () -> {
                        String htmlBody = "Quản trị viên đã phân bạn dạy lớp " + classNew.getCode() + ". <br/> Vui lòng truy cập đường link sau để xác nhận thông tin: <p><a href=\"https://factory.udpm-hn.com/teacher/my-class\">Tại đây</a></p>";
                        emailSender.sendEmail(new String[]{teacherObjNew.getEmail()}, "[LAB-REPORT-APP] Thông báo phân công dạy học", "Thông báo phân công dạy học", htmlBody);
                    };
                    new Thread(emailTask).start();
                }
                if (teacherOld != null && teacherNew == null) {
                    SimpleResponse teacherObjOld = mapGiangVienKeyId.get(teacherOld);
                    LoggerResponse loggerResponse = new LoggerResponse();
                    loggerResponse.setCodeClass(classNew.getCode());
                    loggerResponse.setContent("Đã cập nhật giảng viên của lớp từ " + teacherObjOld.getName()
                            + " - " + teacherObjOld.getUserName() + " thành "
                            + "không có");
                    loggerResponseList.add(loggerResponse);
                }
                String classPeriodNew = classNew.getClassPeriod();
                String classPeriodOld = classOld.getClassPeriod();
                if (classPeriodOld == null && classPeriodNew != null) {
                    MeetingPeriod classPeriodObjNew = mapMeetingPeriodKeyId.get(classPeriodNew);
                    LoggerResponse loggerResponse = new LoggerResponse();
                    loggerResponse.setCodeClass(classNew.getCode());
                    loggerResponse.setContent("Đã cập nhật ca học của lớp là: " + classPeriodObjNew.getName());
                    loggerResponseList.add(loggerResponse);
                }
                if (classPeriodOld != null && classPeriodNew != null && !classPeriodOld.equals(classPeriodNew)) {
                    MeetingPeriod classPeriodObjOld = mapMeetingPeriodKeyId.get(classPeriodOld);
                    MeetingPeriod classPeriodObjNew = mapMeetingPeriodKeyId.get(classPeriodNew);
                    LoggerResponse loggerResponse = new LoggerResponse();
                    loggerResponse.setCodeClass(classNew.getCode());
                    loggerResponse.setContent("Đã cập nhật ca học của lớp từ " + classPeriodObjOld.getName()
                            + " thành " + classPeriodObjNew.getName());
                    loggerResponseList.add(loggerResponse);
                }
                if (classPeriodOld != null && classPeriodNew == null) {
                    MeetingPeriod classPeriodObjOld = mapMeetingPeriodKeyId.get(classPeriodOld);
                    LoggerResponse loggerResponse = new LoggerResponse();
                    loggerResponse.setCodeClass(classNew.getCode());
                    loggerResponse.setContent("Đã cập nhật ca học của lớp từ " + classPeriodObjOld.getName()
                            + " thành 'không có'");
                    loggerResponseList.add(loggerResponse);
                }
            }
            if (response.getStatus()) {
                List<Class> listClass = repository.saveAll(listClassUpdate.values());
                String nameSemester = loggerUtil.getNameSemesterByIdClass(listClass.get(0).getId());
                for (Class xx : listClass) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Lớp: " + xx.getCode() + ": ");
                    Integer check = 0;
                    for (LoggerResponse logger : loggerResponseList) {
                        if (xx.getCode().equals(logger.getCodeClass())) {
                            stringBuilder.append(logger.getContent());
                            check++;
                        }
                    }
                    if (check > 0) {
                        loggerUtil.sendLogScreen(stringBuilder.toString(), nameSemester);
                        loggerUtil.sendLogStreamClass(stringBuilder.toString(), xx.getCode(), nameSemester);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(false);
            response.setMessage("Lỗi hệ thống");
            return response;
        }
        return response;
    }

    @Override
    public List<AdFindSelectClassCustom> listClass(AdFindClassRequest request) {
        List<AdFindSelectClassResponse> listRepo = repository.listClassFindIdActivityAndIdSemester(request);
        List<String> idTeacherList = listRepo.stream()
                .map(AdFindSelectClassResponse::getIdTeacher)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listSimple = callApiIdentity.handleCallApiGetListUserByListId(idTeacherList);
        List<AdFindSelectClassCustom> listCustom = new ArrayList<>();
        listRepo.forEach(repo -> {
            AdFindSelectClassCustom adFindSelectClassCustom = new AdFindSelectClassCustom();
            adFindSelectClassCustom.setId(repo.getId());
            adFindSelectClassCustom.setCode(repo.getCode());
            adFindSelectClassCustom.setTeacherId(repo.getIdTeacher());
            if (repo.getIdTeacher() != null) {
                listSimple.forEach(simple -> {
                    if (repo.getIdTeacher().equals(simple.getId())) {
                        adFindSelectClassCustom.setNameTeacher(simple.getName());
                        adFindSelectClassCustom.setUserNameTeacher(simple.getUserName());
                    }
                });
            }
            listCustom.add(adFindSelectClassCustom);
        });
        return listCustom;
    }

    @Override
    public TeDetailClassResponse findClassById(final String id) {
        Optional<TeDetailClassResponse> classCheck = teClassRepository.findClassById(id);
        if (!classCheck.isPresent()) {
            throw new NotFoundException(Message.CLASS_NOT_EXISTS);
        }
        return classCheck.get();
    }

    @Override
    public Boolean sendMailToStudent() {
        String idSemesterCurrent = semesterHelper.getSemesterCurrent();
        if (idSemesterCurrent == null) {
            throw new RestApiException(Message.CHUA_DEN_THOI_GIAN_CUA_HOC_KY);
        }
        List<SimpleResponse> listStudent = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_STUDENT);
        List<String> listEmailStudent = listStudent.stream()
                .map(SimpleResponse::getEmail)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        Runnable emailTask = () -> {
            String htmlBody =
                    "<p>Dear các bạn sinh viên,</p>" +
                            "<p>Hiện tại xưởng thực hành đã mở thêm lớp dạy ở các hoạt động. Xin mời các bạn sinh viên có nhu cầu tham gia ở lớp xưởng thực hành vào đường link sau để đăng ký.</p>" +
                            "<p><a href=\"https://factory.udpm-hn.com/student/register-class\">Tại đây</a></p>" +
                            "<br/>" +
                            "<p>Trân trọng,</p>" +
                            "<p>[LAB-REPORT-APP]</p>";

            emailSender.sendEmail(listEmailStudent.toArray(new String[0]), "[LAB-REPORT-APP] Thông báo mở lớp xưởng thực hành", " Thông báo mở lớp xưởng thực hành", htmlBody);
        };
        new Thread(emailTask).start();
        return true;
    }

    public void addDataInMapGiangVien(ConcurrentHashMap<String, SimpleResponse> mapAll, ConcurrentHashMap<String, SimpleResponse> mapAllKeyId) {
        List<SimpleResponse> giangVienHuongDanList = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_TEACHER);
        getALlPutMapGiangVien(mapAll, mapAllKeyId, giangVienHuongDanList);
    }

    public void getALlPutMapGiangVien(ConcurrentHashMap<String, SimpleResponse> mapSimple, ConcurrentHashMap<String, SimpleResponse> mapSimpleKeyId, List<SimpleResponse> listGiangVien) {
        for (SimpleResponse xx : listGiangVien) {
            mapSimple.put(xx.getUserName().toLowerCase(), xx);
            mapSimpleKeyId.put(xx.getId(), xx);
        }
    }

    public void addDataInMapClass(ConcurrentHashMap<String, Class> mapAll, ConcurrentHashMap<String, Class> mapAllOld, String idSemester) {
        List<Class> classList = repository.getAllClassEntity(idSemester);
        getALlPutMapClass(mapAll, mapAllOld, classList);
    }

    public void getALlPutMapClass(ConcurrentHashMap<String, Class> mapSimple, ConcurrentHashMap<String, Class> mapSimpleOld, List<Class> listClass) {
        for (Class xx : listClass) {
            mapSimple.put(xx.getCode(), xx);
            Class classNew = formUtils.convertToObject(Class.class, xx);
            mapSimpleOld.put(classNew.getCode(), classNew);
        }
    }

    public void addDataInMapMeetingPeriod(ConcurrentHashMap<String, MeetingPeriod> mapAll, ConcurrentHashMap<String, MeetingPeriod> mapAllKeyId) {
        List<MeetingPeriod> classList = adMeetingPeriodRepository.findAll();
        getALlPutMapMeetingPeriod(mapAll, mapAllKeyId, classList);
    }

    public void getALlPutMapMeetingPeriod(ConcurrentHashMap<String, MeetingPeriod> mapSimple, ConcurrentHashMap<String, MeetingPeriod> mapSimpleKeyId, List<MeetingPeriod> meetingPeriodList) {
        for (MeetingPeriod xx : meetingPeriodList) {
            mapSimple.put(xx.getName().toLowerCase(), xx);
            mapSimpleKeyId.put(xx.getId(), xx);
        }
    }
}

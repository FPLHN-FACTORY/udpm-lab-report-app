package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassSelectRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassSentStudentRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindClassStatisticalRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindUpdateStatusClassRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeClassResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeClassSentStudentRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeClassStatisticalResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailClassResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeFindClassSelectResponse;
import com.labreportapp.labreport.core.teacher.repository.TeActivityRepository;
import com.labreportapp.labreport.core.teacher.repository.TeClassConfigurationRepository;
import com.labreportapp.labreport.core.teacher.repository.TeClassRepository;
import com.labreportapp.labreport.core.teacher.service.TeClassService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.ClassConfiguration;
import com.labreportapp.labreport.infrastructure.constant.StatusClass;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.labreport.util.CompareUtil;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.labreport.util.SemesterHelper;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.NotFoundException;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author hieundph25894
 */
@Service
public class TeClassServiceImpl implements TeClassService {

    @Autowired
    private TeClassRepository teClassRepository;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private TeClassConfigurationRepository teClassConfigurationRepository;

    @Autowired
    private SemesterHelper semesterHelper;

    @Autowired
    private TeActivityRepository teActivityRepository;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private LoggerUtil loggerUtil;

    @Override
    public PageableObject<TeClassResponse> searchTeacherClass(final TeFindClassRequest teFindClass) {
        String idSemesterCurrent = semesterHelper.getSemesterCurrent();
        if (teFindClass.getIdSemester().equalsIgnoreCase("")) {
            if (idSemesterCurrent != null) {
                teFindClass.setIdSemester(idSemesterCurrent);
                teFindClass.setIdActivity("");
            } else {
                teFindClass.setIdSemester("");
            }
        }
        Pageable pageable = PageRequest.of(teFindClass.getPage() - 1, teFindClass.getSize());
        Page<TeClassResponse> pageList = teClassRepository.findClassBySemesterAndActivity(teFindClass, pageable);
        return new PageableObject<>(pageList);
    }

    @Override
    public PageableObject<TeClassSentStudentRespone> findClassBySentStudent(TeFindClassSentStudentRequest request) {
        ClassConfiguration classConfiguration = teClassConfigurationRepository.findAll().get(0);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        LocalDate currentDate = LocalDate.now();
        ZoneId vietnamZone = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime zonedDateTime = currentDate.atStartOfDay(vietnamZone);
        request.setDateNow(zonedDateTime.toLocalDate());
        Page<TeClassResponse> pageList = teClassRepository.findClassBySentStudent(request, pageable, classConfiguration.getClassSizeMax());
        List<TeClassResponse> listResponse = pageList.getContent();
        List<String> idUsers = listResponse.stream()
                .map(TeClassResponse::getTeacherId)
                .distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<SimpleResponse> listTeacher = callApiIdentity.handleCallApiGetListUserByListId(idUsers);
        Page<TeClassSentStudentRespone> pageNew = pageList.map(item -> {
            TeClassSentStudentRespone objNew = new TeClassSentStudentRespone();
            objNew.setStt(item.getStt());
            objNew.setId(item.getId());
            objNew.setCode(item.getCode());
            objNew.setIdTeacher(item.getTeacherId());
            if (item.getTeacherId() != null && listTeacher.size() > 0) {
                listTeacher.forEach(teacher -> {
                    if (teacher.getId().equals(item.getTeacherId())) {
                        objNew.setUsernameTeacher(teacher.getUserName());
                    }
                });
            } else {
                objNew.setUsernameTeacher("");
            }
            objNew.setClassSize(item.getClassSize());
            objNew.setClassPeriod(item.getClassPeriod());
            objNew.setStartHour(item.getStartHour());
            objNew.setStartMinute(item.getStartMinute());
            objNew.setEndHour(item.getEndHour());
            objNew.setEndMinute(item.getEndMinute());
            return objNew;
        });
        return new PageableObject<>(pageNew);
    }

    @Override
    public TeDetailClassResponse findClassById(final String id) {
        Optional<TeDetailClassResponse> classCheck = teClassRepository.findClassById(id);
        if (!classCheck.isPresent()) {
            throw new NotFoundException(Message.CLASS_NOT_EXISTS);
        }
        Optional<Class> classFind = teClassRepository.findById(id);
        if (!classFind.isPresent()) {
            throw new NotFoundException(Message.CLASS_NOT_EXISTS);
        }
        String idUserCurrent = labReportAppSession.getUserId();
        if (!idUserCurrent.equals(classFind.get().getTeacherId())) {
            throw new NotFoundException(Message.CLASS_NOT_EXISTS);
        }
        return classCheck.get();
    }

    @Override
    public List<TeClassResponse> getClassClosestToTheDateToSemester(String idTeacher) {
        List<TeClassResponse> list = teClassRepository.getClassClosestToTheDateToSemester(idTeacher);
        if (list.size() < 0) {
            throw new RestApiException(Message.CLASS_IS_EMPTY);
        }
        return list;
    }

    @Override
    @Transactional
    public Class updateStatusClass(TeFindUpdateStatusClassRequest request) {
        Optional<Class> classFind = teClassRepository.findById(request.getIdClass());
        if (!classFind.isPresent()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        Class classUp = classFind.get();
        String nameSemester = loggerUtil.getNameSemesterByIdClass(classFind.get().getId());
        String message = "";
        if (request.getStatus() == 0) {
            message = CompareUtil.compareAndConvertMessage("trạng thái của lớp"
                    , "\"Khóa\" ", "\"Mở\". ", "");
        } else {
            message = CompareUtil.compareAndConvertMessage("trạng thái của lớp"
                    , "\"Mở\" ", "\"Khóa\". ", "");
        }
        loggerUtil.sendLogStreamClass(message, classFind.get().getCode(), nameSemester);
        classUp.setStatusClass(request.getStatus() == 0 ? StatusClass.OPEN : StatusClass.LOCK);
        return teClassRepository.save(classUp);
    }

    @Override
    @Transactional
    public Class randomPassword(String idClass) {
        Optional<Class> classFind = teClassRepository.findById(idClass);
        if (!classFind.isPresent()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        String nameSemester = loggerUtil.getNameSemesterByIdClass(classFind.get().getId());
        String passNew = generateRandomPassword();
        String message = CompareUtil.compareAndConvertMessage("mật khẩu của lớp "
                , "\"" + classFind.get().getPassword() + "\"", "\"" + passNew + "\". ", "");
        loggerUtil.sendLogStreamClass(message, classFind.get().getCode(), nameSemester);
        Class classUp = classFind.get();
        classUp.setPassword(passNew);
        return teClassRepository.save(classUp);
    }

    @Override
    public PageableObject<TeClassStatisticalResponse> searchClassStatistical(TeFindClassStatisticalRequest request) {
        String idSemesterCurrent = semesterHelper.getSemesterCurrent();
        if (request.getIdSemester().equalsIgnoreCase("")) {
            if (idSemesterCurrent != null) {
                request.setIdSemester(idSemesterCurrent);
                request.setIdActivity("");
            } else {
                request.setIdSemester("");
            }
        }
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        Page<TeClassStatisticalResponse> pageList = teClassRepository.findClassStatistical(request, pageable);
        return new PageableObject<>(pageList);
    }

    @Override
    public List<TeFindClassSelectResponse> listClass(TeFindClassSelectRequest request) {
        return teClassRepository.listClassFindIdActivityAndIdSemester(request);
    }

    public String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            password.append(randomChar);
        }
        return password.toString();
    }

}

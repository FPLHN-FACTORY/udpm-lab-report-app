package com.labreportapp.labreport.core.student.service.impl;


import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.student.model.request.StClassRequest;
import com.labreportapp.labreport.core.student.model.request.StFindClassRequest;
import com.labreportapp.labreport.core.student.model.response.StClassCustomResponse;
import com.labreportapp.labreport.core.student.model.response.StClassResponse;
import com.labreportapp.labreport.core.student.repository.StClassConfigurationRepository;
import com.labreportapp.labreport.core.student.repository.StClassRepository;
import com.labreportapp.labreport.core.student.repository.StStudentClassesRepository;
import com.labreportapp.labreport.core.student.service.StClassService;
import com.labreportapp.labreport.core.teacher.model.response.TeDetailClassResponse;
import com.labreportapp.labreport.core.teacher.repository.TeClassRepository;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.infrastructure.constant.StatusStudentFeedBack;
import com.labreportapp.labreport.infrastructure.constant.StatusTeam;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.NotFoundException;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.transaction.Transactional;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StClassServiceImpl implements StClassService {

    @Autowired
    private StClassRepository stClassRepository;

    @Autowired
    private StStudentClassesRepository stStudentClassesRepository;

    @Autowired
    private StClassConfigurationRepository stClassConfigurationRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private TeClassRepository teClassRepository;

    @Override
    public PageableObject<StClassCustomResponse> getAllClassByCriteriaAndIsActive(final StFindClassRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        req.setStudentId(labReportAppSession.getUserId());
        Page<StClassResponse> getAllClassByCriteria = stClassRepository.getAllClassByCriteriaAndIsActive(req, pageable, Calendar.getInstance().getTimeInMillis());
        List<String> distinctTeacherIds = getAllClassByCriteria.getContent().stream()
                .map(StClassResponse::getIdTeacher)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listResponse = callApiIdentity.handleCallApiGetListUserByListId(distinctTeacherIds);
        List<StClassCustomResponse> responseClassCustom = new ArrayList<>();
        PageableObject<StClassCustomResponse> pageableResponse = new PageableObject<>();

        for (StClassResponse stClassResponse : getAllClassByCriteria.getContent()) {
            StClassCustomResponse stClassCustomResponse = new StClassCustomResponse();
            stClassCustomResponse.setId(stClassResponse.getId());
            stClassCustomResponse.setStt(stClassResponse.getStt());
            stClassCustomResponse.setClassCode(stClassResponse.getCode());
            stClassCustomResponse.setClassSize(stClassResponse.getClassSize());
            stClassCustomResponse.setClassPeriod(stClassResponse.getClassPeriod());
            stClassCustomResponse.setStartHour(stClassResponse.getStartHour());
            stClassCustomResponse.setStartMinute(stClassResponse.getStartMinute());
            stClassCustomResponse.setEndHour(stClassResponse.getEndHour());
            stClassCustomResponse.setEndMinute(stClassResponse.getEndMinute());
            stClassCustomResponse.setStartTime(stClassResponse.getStartTime());
            stClassCustomResponse.setLevel(stClassResponse.getLevel());
            stClassCustomResponse.setActivityName(stClassResponse.getActivityName());
            stClassCustomResponse.setDescriptions(stClassResponse.getDescriptions());
            stClassCustomResponse.setStartTimeStudent(stClassResponse.getStartTimeStudent());
            stClassCustomResponse.setEndTimeStudent(stClassResponse.getEndTimeStudent());
            stClassCustomResponse.setPassWord(stClassResponse.getPassWord());
            if (stClassResponse.getIdTeacher() != null) {
                for (SimpleResponse xx : listResponse) {
                    if (xx.getId().equals(stClassResponse.getIdTeacher())) {
                        stClassCustomResponse.setNameTeacher(xx.getName());
                        stClassCustomResponse.setUserNameTeacher(xx.getUserName());
                    }
                }
            }
            responseClassCustom.add(stClassCustomResponse);
        }

        pageableResponse.setData(responseClassCustom);
        pageableResponse.setCurrentPage(getAllClassByCriteria.getNumber());
        pageableResponse.setTotalPages(getAllClassByCriteria.getTotalPages());
        return pageableResponse;
    }

    @Override
    @Transactional
    @Synchronized
    public StClassCustomResponse joinClass(final StClassRequest req) {
        Optional<Class> findClass = stClassRepository.findById(req.getIdClass());
        if (!findClass.isPresent()) {
            throw new NotFoundException(Message.CLASS_NOT_EXISTS);
        }
        Optional<StClassResponse> conditionClass = stClassRepository.checkConditionCouldJoinOrLeaveClass(req, Calendar.getInstance().getTimeInMillis());
        if (!conditionClass.isPresent()) {
            throw new RestApiException(Message.YOU_CANNOT_ENTER_CLASS_YET);
        }
        Optional<StudentClasses> findStudentClasses = stStudentClassesRepository.
                findStudentClassesByClassIdAndStudentId(req.getIdClass(), labReportAppSession.getUserId());
        if (findStudentClasses.isPresent()) {
            throw new RestApiException(Message.YOU_HAD_IN_CLASS);
        }
        Integer configurationSizeMax = stClassConfigurationRepository.
                getClassConfiguration().getClassSizeMax();
        if (findClass.get().getClassSize().equals(configurationSizeMax)) {
            throw new RestApiException(Message.CLASS_DID_FULL_CLASS_SIZE);
        }
        System.err.println("aaaaa:" + req.getPassWord());
        if (findClass.get().getPassword() != null) {
            if (req.getPassWord().equals("") || req.getPassWord() == null) {
                throw new RestApiException(Message.CONFIRM_PASSWORD_ISEMPTY);
            } else if (!req.getPassWord().equals(findClass.get().getPassword())) {
                throw new RestApiException(Message.CONFIRM_PASSWORD_FAILED);
            }
        }
        SimpleResponse responseStudent = callApiIdentity.handleCallApiGetUserById(labReportAppSession.getUserId());
        StudentClasses studentJoinClass = new StudentClasses();
        StClassCustomResponse customResponse = new StClassCustomResponse();

        if (responseStudent != null) {
            studentJoinClass.setClassId(req.getIdClass());
            studentJoinClass.setEmail(responseStudent.getEmail());
            studentJoinClass.setStudentId(labReportAppSession.getUserId());
            studentJoinClass.setStatusStudentFeedBack(StatusStudentFeedBack.CHUA_FEEDBACK);
            studentJoinClass.setStatus(StatusTeam.INACTIVE);
            studentJoinClass.setCreatedDate(new Date().getTime());
            StudentClasses studentInClass = stStudentClassesRepository.save(studentJoinClass);

            if (studentInClass.getStudentId().equals(labReportAppSession.getUserId())) {
                Class classOfStudentWantJoin = findClass.get();
                classOfStudentWantJoin.setClassSize(classOfStudentWantJoin.getClassSize() + 1);
                Class updatedClass = stClassRepository.save(classOfStudentWantJoin);
                SimpleResponse simpleResponse = callApiIdentity.handleCallApiGetUserById(studentInClass.getStudentId());
                if (simpleResponse != null) {
                    StringBuilder message = new StringBuilder();
                    String nameSemester = loggerUtil.getNameSemesterByIdClass(req.getIdClass());
                    message.append("Sinh viên \"").append(simpleResponse.getName()).append(" - ")
                            .append(simpleResponse.getUserName()).append("\"").append(" đã <i style=\"color: red\">tham gia</i> lớp học")
                            .append(" và cập nhật sĩ số từ ").append(updatedClass.getClassSize() - 1)
                            .append(" thành ").append(updatedClass.getClassSize()).append(".");
                    loggerUtil.sendLogStreamClass(message.toString(), updatedClass.getCode(), nameSemester);
                }
                customResponse.setClassCode(updatedClass.getCode());
                customResponse.setClassSize(updatedClass.getClassSize());
            } else {
                stStudentClassesRepository.delete(studentInClass);
                throw new RestApiException(Message.ERROR_UNKNOWN);
            }
        }
        return customResponse;
    }

    @Override
    public TeDetailClassResponse findClassById(final String id) {
        Optional<TeDetailClassResponse> classCheck = teClassRepository.findClassById(id);
        if (!classCheck.isPresent()) {
            throw new NotFoundException(Message.CLASS_NOT_EXISTS);
        }
        String check = stClassRepository.checkStudentInClass(labReportAppSession.getUserId(), id);
        if (check == null) {
            throw new NotFoundException(Message.STUDENT_CLASSES_NOT_EXISTS);
        }
        return classCheck.get();
    }

}

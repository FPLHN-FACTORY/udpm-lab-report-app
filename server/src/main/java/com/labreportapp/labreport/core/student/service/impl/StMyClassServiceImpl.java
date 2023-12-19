package com.labreportapp.labreport.core.student.service.impl;

import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.student.model.request.StClassRequest;
import com.labreportapp.labreport.core.student.model.request.StFindClassRequest;
import com.labreportapp.labreport.core.student.model.response.StClassResponse;
import com.labreportapp.labreport.core.student.model.response.StMyClassCustom;
import com.labreportapp.labreport.core.student.model.response.StMyClassResponse;
import com.labreportapp.labreport.core.student.repository.StClassRepository;
import com.labreportapp.labreport.core.student.repository.StMeetingRepository;
import com.labreportapp.labreport.core.student.repository.StMyClassRepository;
import com.labreportapp.labreport.core.student.repository.StStudentClassesRepository;
import com.labreportapp.labreport.core.student.service.StMyClassService;
import com.labreportapp.labreport.entity.Class;
import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.infrastructure.constant.RoleTeam;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.labreport.repository.LevelRepository;
import com.labreportapp.labreport.repository.SemesterRepository;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.labreport.util.SemesterHelper;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author thangncph26123
 */
@Service
public class StMyClassServiceImpl implements StMyClassService {

    @Autowired
    private StMyClassRepository stMyClassRepository;

    @Autowired
    private StClassRepository stClassRepository;

    @Autowired
    private StStudentClassesRepository stStudentClassesRepository;

    @Autowired
    @Qualifier(SemesterRepository.NAME)
    private SemesterRepository semesterRepository;

    @Autowired
    @Qualifier(LevelRepository.NAME)
    private LevelRepository levelRepository;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private SemesterHelper semesterHelper;

    @Autowired
    private StMeetingRepository stMeetingRepository;

    @Autowired
    private LoggerUtil loggerUtil;

    @Override
    public List<StMyClassCustom> getAllClass(final StFindClassRequest req) {
        String idSemesterCurrent = semesterHelper.getSemesterCurrent();
        if (req.getSemesterId() == null) {
            if (idSemesterCurrent != null) {
                req.setSemesterId(idSemesterCurrent);
                req.setActivityId("");
            } else {
                req.setSemesterId("");
            }
        } else if (req.getSemesterId().equalsIgnoreCase("")) {
            if (idSemesterCurrent != null) {
                req.setSemesterId(idSemesterCurrent);
                req.setActivityId("");
            } else {
                req.setSemesterId("");
            }
        }
        List<StMyClassResponse> listMyClassResponse = stMyClassRepository.getAllClass(req);
        List<String> distinctTeacherIds = listMyClassResponse.stream()
                .map(StMyClassResponse::getTeacherId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listSimpleResponse = callApiIdentity.handleCallApiGetListUserByListId(distinctTeacherIds);
        List<StMyClassCustom> listCustom = new ArrayList<>();
        for (StMyClassResponse stMyClassResponse : listMyClassResponse) {
            StMyClassCustom stMyClassCustom = new StMyClassCustom();
            stMyClassCustom.setId(stMyClassResponse.getId());
            stMyClassCustom.setClassPeriod(stMyClassResponse.getClassPeriod());
            stMyClassCustom.setCode(stMyClassResponse.getCode());
            stMyClassCustom.setLevel(stMyClassResponse.getLevel());
            stMyClassCustom.setNameActivity(stMyClassResponse.getNameActivity());
            stMyClassCustom.setStartTime(stMyClassResponse.getStartTime());
            stMyClassCustom.setStt(stMyClassResponse.getStt());
            stMyClassCustom.setTeacherId(stMyClassResponse.getTeacherId());
            stMyClassCustom.setStartHour(stMyClassResponse.getStartHour());
            stMyClassCustom.setStartMinute(stMyClassResponse.getStartMinute());
            stMyClassCustom.setEndHour(stMyClassResponse.getEndHour());
            stMyClassCustom.setEndMinute(stMyClassResponse.getEndMinute());
            for (SimpleResponse simpleResponse : listSimpleResponse) {
                if (stMyClassResponse.getTeacherId() != null) {
                    if (stMyClassResponse.getTeacherId().equals(simpleResponse.getId())) {
                        stMyClassCustom.setUserNameTeacher(simpleResponse.getUserName());
                        stMyClassCustom.setNameTeacher(simpleResponse.getName());
                        break;
                    }
                }
            }
            listCustom.add(stMyClassCustom);
        }
        return listCustom;
    }

    @Override
    public List<SimpleResponse> getAllStudentClasses(String idClass) {
        List<String> idList = stMyClassRepository.getAllStudentClasses(idClass);
        List<SimpleResponse> listResponse = callApiIdentity.handleCallApiGetListUserByListId(idList);
        return listResponse;
    }

    @Override
    public void leaveClass(final StClassRequest req) {
        Optional<Class> findCurrentMyClass = stClassRepository.findById(req.getIdClass());
        if(!findCurrentMyClass.isPresent()) {
            throw new RestApiException(Message.CLASS_NOT_EXISTS);
        }
        Optional<StudentClasses> findStudentInClass = stStudentClassesRepository.
                findStudentClassesByClassIdAndStudentId(req.getIdClass(), labReportAppSession.getUserId());
        if (findStudentInClass.isPresent()) {
            Optional<StClassResponse> conditionClass = stClassRepository.checkConditionCouldJoinOrLeaveClass(req, Calendar.getInstance().getTimeInMillis());
            Integer countLessonMeeting = stMeetingRepository.countMeetingLessonByIdClass(new Date().getTime(), req.getIdClass());
            if (countLessonMeeting > 0) {
                throw new RestApiException("Lớp học đã diễn ra, không thể rời lớp học !");
            }
            if (conditionClass.isEmpty()) {
                throw new RestApiException(Message.YOU_DONT_LEAVE_CLASS);
            } else {
                stStudentClassesRepository.delete(findStudentInClass.get());
                findCurrentMyClass.get().setClassSize(findCurrentMyClass.get().getClassSize() - 1);
                Class classSave = stClassRepository.save(findCurrentMyClass.get());
                SimpleResponse simpleResponse = callApiIdentity.handleCallApiGetUserById(findStudentInClass.get().getStudentId());
                if (simpleResponse != null) {
                    StringBuilder message = new StringBuilder();
                    String nameSemester = loggerUtil.getNameSemesterByIdClass(req.getIdClass());
                    message.append("Sinh viên \"").append(simpleResponse.getName()).append(" - ")
                            .append(simpleResponse.getUserName()).append("\"").append(" đã <i style=\"color: red\">rời khỏi</i>  lớp học")
                            .append(" và cập nhật sĩ số từ ").append(classSave.getClassSize() - 1)
                            .append(" thành ").append(classSave.getClassSize()).append(".");
                    loggerUtil.sendLogStreamClass(message.toString(), conditionClass.get().getCode(), nameSemester);
                }
            }
        }
    }

    @Override
    public List<SimpleEntityProjection> getAllSimpleEntityProj() {
        return levelRepository.getAllSimpleEntityProjection();
    }
}

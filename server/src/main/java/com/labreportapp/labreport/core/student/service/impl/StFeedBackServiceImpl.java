package com.labreportapp.labreport.core.student.service.impl;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.student.model.request.StCheckFeedBackRequest;
import com.labreportapp.labreport.core.student.model.request.StFindClassRequest;
import com.labreportapp.labreport.core.student.model.request.StStudentFeedBackRequest;
import com.labreportapp.labreport.core.student.model.request.StStudentListFeedBackRequest;
import com.labreportapp.labreport.core.student.model.response.StCheckFeedBackResponse;
import com.labreportapp.labreport.core.student.model.response.StMyClassCustom;
import com.labreportapp.labreport.core.student.model.response.StMyClassResponse;
import com.labreportapp.labreport.core.student.repository.StFeedBackRepository;
import com.labreportapp.labreport.core.student.service.StFeedBackService;
import com.labreportapp.labreport.entity.FeedBack;
import com.labreportapp.labreport.entity.Semester;
import com.labreportapp.labreport.entity.StudentClasses;
import com.labreportapp.labreport.infrastructure.constant.StatusFeedBack;
import com.labreportapp.labreport.infrastructure.constant.StatusStudentFeedBack;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.labreport.repository.SemesterRepository;
import com.labreportapp.labreport.repository.StudentClassesRepository;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import com.labreportapp.labreport.util.SemesterHelper;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class StFeedBackServiceImpl implements StFeedBackService {

    @Autowired
    private StFeedBackRepository stFeedBackRepository;

    @Autowired
    private SemesterHelper semesterHelper;

    @Autowired
    @Qualifier(SemesterRepository.NAME)
    private SemesterRepository semesterRepository;

    @Autowired
    @Qualifier(StudentClassesRepository.NAME)
    private StudentClassesRepository studentClassesRepository;

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Override
    public Boolean checkFeedBack() {
        String idSemesterCurrent = semesterHelper.getSemesterCurrent();
        if (idSemesterCurrent == null) {
            return false;
        }
        Optional<Semester> semesterFind = semesterRepository.findById(idSemesterCurrent);
        if (!semesterFind.isPresent()) {
            throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
        }
        if (semesterFind.get().getStatusFeedBack() == StatusFeedBack.DA_FEEDBACK) {
            List<StCheckFeedBackResponse> listStudentClasses = stFeedBackRepository.getStudentClassesByIdStudent(labReportAppSession.getUserId(), idSemesterCurrent);
            if (listStudentClasses == null || listStudentClasses.size() == 0) {
                return false;
            }
            for (StCheckFeedBackResponse studentClasses : listStudentClasses) {
                if (studentClasses.getStatusStudentFeedBack() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean createFeedBack(@Valid StStudentListFeedBackRequest request) {
        String idSemesterCurrent = semesterHelper.getSemesterCurrent();
        if (idSemesterCurrent == null) {
            return false;
        }
        Optional<Semester> semesterFind = semesterRepository.findById(idSemesterCurrent);
        if (!semesterFind.isPresent()) {
            throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
        }
        List<StCheckFeedBackResponse> listStudentClasses = stFeedBackRepository.getStudentClassesByIdStudent(labReportAppSession.getUserId(), idSemesterCurrent);
        List<String> distinctStudentClassesIds = listStudentClasses.stream()
                .map(StCheckFeedBackResponse::getId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<StudentClasses> listStudentClassesFind = studentClassesRepository.findAllById(distinctStudentClassesIds);
        for (StudentClasses studentClasses : listStudentClassesFind) {
            studentClasses.setStatusStudentFeedBack(StatusStudentFeedBack.DA_FEEDBACK);
        }
        studentClassesRepository.saveAll(listStudentClassesFind);
        List<StStudentFeedBackRequest> listFeedBack = request.getListFeedBack();
        List<FeedBack> listFeedBackNew = new ArrayList<>();
        for (StStudentFeedBackRequest studentFeedBackRequest : listFeedBack) {
            FeedBack feedBack = new FeedBack();
            feedBack.setStudentId(labReportAppSession.getUserId());
            feedBack.setClassId(studentFeedBackRequest.getClassId());
            feedBack.setDescriptions(studentFeedBackRequest.getDescriptions());
            listFeedBackNew.add(feedBack);
        }
        stFeedBackRepository.saveAll(listFeedBackNew);
        return true;
    }

    @Override
    public Semester getSemesterCurrent() {
        String idSemesterCurrent = semesterHelper.getSemesterCurrent();
        return semesterRepository.findById(idSemesterCurrent).get();
    }

    @Override
    public List<StMyClassCustom> getAllClass(final StFindClassRequest req) {
        req.setStudentId(labReportAppSession.getUserId());
        List<StMyClassResponse> listMyClassResponse = stFeedBackRepository.getAllClass(req);
        List<String> distinctTeacherIds = listMyClassResponse.stream()
                .map(StMyClassResponse::getTeacherId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listSimpleResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(distinctTeacherIds);
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
}

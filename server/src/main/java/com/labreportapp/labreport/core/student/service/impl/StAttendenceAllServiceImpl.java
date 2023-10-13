package com.labreportapp.labreport.core.student.service.impl;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.student.model.request.StFindAttendenceAllRequest;
import com.labreportapp.labreport.core.student.model.request.StFindClassRequest;
import com.labreportapp.labreport.core.student.model.response.StAttendenceAllCustomResponse;
import com.labreportapp.labreport.core.student.model.response.StAttendenceAllResponse;
import com.labreportapp.labreport.core.student.model.response.StClassAttendenceAllCustomResponse;
import com.labreportapp.labreport.core.student.model.response.StMyClassResponse;
import com.labreportapp.labreport.core.student.repository.StAttendenceAllRepository;
import com.labreportapp.labreport.core.student.repository.StMyClassRepository;
import com.labreportapp.labreport.core.student.service.StAttendenceAllService;
import com.labreportapp.labreport.infrastructure.apiconstant.ActorConstants;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StAttendenceAllServiceImpl implements StAttendenceAllService {

    @Autowired
    private StMyClassRepository stMyClassRepository;

    @Autowired
    private StAttendenceAllRepository stAttendenceAllRepository;

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Override
    public List<StClassAttendenceAllCustomResponse> getClassAttendenceListByStudentInClassAndSemester(StFindAttendenceAllRequest req) {
        req.setIdStudent(labReportAppSession.getUserId());
        StFindClassRequest stFindClassRequest = new StFindClassRequest();
        stFindClassRequest.setSemesterId(req.getIdSemester());
        stFindClassRequest.setStudentId(req.getIdStudent());

        List<StMyClassResponse> getClassListByStudentInSemester = stMyClassRepository.getAllClass(stFindClassRequest);
        List<StClassAttendenceAllCustomResponse> response = new ArrayList<>();

        List<SimpleResponse> simplesResponse = convertRequestCallApiIdentity.
                handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_TEACHER);

        Map<String, SimpleResponse> simpleMap = simplesResponse.stream()
                .collect(Collectors.toMap(SimpleResponse::getId, Function.identity()));


        for (StMyClassResponse classResponse : getClassListByStudentInSemester) {
            StClassAttendenceAllCustomResponse classAttendenceResponse = new StClassAttendenceAllCustomResponse();
            classAttendenceResponse.setId(classResponse.getId());
            classAttendenceResponse.setClassCode(classResponse.getCode());

            req.setIdClass(classAttendenceResponse.getId());
            List<StAttendenceAllResponse> getAttendenceListByStudentInClassAndSemester = stAttendenceAllRepository
                    .getAttendenceListByStudentInClassAndSemester(req);

            List<StAttendenceAllCustomResponse> customAttendenceListResponses = new ArrayList<>();
            getAttendenceListByStudentInClassAndSemester.forEach(att -> {
                        StAttendenceAllCustomResponse attendenceInClass = new StAttendenceAllCustomResponse();
                        attendenceInClass.setStt(att.getStt());
                        attendenceInClass.setStatus(att.getStatus());
                        attendenceInClass.setMeetingDate(att.getMeetingDate());
                        attendenceInClass.setMeetingPeriod(att.getMeetingPeriod());
                        attendenceInClass.setTypeMeeting(att.getTypeMeeting());
                        attendenceInClass.setName(att.getName());
                        attendenceInClass.setTeacherId(att.getTeacherId());
                        attendenceInClass.setStartHour(att.getStartHour());
                        attendenceInClass.setStartMinute(att.getStartMinute());
                        attendenceInClass.setEndHour(att.getEndHour());
                        attendenceInClass.setEndMinute(att.getEndMinute());
                        SimpleResponse simpleResponse = simpleMap.get(att.getTeacherId());
                        attendenceInClass.setTeacherUsername(att.getTeacherId().equals(simpleResponse.getId())
                                ? simpleResponse.getUserName() : null);
                        customAttendenceListResponses.add(attendenceInClass);
                    }
            );
            classAttendenceResponse.setAttendences(customAttendenceListResponses);
            response.add(classAttendenceResponse);
        }
        return response;
    }
}

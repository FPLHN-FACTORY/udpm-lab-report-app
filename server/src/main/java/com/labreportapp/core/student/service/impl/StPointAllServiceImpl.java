package com.labreportapp.core.student.service.impl;

import com.labreportapp.core.student.model.request.StFindClassRequest;
import com.labreportapp.core.student.model.request.StFindPointAllRequest;
import com.labreportapp.core.student.model.response.StMyClassResponse;
import com.labreportapp.core.student.model.response.StPointAllCustomRespone;
import com.labreportapp.core.student.model.response.StPointAllRespone;
import com.labreportapp.core.student.model.response.StPointCustomResponse;
import com.labreportapp.core.student.repository.StMyClassRepository;
import com.labreportapp.core.student.repository.StPointAllRepository;
import com.labreportapp.core.student.service.StPointAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StPointAllServiceImpl implements StPointAllService {
    @Autowired
    private StPointAllRepository stPointAllRepository;

    @Autowired
    private StMyClassRepository stMyClassRepository;

    @Override
    public List<StPointAllCustomRespone> getClassPointListByStudentInClassAndSemester(StFindPointAllRequest req) {
        StFindClassRequest stFindClassRequest = new StFindClassRequest();
        stFindClassRequest.setSemesterId(req.getIdSemester());
        stFindClassRequest.setStudentId(req.getIdStudent());
        List<StMyClassResponse> getClassListByStudentInSemester = stMyClassRepository.getAllClass(stFindClassRequest);

        List<StPointAllCustomRespone> pointsResponse = new ArrayList<>();

        for (StMyClassResponse classResponse : getClassListByStudentInSemester) {
            StPointAllCustomRespone pointResponse = new StPointAllCustomRespone();
            pointResponse.setId(classResponse.getId());
            pointResponse.setClassCode(classResponse.getCode());

            req.setIdClass(pointResponse.getId());
            List<StPointAllRespone> getPointListByStudentInClassAndSemester = stPointAllRepository.getPointListByStudentInClassAndSemester(req);

            pointResponse.setPoints(new ArrayList<>());

            for (int i = 0; i < getPointListByStudentInClassAndSemester.size(); i++) {
                StPointAllRespone point = getPointListByStudentInClassAndSemester.get(i);
                if (point != null) {
                    pointResponse.getPoints().add(new StPointCustomResponse(1, "Điểm giai đoạn 1", 0, point.getCheckPointPhase1(), ""));
                    pointResponse.getPoints().add(new StPointCustomResponse(2, "Điểm giai đoạn 2", 0, point.getCheckPointPhase2(), ""));
                    pointResponse.getPoints().add(new StPointCustomResponse(3, "Điểm final", 0, point.getCheckPointPhase2(), ""));
                }
            }

            pointsResponse.add(pointResponse);
        }
        return pointsResponse;
    }
}
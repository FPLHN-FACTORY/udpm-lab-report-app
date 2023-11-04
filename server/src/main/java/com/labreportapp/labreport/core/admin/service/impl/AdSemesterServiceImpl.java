package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdCreateSemesterRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindSemesterRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateSemesterRequest;
import com.labreportapp.labreport.core.admin.model.response.AdSemesterResponse;
import com.labreportapp.labreport.core.admin.repository.AdSemesterRepository;
import com.labreportapp.labreport.core.admin.service.AdSemesterService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.entity.Semester;
import com.labreportapp.labreport.infrastructure.constant.StatusFeedBack;
import com.labreportapp.labreport.util.FormUtils;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdSemesterServiceImpl implements AdSemesterService {

    @Autowired
    private AdSemesterRepository adSemesterRepository;

    private FormUtils formUtils = new FormUtils();

    private List<AdSemesterResponse> adSemesterResponseList;

    @Override
    public List<Semester> findAllSermester(Pageable pageable) {
        return adSemesterRepository.getAllSemester(pageable);
    }

    @Override
    public Semester createSermester(@Valid AdCreateSemesterRequest obj) {
        Semester semester = formUtils.convertToObject(Semester.class, obj);
        List<Semester> semesterList = adSemesterRepository.findAllSemester();
        for (Semester semester1 : semesterList) {
            if (obj.getStartTime() < semester1.getEndTime() && obj.getEndTime() > semester1.getStartTime()) {
                throw new RestApiException(Message.TIME_SEMESTER_OVERLOAD);
            }
            if (obj.getStartTimeStudent() < obj.getStartTime() ||
                    obj.getEndTimeStudent() > obj.getEndTime() ||
                    obj.getStartTimeStudent() > obj.getEndTimeStudent()) {
                throw new RestApiException(Message.TIME_STUDENT_SEMESTER_OVERLOAD);
            }
        }
        semester.setStatusFeedBack(StatusFeedBack.CHUA_FEEDBACK);
        return adSemesterRepository.save(semester);
    }

    @Override
    public Semester updateSermester(@Valid AdUpdateSemesterRequest obj) {
        Optional<Semester> findCategoryById = adSemesterRepository.findById(obj.getId());
        if (!findCategoryById.isPresent()) {
            throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
        }
        List<Semester> semesterList = adSemesterRepository.findAllSemester();
        for (Semester semester1 : semesterList) {
            if (semester1.getId().equals(obj.getId())) {
                continue;
            }
            if (obj.getStartTime() < semester1.getEndTime() && obj.getEndTime() > semester1.getStartTime()) {
                throw new RestApiException(Message.TIME_SEMESTER_OVERLOAD);
            }
//            if(obj.getStartTime() < semester1.getEndTime() && obj.getStartTime() > semester1.getStartTime()){
//                throw new RestApiException(Message.TIME_SEMESTER_OVERLOAD);
//            }
//            if(obj.getEndTime() < semester1.getEndTime() && obj.getEndTime() > semester1.getStartTime()){
//                throw new RestApiException(Message.TIME_SEMESTER_OVERLOAD);
//            }
            if (obj.getStartTimeStudent() < obj.getStartTime() ||
                    obj.getEndTimeStudent() > obj.getEndTime() ||
                    obj.getStartTimeStudent() > obj.getEndTimeStudent()) {
                throw new RestApiException(Message.TIME_STUDENT_SEMESTER_OVERLOAD);
            }
        }
        Semester semester = findCategoryById.get();
        semester.setName(obj.getName());
        semester.setStartTime(obj.getStartTime());
        semester.setEndTime(obj.getEndTime());
        semester.setStartTimeStudent(obj.getStartTimeStudent());
        semester.setEndTimeStudent(obj.getEndTimeStudent());
        return adSemesterRepository.save(semester);
    }

    @Override
    public PageableObject<AdSemesterResponse> searchSemester(AdFindSemesterRequest rep) {
        Pageable pageable = PageRequest.of(rep.getPage() - 1, rep.getSize());
        Page<AdSemesterResponse> adSemesterResponses = adSemesterRepository.searchSemester(rep, pageable);
        adSemesterResponseList = adSemesterResponses.stream().toList();
        return new PageableObject<>(adSemesterResponses);
    }

    @Override
    public Semester findSemesterById(String id) {
        Optional<Semester> findSemester = adSemesterRepository.findById(id);
        if (!findSemester.isPresent()) {
            throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
        }
        return findSemester.get();
    }

    @Override
    public Boolean deleteSemester(String id) {
        Optional<Semester> findSemesterById = adSemesterRepository.findById(id);
        if (!findSemesterById.isPresent()) {
            throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
        }
        Integer countActivities = adSemesterRepository.countActivitiesBySemesterId(id);
        if (countActivities > 0) {
            System.out.println(countActivities);
            throw new RestApiException(Message.SEMESTER_ACTIVITY_ALREADY_EXISTS);
        }
        adSemesterRepository.delete(findSemesterById.get());
        return true;
    }

    @Override
    public Boolean updateStatusFeedback(String id) {
        Optional<Semester> findSemesterById = adSemesterRepository.findById(id);
        if (!findSemesterById.isPresent()) {
            throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
        }
        findSemesterById.get().setStatusFeedBack(StatusFeedBack.DA_FEEDBACK);
        adSemesterRepository.save(findSemesterById.get());
        return true;
    }

    @Override
    public List<AdSemesterResponse> getAllSemesters() {
        return adSemesterRepository.getAllSemesters();
    }
}

package com.labreportapp.core.admin.service.impl;

import com.labreportapp.core.admin.model.request.AdCreateSemesterRequest;
import com.labreportapp.core.admin.model.request.AdFindSemesterRequest;
import com.labreportapp.core.admin.model.request.AdUpdateSemesterRequest;
import com.labreportapp.core.admin.model.response.AdSemesterResponse;
import com.labreportapp.core.admin.repository.AdSemesterRepository;
import com.labreportapp.core.admin.service.AdSemesterService;
import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.entity.Activity;
import com.labreportapp.entity.Semester;
import com.labreportapp.infrastructure.constant.Message;
import com.labreportapp.infrastructure.exception.rest.RestApiException;
import com.labreportapp.util.FormUtils;
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
    public Semester createSermester(AdCreateSemesterRequest obj) {
        Semester semester = formUtils.convertToObject(Semester.class, obj);
        return adSemesterRepository.save(semester);
    }

    @Override
    public Semester updateSermester(AdUpdateSemesterRequest obj) {
        Optional<Semester> findCategoryById = adSemesterRepository.findById(obj.getId());
        if (!findCategoryById.isPresent()) {
            throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
        }
        Semester semester = findCategoryById.get();
        semester.setName(obj.getName());
        semester.setStartTime(obj.getStartTime());
        semester.setEndTime(obj.getEndTime());
        return adSemesterRepository.save(semester);
    }

    @Override
    public PageableObject<AdSemesterResponse> searchSemester(AdFindSemesterRequest rep) {
        Pageable pageable = PageRequest.of(rep.getPage()-1, rep.getSize());
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
        Integer countActivities = adSemesterRepository.countActivitiesBySemesterId(id);

        if (!findSemesterById.isPresent()) {
            throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
        }

        if (countActivities > 0){
            System.out.println(countActivities);
            throw new RestApiException(Message.SEMESTER_ACTIVITY_ALREADY_EXISTS);
        }
        adSemesterRepository.delete(findSemesterById.get());
        return true;
    }
}

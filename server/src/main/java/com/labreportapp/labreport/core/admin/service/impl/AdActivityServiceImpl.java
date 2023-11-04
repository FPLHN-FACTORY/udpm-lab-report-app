package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdCreatActivityRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindActivityRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateActivityRequest;
import com.labreportapp.labreport.core.admin.model.response.AdActivityLevelResponse;
import com.labreportapp.labreport.core.admin.model.response.AdActivityResponse;
import com.labreportapp.labreport.core.admin.model.response.AdGetActivityResponse;
import com.labreportapp.labreport.core.admin.repository.AdActivityRepository;
import com.labreportapp.labreport.core.admin.repository.AdSemesterRepository;
import com.labreportapp.labreport.core.admin.service.AdActivityService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.entity.Activity;
import com.labreportapp.labreport.entity.Semester;
import com.labreportapp.labreport.infrastructure.constant.AllowUseTrello;
import com.labreportapp.labreport.util.SemesterHelper;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class AdActivityServiceImpl implements AdActivityService {

    @Autowired
    private AdActivityRepository adActivityRepository;

    @Autowired
    private AdSemesterRepository adSemesterRepository;

    @Autowired
    private SemesterHelper semesterHelper;

    @Override
    public PageableObject<AdActivityResponse> searchActivity(final AdFindActivityRequest rep) {
        String idSemesterCurrent = semesterHelper.getSemesterCurrent();
        if (rep.getSemesterId() == null) {
            if (idSemesterCurrent != null) {
                rep.setSemesterId(idSemesterCurrent);
            } else {
                rep.setSemesterId("");
            }
        } else if (rep.getSemesterId().equalsIgnoreCase("")) {
            if (idSemesterCurrent != null) {
                rep.setSemesterId(idSemesterCurrent);
            } else {
                rep.setSemesterId("");
            }
        }
        Pageable pageable = PageRequest.of(rep.getPage(), rep.getSize());
        var responses = adActivityRepository.findByNameActivity(rep, pageable);
        return new PageableObject<>(responses);
    }

    @Override
    public Activity creatActivity(@Valid AdCreatActivityRequest command) {
        String name = adActivityRepository.getMaActivity(command.getName(), command.getSemesterId());
        if (name != null) {
            throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
        }
        String code = adActivityRepository.findActivtyByCode(command.getCode(), command.getSemesterId());
        if (code != null) {
            throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
        }
        Activity activity = new Activity();
        activity.setName(command.getName());
        activity.setCode(command.getCode());
        if (!command.getStartTime().equals("") && !command.getEndTime().equals("")) {
            Long startTime = convertDateToString(command.getStartTime());
            Long endTime = convertDateToString(command.getEndTime());

            activity.setStartTime(startTime);
            activity.setEndTime(endTime);
        }
        activity.setLevelId(command.getLevel());
        activity.setSemesterId(command.getSemesterId());
        activity.setDescriptions(command.getDescriptions());
        activity.setAllowUseTrello(AllowUseTrello.values()[command.getAllowUseTrello()]);
        return adActivityRepository.save(activity);
    }

    @Override
    public Activity updateActivity(@Valid AdUpdateActivityRequest command) {
        Optional<Activity> optional = adActivityRepository.findById(command.getId());
        if (!optional.isPresent()) {
            throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
        }
        String name = adActivityRepository.getMaActivityUpdate(command.getName(), command.getId(), command.getSemesterId());
        if (name != null) {
            throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
        }
        String code = adActivityRepository.findActivtyByCodeUpdate(command.getCode(), command.getId(), command.getSemesterId());
        if (code != null) {
            throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
        }
        Activity activity = optional.get();
        activity.setName(command.getName());
        activity.setCode(command.getCode());
        if (!command.getStartTime().equals("") && !command.getEndTime().equals("")) {
            Long startTime = convertDateToString(command.getStartTime());
            Long endTime = convertDateToString(command.getEndTime());

            activity.setStartTime(startTime);
            activity.setEndTime(endTime);
        }
        activity.setLevelId(command.getLevel());
        activity.setSemesterId(command.getSemesterId());
        activity.setDescriptions(command.getDescriptions());
        activity.setAllowUseTrello(AllowUseTrello.values()[command.getAllowUseTrello()]);
        return adActivityRepository.save(activity);
    }

    @Override
    public String deleteActivity(String id) {
        Optional<Activity> optional = adActivityRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
        }
        Integer countClass = adActivityRepository.countClassInActivity(id);
        if (countClass != null && countClass > 0) {
            throw new RestApiException(Message.ACTIVITY_HAVE_CLASS);
        }
        adActivityRepository.delete(optional.get());
        return id;
    }

    @Override
    public Activity getOneByIdActivity(String id) {
        Optional<Activity> optional = adActivityRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
        }
        return optional.get();
    }

    @Override
    public List<String> getAllIdByStatus(String status) {
        List<String> list = adActivityRepository.getAllIdByStatus(status);
        return list;
    }

    @Override
    public List<Semester> getSemester() {
        return adSemesterRepository.findAllSemester();
    }

    @Override
    public List<AdActivityLevelResponse> getLevel() {
        return adActivityRepository.getAllLevel();
    }

    @Override
    public List<AdGetActivityResponse> getAllByIdSemesters(AdFindActivityRequest adFindClass) {
        return adActivityRepository.getAllByIdSemester(adFindClass);
    }

    public Long convertDateToString(String dateStringToLong) {

        String pattern = "yyyy-MM-dd";

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            Date date = dateFormat.parse(dateStringToLong);
            long timeInMillis = date.getTime();
            return timeInMillis;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}


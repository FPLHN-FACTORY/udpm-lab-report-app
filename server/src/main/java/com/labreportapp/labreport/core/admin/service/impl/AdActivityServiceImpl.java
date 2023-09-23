package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdCreatActivityRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindActivityRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateActivityRequest;
import com.labreportapp.labreport.core.admin.model.response.AdActivityResponse;
import com.labreportapp.labreport.core.admin.model.response.AdActivityLevelResponse;
import com.labreportapp.labreport.core.admin.repository.AdActivityRepository;
import com.labreportapp.labreport.core.admin.repository.AdSemesterRepository;
import com.labreportapp.labreport.core.admin.service.AdActivityService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.entity.Activity;
import com.labreportapp.labreport.entity.Semester;
import com.labreportapp.labreport.infrastructure.constant.AllowUseTrello;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Override
    public PageableObject<AdActivityResponse> searchActivity(final AdFindActivityRequest rep) {
        Pageable pageable = PageRequest.of(rep.getPage(), rep.getSize());
//        if (rep.getSemesterId().equals("")) {
//            List<Semester> listSemester = adSemesterRepository.findAllSemester();
//            rep.setSemesterId(listSemester.get(0).getId());
//        }
        Page<AdActivityResponse> responses = adActivityRepository.findByNameActivity(rep, pageable);
        return new PageableObject<>(responses);
    }

    @Override
    public Activity creatActivity(@Valid AdCreatActivityRequest command) {
        String name = adActivityRepository.getMaActivity(command.getName());
        List<AdActivityLevelResponse> listLevel = adActivityRepository.getAllLevel();
        if (name != null) {
            throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
        }
        Activity activity = new Activity();
        activity.setName(command.getName());
        activity.setCode(command.getCode());
        if (listLevel != null) {
            listLevel.forEach(level -> {
                if (command.getLevel().equals(level.getId())) {
                    if (level.getName().equalsIgnoreCase("Level 3")) {
                        activity.setAllowUseTrello(AllowUseTrello.CHO_PHEP);
                    } else {
                        activity.setAllowUseTrello(AllowUseTrello.KHONG_CHO_PHEP);
                    }
                }
            });
        }
        if (!command.getStartTime().equals("") && !command.getEndTime().equals("")) {
            Long startTime = convertDateToString(command.getStartTime());
            Long endTime = convertDateToString(command.getEndTime());

            activity.setStartTime(startTime);
            activity.setEndTime(endTime);
        }
        activity.setLevelId(command.getLevel());
        activity.setSemesterId(command.getSemesterId());
        activity.setDescriptions(command.getDescriptions());

        return adActivityRepository.save(activity);
    }

    @Override
    public Activity updateActivity(@Valid AdUpdateActivityRequest command) {
        Optional<Activity> optional = adActivityRepository.findById(command.getId());
        List<AdActivityLevelResponse> listLevel = adActivityRepository.getAllLevel();
        if (!optional.isPresent()) {
            throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
        }
        Activity activity = optional.get();
        activity.setName(command.getName());
        activity.setCode(command.getCode());
        if (listLevel != null) {
            listLevel.forEach(level -> {
                if (command.getLevel().equals(level.getId())) {
                    if (level.getName().equalsIgnoreCase("Level 3")) {
                        activity.setAllowUseTrello(AllowUseTrello.CHO_PHEP);
                    } else {
                        activity.setAllowUseTrello(AllowUseTrello.KHONG_CHO_PHEP);
                    }
                }
            });
        }
        if (!command.getStartTime().equals("") && !command.getEndTime().equals("")) {
            Long startTime = convertDateToString(command.getStartTime());
            Long endTime = convertDateToString(command.getEndTime());

            activity.setStartTime(startTime);
            activity.setEndTime(endTime);
        }
        activity.setLevelId(command.getLevel());
        activity.setSemesterId(command.getSemesterId());
        activity.setDescriptions(command.getDescriptions());
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

    public Long convertDateToString(String dateStringToLong) {

        String pattern = "yyyy-MM-dd"; // Định dạng của chuỗi ngày

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            Date date = dateFormat.parse(dateStringToLong); // Chuyển đổi chuỗi ngày thành đối tượng Date
            long timeInMillis = date.getTime(); // Lấy giá trị thời gian dưới dạng milliseconds
            return timeInMillis;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}


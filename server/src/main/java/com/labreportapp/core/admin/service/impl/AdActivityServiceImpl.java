package com.labreportapp.core.admin.service.impl;

import com.labreportapp.core.admin.model.request.AdCreatActivityRequest;
import com.labreportapp.core.admin.model.request.AdFindActivityRequest;
import com.labreportapp.core.admin.model.request.AdUpdateActivityRequest;
import com.labreportapp.core.admin.model.response.AdActivityResponse;
import com.labreportapp.core.admin.repository.AdActivityRepository;
import com.labreportapp.core.admin.repository.AdSemesterRepository;

import com.labreportapp.core.common.base.PageableObject;
import com.labreportapp.entity.Activity;
import com.labreportapp.entity.Semester;
import com.labreportapp.infrastructure.constant.Level;
import com.labreportapp.infrastructure.constant.Message;
import com.labreportapp.infrastructure.exception.rest.RestApiException;
import com.labreportapp.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdActivityServiceImpl implements AdActivityService {

    @Autowired
    private AdActivityRepository adActivityRepository;

    private FormUtils formUtils = new FormUtils();

    private List<AdActivityResponse> listActivity;

    @Autowired
    private AdSemesterRepository adSemesterRepository;

    @Override
    public PageableObject<AdActivityResponse> searchActivity(AdFindActivityRequest rep) {
        Pageable pageable = PageRequest.of(rep.getPage(), rep.getSize());
        Page<AdActivityResponse> responses = adActivityRepository.findByNameActivity(rep, pageable);
        listActivity = responses.stream().toList();
        return new PageableObject<>(responses);
    }

    @Override
    public Activity creatActivity(AdCreatActivityRequest command) {
        String name = adActivityRepository.getMaActivity(command.getName());
        if(name != null) {
            throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
        }
//        Activity activity = formUtils.convertToObject(Activity.class, command);
        Activity activity = new Activity();
        activity.setName(command.getName());
        if (command.getLevel().equals("0")){
            activity.setLevel(Level.LEVEL_1);
        }
        if (command.getLevel().equals("1")){
            activity.setLevel(Level.LEVEL_2);
        }
        if (command.getLevel().equals("2")){
            activity.setLevel(Level.LEVEL_3);
        }
        if (!command.getStartTime().equals("") && !command.getEndTime().equals("")){
            Long startTime = convertDateToString(command.getStartTime());
            Long endTime = convertDateToString(command.getEndTime());
            activity.setStartTime(startTime);
            activity.setEndTime(endTime);
        }
        activity.setSemesterId(command.getSemesterId());

        return adActivityRepository.save(activity);
    }

    @Override
    public Activity updateActivity(AdUpdateActivityRequest command) {
        Optional<Activity> optional = adActivityRepository.findById(command.getId());
        if(!optional.isPresent()){
            throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
        }
        Activity activity = optional.get();
        activity.setName(command.getName());

        if (command.getLevel().equals("0")){
            activity.setLevel(Level.LEVEL_1);
        }
        if (command.getLevel().equals("1")){
            activity.setLevel(Level.LEVEL_2);
        }
        if (command.getLevel().equals("2")){
            activity.setLevel(Level.LEVEL_3);
        }
        if (!command.getStartTime().equals("") && !command.getEndTime().equals("")){
            Long startTime = convertDateToString(command.getStartTime());
            Long endTime = convertDateToString(command.getEndTime());
            activity.setStartTime(startTime);
            activity.setEndTime(endTime);
        }

        activity.setSemesterId(command.getSemesterId());
        return adActivityRepository.save(activity);
    }

    @Override
    public boolean deleteActivity(String id) {
        Optional<Activity> optional = adActivityRepository.findById(id);
        if(!optional.isPresent()){
            throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
        }
        adActivityRepository.delete(optional.get());
        return true;
    }

    @Override
    public Activity getOneByIdActivity(String id) {
        Optional<Activity> optional = adActivityRepository.findById(id);
        if(!optional.isPresent()){
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
        return adSemesterRepository.findAll();
    }

    public Long convertDateToString(String dateStringToLong){

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


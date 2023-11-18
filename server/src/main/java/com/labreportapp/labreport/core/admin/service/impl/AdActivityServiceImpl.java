package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdCreatActivityRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindActivityRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateActivityRequest;
import com.labreportapp.labreport.core.admin.model.response.AdActivityLevelResponse;
import com.labreportapp.labreport.core.admin.model.response.AdActivityResponse;
import com.labreportapp.labreport.core.admin.model.response.AdGetActivityResponse;
import com.labreportapp.labreport.core.admin.repository.AdActivityRepository;
import com.labreportapp.labreport.core.admin.repository.AdLevelRepository;
import com.labreportapp.labreport.core.admin.repository.AdSemesterRepository;
import com.labreportapp.labreport.core.admin.service.AdActivityService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.entity.Activity;
import com.labreportapp.labreport.entity.Level;
import com.labreportapp.labreport.entity.Semester;
import com.labreportapp.labreport.infrastructure.constant.AllowUseTrello;
import com.labreportapp.labreport.util.CompareUtil;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.labreport.util.SemesterHelper;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private AdLevelRepository adLevelRepository;

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
        Long startTime = DateUtils.truncate(new Date(command.getStartTime()), Calendar.DATE).getTime();
        Long endTime = DateUtils.truncate(new Date(command.getEndTime()), Calendar.DATE).getTime();
        activity.setStartTime(startTime);
        activity.setEndTime(endTime);
        activity.setLevelId(command.getLevel());
        activity.setSemesterId(command.getSemesterId());
        activity.setDescriptions(command.getDescriptions());
        activity.setAllowUseTrello(AllowUseTrello.values()[command.getAllowUseTrello()]);
        loggerUtil.sendLogScreen("Đã thêm hoạt động mới \"" + activity.getName() + "\".", "");
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Đã cập nhật hoạt động có mã-tên là : \"").append(activity.getCode()).append(" - ").append(activity.getName()).append("\". ");
        String messageCode = CompareUtil.compareAndConvertMessage("mã của hoạt động", activity.getCode(), command.getCode(), "");
        stringBuilder.append(messageCode).append(!messageCode.equals("") ? ", " : "");

        String messageName = CompareUtil.compareAndConvertMessage("tên của hoạt động", activity.getName(), command.getName(), "");
        stringBuilder.append(messageName).append(!messageName.equals("") ? ", " : "");

        Long startTime = DateUtils.truncate(new Date(command.getStartTime()), Calendar.DATE).getTime();
        Long endTime = DateUtils.truncate(new Date(command.getEndTime()), Calendar.DATE).getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String startTimeOld = sdf.format(activity.getStartTime());
        String startTimeNew = sdf.format(startTime);
        String messageStartTime = CompareUtil.compareAndConvertMessage("ngày bắt đầu của hoạt động", startTimeOld, startTimeNew, "");
        stringBuilder.append(messageStartTime).append(!messageStartTime.equals("") ? ", " : "");

        String endTimeOld = sdf.format(activity.getEndTime());
        String endTimeNew = sdf.format(endTime);
        String messageEndTime = CompareUtil.compareAndConvertMessage("ngày kết thúc của hoạt động", endTimeOld, endTimeNew, "");
        stringBuilder.append(messageEndTime).append(!messageEndTime.equals("") ? ", " : "");

        if (!command.getLevel().equals(activity.getLevelId())) {
            Optional<Level> levelOld = adLevelRepository.findById(activity.getLevelId());
            Optional<Level> levelNew = adLevelRepository.findById(command.getLevel());
            String messageLevel = CompareUtil.compareAndConvertMessage("level của hoạt động", levelOld.get().getName(), levelNew.get().getName(), "");
            stringBuilder.append(messageLevel).append(!messageLevel.equals("") ? ", " : "");
        }

        if (!command.getSemesterId().equals(activity.getSemesterId())) {
            Optional<Semester> semesterOld = adSemesterRepository.findById(activity.getSemesterId());
            Optional<Semester> semesterNew = adSemesterRepository.findById(command.getSemesterId());
            String messageLevel = CompareUtil.compareAndConvertMessage("kì của hoạt động", semesterOld.get().getName(), semesterNew.get().getName(), "");
            stringBuilder.append(messageLevel).append(!messageLevel.equals("") ? ", " : "");
        }
        String messageDescriptions = CompareUtil.compareAndConvertMessage("mô tả của hoạt động", activity.getDescriptions(), command.getDescriptions(), "");
        stringBuilder.append(messageDescriptions).append(!messageDescriptions.equals("") ? ", " : "");

        String messageTrello = CompareUtil.compareAndConvertMessage("cho phép sử dụng trello của hoạt động", activity.getAllowUseTrello(), AllowUseTrello.values()[command.getAllowUseTrello()], "");
        stringBuilder.append(messageTrello).append(!messageTrello.equals("") ? ", " : "");
        loggerUtil.sendLogScreen(stringBuilder.toString(), "");

        activity.setName(command.getName());
        activity.setCode(command.getCode());
        activity.setStartTime(startTime);
        activity.setEndTime(endTime);
        activity.setLevelId(command.getLevel());
        activity.setSemesterId(command.getSemesterId());
        activity.setDescriptions(command.getDescriptions());
        activity.setAllowUseTrello(AllowUseTrello.values()[command.getAllowUseTrello()]);

        return adActivityRepository.save(activity);
    }

    @Override
    public String deleteActivity(String id) {
        Optional<Activity> optional = adActivityRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RestApiException(Message.ACTIVITY_NOT_EXISTS);
        }
        Integer countClass = adActivityRepository.countClassInActivity(id);
        if (countClass != null && countClass > 0) {
            throw new RestApiException(Message.ACTIVITY_HAVE_CLASS);
        }
        loggerUtil.sendLogScreen("Đã xóa hoạt động \"" + optional.get().getCode() + " - " + optional.get().getName() + "\"", "");
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


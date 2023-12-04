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
import com.labreportapp.labreport.util.CompareUtil;
import com.labreportapp.labreport.util.FormUtils;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import lombok.Synchronized;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class AdSemesterServiceImpl implements AdSemesterService {

    @Autowired
    private AdSemesterRepository adSemesterRepository;

    private FormUtils formUtils = new FormUtils();

    private List<AdSemesterResponse> adSemesterResponseList;

    @Autowired
    private LoggerUtil loggerUtil;

    @Override
    public List<Semester> findAllSermester(Pageable pageable) {
        return adSemesterRepository.getAllSemester(pageable);
    }

    @Override
    @Synchronized
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
            semester1.setStartTime(DateUtils.truncate(new Date(semester1.getStartTime()), Calendar.DATE).getTime());
            semester1.setEndTime(DateUtils.truncate(new Date(semester1.getEndTime()), Calendar.DATE).getTime());
            semester1.setStartTimeStudent(DateUtils.truncate(new Date(semester1.getStartTimeStudent()), Calendar.DATE).getTime());
            semester1.setEndTimeStudent(DateUtils.truncate(new Date(semester1.getEndTimeStudent()), Calendar.DATE).getTime());
        }
        semester.setStatusFeedBack(StatusFeedBack.CHUA_FEEDBACK);
        loggerUtil.sendLogScreen("Đã thêm mới học kỳ " + obj.getName(), "");
        return adSemesterRepository.save(semester);
    }

    @Override
    public Semester updateSermester(@Valid AdUpdateSemesterRequest obj) {
        Optional<Semester> findSemesterById = adSemesterRepository.findById(obj.getId());
        if (findSemesterById.isEmpty()) {
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        Semester semester = findSemesterById.get();

        StringBuilder stringBuilder = new StringBuilder();

        String messageNameSemester = CompareUtil.compareAndConvertMessage(" tên học kỳ", semester.getName(), obj.getName(), "");
        stringBuilder.append(messageNameSemester).append(!messageNameSemester.equals("") ? "," : "");

        String startTimeOld = sdf.format(semester.getStartTime());
        String startTimeNew = sdf.format(obj.getStartTime());
        String messageStartTimeSemester = CompareUtil.compareAndConvertMessage(" ngày bắt đầu của học kỳ", startTimeOld, startTimeNew, "");
        stringBuilder.append(messageStartTimeSemester).append(!messageStartTimeSemester.equals("") ? "," : "");

        String endTimeOld = sdf.format(semester.getEndTime());
        String endTimeNew = sdf.format(obj.getEndTime());
        String messageEndTimeSemester = CompareUtil.compareAndConvertMessage(" ngày kết thúc của học kỳ", endTimeOld, endTimeNew, "");
        stringBuilder.append(messageEndTimeSemester).append(!messageEndTimeSemester.equals("") ? "," : "");

        String startTimeStudentOld = sdf.format(semester.getStartTimeStudent());
        String startTimeStudentNew = sdf.format(obj.getStartTimeStudent());
        String messageStartTimeStudent = CompareUtil.compareAndConvertMessage(" ngày bắt đầu sinh viên của học kỳ", startTimeStudentOld, startTimeStudentNew, "");
        stringBuilder.append(messageStartTimeStudent).append(!messageStartTimeStudent.equals("") ? "," : "");

        String endTimeStudentOld = sdf.format(semester.getEndTimeStudent());
        String endTimeStudentNew = sdf.format(obj.getEndTimeStudent());
        String messageEndTimeStudent = CompareUtil.compareAndConvertMessage(" ngày kết thúc sinh viên của học kỳ", endTimeStudentOld, endTimeStudentNew, "");
        stringBuilder.append(messageEndTimeStudent).append(!messageEndTimeStudent.equals("") ? "," : "");

        if (stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
            stringBuilder.setCharAt(stringBuilder.length() - 1, '.');
        }
        loggerUtil.sendLogScreen(stringBuilder.toString(), "");

        semester.setName(obj.getName());
        semester.setStartTime(DateUtils.truncate(new Date(obj.getStartTime()), Calendar.DATE).getTime());
        semester.setEndTime(DateUtils.truncate(new Date(obj.getEndTime()), Calendar.DATE).getTime());
        semester.setStartTimeStudent(DateUtils.truncate(new Date(obj.getStartTimeStudent()), Calendar.DATE).getTime());
        semester.setEndTimeStudent(DateUtils.truncate(new Date(obj.getEndTimeStudent()), Calendar.DATE).getTime());
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
        if (findSemester.isEmpty()) {
            throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
        }
        return findSemester.get();
    }

    @Override
    public Boolean deleteSemester(String id) {
        Optional<Semester> findSemesterById = adSemesterRepository.findById(id);
        if (findSemesterById.isEmpty()) {
            throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
        }
        Integer countActivities = adSemesterRepository.countActivitiesBySemesterId(id);
        if (countActivities > 0) {
            throw new RestApiException(Message.SEMESTER_ACTIVITY_ALREADY_EXISTS);
        }
        loggerUtil.sendLogScreen("Đã xóa học kỳ " + findSemesterById.get().getName()+".", "");
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
        loggerUtil.sendLogScreen("Đã bật feedback của học kỳ " + findSemesterById.get().getName()+".", "");
        return true;
    }

    @Override
    public List<AdSemesterResponse> getAllSemesters() {
        return adSemesterRepository.getAllSemesters();
    }
}

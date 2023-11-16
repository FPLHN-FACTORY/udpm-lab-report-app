package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdCreateMeetingPeriodRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindMeetingConfigurationRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateMeetingPeriodRequest;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingPeriodConfigurationResponse;
import com.labreportapp.labreport.core.admin.repository.AdMeetingPeriodConfigurationRepository;
import com.labreportapp.labreport.core.admin.service.AdMeetingPeriodConfigurationService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.entity.MeetingPeriod;
import com.labreportapp.labreport.util.FormUtils;
import com.labreportapp.labreport.util.LoggerUtil;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * @author quynhncph26201
 */
@Service
@Validated
public class AdMeetingPeriodConfigurationServiceImpl implements AdMeetingPeriodConfigurationService {

    @Autowired
    private AdMeetingPeriodConfigurationRepository adMeetingPeriodRepository;

    private FormUtils formUtils = new FormUtils();

    private List<AdMeetingPeriodConfigurationResponse> adMeetingPeriodResponsesList;

    @Autowired
    private LoggerUtil loggerUtil;

    @Override
    public List<MeetingPeriod> findAllMeetingPeriod(Pageable pageable) {
        return adMeetingPeriodRepository.getAllMeetingPeriod(pageable);
    }

    @Override
    public MeetingPeriod createMeetingPeriod(@Valid AdCreateMeetingPeriodRequest obj) {
        MeetingPeriod meetingPeriod = formUtils.convertToObject(MeetingPeriod.class, obj);
        loggerUtil.sendLogScreen("Đã thêm ca học mới: " + obj.getName() + ".", "");
        return adMeetingPeriodRepository.save(meetingPeriod);
    }

    @Override
    public MeetingPeriod updateMeetingPeriod(AdUpdateMeetingPeriodRequest obj) {
        Optional<MeetingPeriod> findById = adMeetingPeriodRepository.findById(obj.getId());
        if (!findById.isPresent()) {
            throw new RestApiException(Message.PERIOD_NOT_EXISTS);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Đã cập nhật lại ca học: ");
        if (!findById.get().getName().equals(obj.getName())) {
            stringBuilder.append(" tên ca học từ \"").append(findById.get().getName()).append("\" thành \"")
                    .append(obj.getName()).append("\",");
        }
        if (!findById.get().getStartHour().equals(obj.getStartHour()) || !findById.get().getStartMinute().equals(obj.getStartMinute())
                || !findById.get().getEndHour().equals(obj.getEndHour()) || !findById.get().getEndMinute().equals(obj.getEndMinute())) {
            stringBuilder.append(" thời gian ca học từ ")
                    .append(findById.get().getStartHour()).append("h:").append(findById.get().getStartMinute()).append("p")
                    .append(" - ")
                    .append(findById.get().getEndHour()).append("h:").append(findById.get().getEndMinute()).append("p")
                    .append(" thành ")
                    .append(obj.getStartHour()).append("h:").append(obj.getStartMinute()).append("p")
                    .append(" - ")
                    .append(obj.getEndHour()).append("h:").append(obj.getEndMinute()).append("p ,");
        }
        if (stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
            stringBuilder.setCharAt(stringBuilder.length() - 1, '.');
        }
        loggerUtil.sendLogScreen(stringBuilder.toString(), "");
        MeetingPeriod meetingPeriod = findById.get();
        meetingPeriod.setName(obj.getName());
        meetingPeriod.setStartMinute(obj.getStartMinute());
        meetingPeriod.setEndMinute(obj.getEndMinute());
        meetingPeriod.setStartHour(obj.getStartHour());
        meetingPeriod.setEndHour(obj.getEndHour());

        return adMeetingPeriodRepository.save(meetingPeriod);
    }

    @Override
    public PageableObject<AdMeetingPeriodConfigurationResponse> searchMeetingPeriod(AdFindMeetingConfigurationRequest rep) {
        Pageable pageable = PageRequest.of(rep.getPage() - 1, rep.getSize());
        Page<AdMeetingPeriodConfigurationResponse> adMeetingPeriodResponses = adMeetingPeriodRepository.searchMeetingPeriod(rep, pageable);
        adMeetingPeriodResponsesList = adMeetingPeriodResponses.stream().toList();
        return new PageableObject<>(adMeetingPeriodResponses);
    }

    @Override
    public Boolean deleteMeetingPeriod(String id) {
        Optional<MeetingPeriod> findMeetingPeriodById = adMeetingPeriodRepository.findById(id);
        Integer countMeetings = adMeetingPeriodRepository.countMeetingByMeetingPeriodId(id);
        if (!findMeetingPeriodById.isPresent()) {
            throw new RestApiException(Message.PERIOD_NOT_EXISTS);
        }
        if (countMeetings != null && countMeetings > 0) {
            throw new RestApiException(Message.PERIOD_OVERLAP);
        }
        loggerUtil.sendLogScreen("Đã xóa ca học: " + findMeetingPeriodById.get().getName() + ".", "");
        adMeetingPeriodRepository.delete(findMeetingPeriodById.get());
        return true;
    }
}

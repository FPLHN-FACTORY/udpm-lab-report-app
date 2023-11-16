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
        loggerUtil.sendLogScreen("Đã thêm cấu hình ca học mới " + obj.getName()+" .", "");
        return adMeetingPeriodRepository.save(meetingPeriod);
    }

    @Override
    public MeetingPeriod updateMeetingPeriod(AdUpdateMeetingPeriodRequest obj) {
        Optional<MeetingPeriod> findById = adMeetingPeriodRepository.findById(obj.getId());
        if (!findById.isPresent()) {
            throw new RestApiException(Message.PERIOD_NOT_EXISTS);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Đã cập nhật lại ca học ");
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
        adMeetingPeriodRepository.delete(findMeetingPeriodById.get());
        return true;
    }
}

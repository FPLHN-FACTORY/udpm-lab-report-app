package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.response.AdMeetingPeriodResponse;
import com.labreportapp.labreport.core.admin.repository.AdMeetingPeriodRepository;
import com.labreportapp.labreport.core.admin.service.AdMeetingPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class AdMeetingPeriodServiceImpl implements AdMeetingPeriodService {

    @Autowired
    private AdMeetingPeriodRepository adMeetingPeriodRepository;

    @Override
    public List<AdMeetingPeriodResponse> getAllMeetingPeriod() {
        return adMeetingPeriodRepository.getAllMeetingPeriod();
    }
}

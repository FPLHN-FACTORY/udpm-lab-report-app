package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.teacher.repository.TeMeetingPeriodRepository;
import com.labreportapp.labreport.core.teacher.service.TeMeetingPeriodService;
import com.labreportapp.labreport.entity.MeetingPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hieundph25894
 */
@Service
public class TeMeetingPeriodServiceImpl implements TeMeetingPeriodService {

    @Autowired
    private TeMeetingPeriodRepository teMeetingPeriodRepository;

    @Override
    public List<MeetingPeriod> listMeetingPeriod() {
        List<MeetingPeriod> list = teMeetingPeriodRepository.findAll(Sort.by("name"));
        return list;
    }
}

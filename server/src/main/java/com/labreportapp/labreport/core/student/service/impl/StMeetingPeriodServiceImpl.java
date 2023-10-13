package com.labreportapp.labreport.core.student.service.impl;

import com.labreportapp.labreport.core.student.repository.StMeetingPeriodRepository;
import com.labreportapp.labreport.core.student.service.StMeetingPeriodService;
import com.labreportapp.labreport.entity.MeetingPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hieundph25894
 */
@Service
public class StMeetingPeriodServiceImpl implements StMeetingPeriodService {

    @Autowired
    private StMeetingPeriodRepository stMeetingPeriodRepository;

    @Override
    public List<MeetingPeriod> listMeetingPeriod() {
        List<MeetingPeriod> list = stMeetingPeriodRepository.findAll(Sort.by("name"));
        return list;
    }
}

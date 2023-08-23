package com.labreportapp.core.teacher.service.impl;

import com.labreportapp.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.core.teacher.model.response.TeMeetingRespone;
import com.labreportapp.core.teacher.repository.TeMeetingRepository;
import com.labreportapp.core.teacher.service.TeMeetingService;
import com.labreportapp.entity.Meeting;
import com.labreportapp.infrastructure.constant.Message;
import com.labreportapp.infrastructure.exception.rest.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author hieundph25894
 */
@Service
public class TeMeetingServiceImpl implements TeMeetingService {

    @Autowired
    private TeMeetingRepository teMeetingRepository;

    @Override
    public List<TeMeetingRespone> searchMeetingByIdClass(TeFindMeetingRequest request) {
        List<TeMeetingRespone> list = teMeetingRepository.findMeetingByIdClass(request);
        return list;
    }

    @Override
    public Integer countMeetingByClassId(String idClass) {
        return teMeetingRepository.countMeetingByClassId(idClass);
    }

    @Override
    public TeMeetingRespone searchMeetingByIdMeeting(TeFindMeetingRequest request) {
        Optional<TeMeetingRespone> meeting = teMeetingRepository.searchMeetingByIdMeeting(request);
        if (!meeting.isPresent()) {
            throw new RestApiException(Message.MEETING_NOT_EXISTS);
        }
        return meeting.get();
    }

}

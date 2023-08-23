package com.labreportapp.core.teacher.service;

import com.labreportapp.core.teacher.model.request.TeFindMeetingRequest;
import com.labreportapp.core.teacher.model.response.TeMeetingRespone;
import com.labreportapp.entity.Meeting;

import java.util.List;

/**
 * @author hieundph25894
 */
public interface TeMeetingService {

    List<TeMeetingRespone> searchMeetingByIdClass(final TeFindMeetingRequest request);

    Integer countMeetingByClassId(String idClass);

    TeMeetingRespone searchMeetingByIdMeeting(final TeFindMeetingRequest request);
}

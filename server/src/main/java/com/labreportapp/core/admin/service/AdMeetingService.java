package com.labreportapp.core.admin.service;

import com.labreportapp.core.admin.model.request.AdCreateMeetingRequest;
import com.labreportapp.core.admin.model.request.AdUpdateMeetingRequest;
import com.labreportapp.core.admin.model.response.AdMeetingResponse;
import com.labreportapp.entity.Meeting;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface AdMeetingService {

    List<AdMeetingResponse> getAllMeetingByIdClass(String idClass);

    Meeting create(@Valid AdCreateMeetingRequest request);

    Meeting update(@Valid AdUpdateMeetingRequest request);

    String delete(String id);
}

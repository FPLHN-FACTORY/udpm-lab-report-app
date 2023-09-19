package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.AdCreateMeetingRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateMeetingRequest;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingResponse;
import com.labreportapp.labreport.entity.Meeting;
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
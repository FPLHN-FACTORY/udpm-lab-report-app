package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.AdFindClassRequest;
import com.labreportapp.labreport.core.admin.model.response.AdListClassCustomResponse;
import com.labreportapp.labreport.core.admin.model.response.AdMeetingRequestCustom;
import com.labreportapp.labreport.core.common.base.PageableObject;

import java.util.List;

/**
 * @author todo thangncph26123
 */
public interface AdMeetingRequestService {

    PageableObject<AdListClassCustomResponse> searchClassHaveMeetingRequest(final AdFindClassRequest request);

    Integer countClassHaveMeetingRequest(final AdFindClassRequest request);

    List<AdMeetingRequestCustom> getAllMeetingRequestByIdClass(String idClass);

    Boolean approveMeetingRequest(List<String> listIdMeetingRequest);

    Boolean approveClass(List<String> listIdClass);

    Boolean noApproveMeetingRequest(List<String> listIdMeetingRequest);

    Boolean noApproveClass(List<String> listIdClass);
}

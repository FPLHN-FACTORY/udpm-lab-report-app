package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.response.AdFeedBackResponse;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface AdFeedBackService {

    List<AdFeedBackResponse> searchFeedBack(String idClass);
}

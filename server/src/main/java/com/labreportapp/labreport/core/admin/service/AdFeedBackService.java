package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.response.AdFeedBackCustom;
import com.labreportapp.labreport.core.admin.model.response.AdObjFeedbackResponse;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface AdFeedBackService {

    List<AdFeedBackCustom> searchFeedBack(String idClass);

    AdObjFeedbackResponse getAllFeedbackByIdClass(String idClass);
}

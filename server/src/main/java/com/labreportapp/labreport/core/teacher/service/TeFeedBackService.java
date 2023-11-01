package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.teacher.model.response.TeObjFeedbackResponse;

/**
 * @author hieundph25894 - duchieu212
 */
public interface TeFeedBackService {

    TeObjFeedbackResponse getAllFeedbackByIdClass(String idClass);
}

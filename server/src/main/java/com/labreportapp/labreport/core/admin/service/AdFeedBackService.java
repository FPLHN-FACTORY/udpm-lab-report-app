package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.response.AdFeedBackResponse;
import com.labreportapp.labreport.core.admin.model.response.AdStudentCallApiResponse;
import java.util.List;

/**
 * @author quynhncph26201
 */
public interface AdFeedBackService {

    List<AdFeedBackResponse> searchFeedBack(String idClass);

    List<AdStudentCallApiResponse> searchApiStudentClassesByIdClass(String idClass);


}

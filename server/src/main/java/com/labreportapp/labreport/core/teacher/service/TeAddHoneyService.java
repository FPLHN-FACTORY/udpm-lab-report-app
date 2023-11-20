package com.labreportapp.labreport.core.teacher.service;

import com.labreportapp.labreport.core.common.base.CategoryHoneyResponse;

import java.util.List;

/**
 * @author todo thangncph26123
 */
public interface TeAddHoneyService {

    List<CategoryHoneyResponse> getAllCategory();

    Boolean addHoney(String idClass, String categoryId);
}

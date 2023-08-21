package com.labreportapp.core.student.service;

import com.labreportapp.core.common.base.SimpleEntityProjection;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface StActivityService {

    List<SimpleEntityProjection> getAllActivity(String semesterId);
}

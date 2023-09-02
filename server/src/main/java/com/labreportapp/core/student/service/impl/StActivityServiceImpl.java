package com.labreportapp.core.student.service.impl;

import com.labreportapp.core.common.base.SimpleEntityProjection;
import com.labreportapp.core.student.repository.StActivityRepository;
import com.labreportapp.core.student.service.StActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class StActivityServiceImpl implements StActivityService {

    @Autowired
    private StActivityRepository stActivityRepository;

    @Override
    public List<SimpleEntityProjection> getAllActivity(String semesterId) {
        return stActivityRepository.getAllActivity(semesterId);
    }
}

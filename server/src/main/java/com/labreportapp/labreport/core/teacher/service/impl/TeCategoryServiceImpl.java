package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.teacher.repository.TeCategoryRepository;
import com.labreportapp.labreport.core.teacher.service.TeCategoryService;
import com.labreportapp.portalprojects.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hieundph25894 - duchieu212
 */
@Service
public class TeCategoryServiceImpl implements TeCategoryService {

    @Autowired
    private TeCategoryRepository teCategoryRepository;

    @Override
    public List<Category> getAllCategory() {
        return teCategoryRepository.findAll();
    }
}

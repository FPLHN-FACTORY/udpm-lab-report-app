package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.teacher.model.response.TeLevelResponse;
import com.labreportapp.labreport.core.teacher.repository.TeLevelRepository;
import com.labreportapp.labreport.core.teacher.service.TeLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hieundph25894
 */
@Service
public class TeLevelServiceImpl implements TeLevelService {

    @Autowired
    private TeLevelRepository teLevelRepository;

    @Override
    public List<TeLevelResponse> getAllLevel() {
        List<TeLevelResponse> list = teLevelRepository.getAllLevel();
        if (list.size() == 0) {
            return null;
        }
        return list;
    }

}

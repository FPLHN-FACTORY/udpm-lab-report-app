package com.labreportapp.labreport.core.admin.service.impl;


import com.labreportapp.labreport.core.admin.model.response.AdFeedBackResponse;
import com.labreportapp.labreport.core.admin.repository.AdFeedBackRepository;
import com.labreportapp.labreport.core.admin.service.AdFeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author quynhncph26201
 */
@Service
public class AdFeedBackServiceImpl implements AdFeedBackService {
    @Autowired
    private AdFeedBackRepository repository;


    @Override
    public List<AdFeedBackResponse> searchFeedBack(String idClass) {
        List<AdFeedBackResponse> list = repository.getAllFeedBack(idClass);
        return list;
    }
}

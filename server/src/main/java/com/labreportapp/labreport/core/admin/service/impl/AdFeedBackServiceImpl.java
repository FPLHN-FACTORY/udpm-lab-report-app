package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdFeedBackRequest;
import com.labreportapp.labreport.core.admin.model.response.AdFeedBackResponse;
import com.labreportapp.labreport.core.admin.repository.AdFeedBackRepository;
import com.labreportapp.labreport.core.admin.service.AdFeedBackService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.teacher.model.response.TePointRespone;
import com.labreportapp.labreport.core.teacher.model.response.TePostRespone;
import com.labreportapp.labreport.core.teacher.repository.TePostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

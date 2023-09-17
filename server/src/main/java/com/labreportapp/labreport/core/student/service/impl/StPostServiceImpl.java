package com.labreportapp.labreport.core.student.service.impl;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.student.model.request.StFindPostRequest;
import com.labreportapp.labreport.core.student.model.response.StPostResponse;
import com.labreportapp.labreport.core.student.repository.StPostRepository;
import com.labreportapp.labreport.core.student.service.StPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author quynhncph26201
 */
@Service
public class StPostServiceImpl implements StPostService {
    @Autowired
    private StPostRepository repository;
    @Override
    public PageableObject<StPostResponse> searchPagePost(StFindPostRequest repquest) {
        Pageable pageable = PageRequest.of(repquest.getPage() - 1, repquest.getSize());
        Page<StPostResponse> list = repository.searchPostByIdClass(repquest, pageable);
        return new PageableObject<>(list);
    }
}

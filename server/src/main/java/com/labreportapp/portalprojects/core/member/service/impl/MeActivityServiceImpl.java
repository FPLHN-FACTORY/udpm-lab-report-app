package com.labreportapp.portalprojects.core.member.service.impl;

import com.labreportapp.portalprojects.core.common.base.PageableObject;
import com.labreportapp.portalprojects.core.member.model.request.MeFindActivityRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeActivityResponse;
import com.labreportapp.portalprojects.core.member.repository.MeActivityRepository;
import com.labreportapp.portalprojects.core.member.service.MeActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author thangncph26123
 */
@Service
public class MeActivityServiceImpl implements MeActivityService {

    @Autowired
    private MeActivityRepository meActivityRepository;

    @Override
    public PageableObject<MeActivityResponse> getAll(final MeFindActivityRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        return new PageableObject<>(meActivityRepository.getAll(pageRequest, request));
    }
}

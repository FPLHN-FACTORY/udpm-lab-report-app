package com.labreportapp.core.admin.service.impl;

import com.labreportapp.core.admin.service.AdTeacherService;
import com.labreportapp.core.common.response.SimpleResponse;
import com.labreportapp.infrastructure.apiconstant.ActorConstants;
import com.labreportapp.util.ConvertRequestCallApiIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thangncph26123
 */
@Service
public class AdTeacherServiceImpl implements AdTeacherService {

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Override
    public List<SimpleResponse> getAllTeacher() {
        List<SimpleResponse> response = convertRequestCallApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_TEACHER);
        return response;
    }
}

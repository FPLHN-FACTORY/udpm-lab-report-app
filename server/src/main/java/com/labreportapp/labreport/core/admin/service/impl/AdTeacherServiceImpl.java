package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.service.AdTeacherService;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.infrastructure.apiconstant.ActorConstants;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
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
